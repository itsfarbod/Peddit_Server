package RequestHandlers;

import Models.UserModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpRequestHandler extends Thread{
    String request;
    Socket socket;

    public SignUpRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    private String stringMatchWith(String str, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    private String matchWith(String regex){
        return stringMatchWith(request, regex);
    }

    private void sendMessage(String message){
        try {
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(message);
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String userName = matchWith("@(.*?)/");
        String requestCommand = matchWith("/(.*?)#");
        String jsonString = matchWith("#(.*)$");

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/UserPass.txt"))) {
            String line;
            String UN;
            boolean signedUpBefore = false;
            while ((line = br.readLine()) != null) {
                UN=stringMatchWith(line, "^(.*?) :");
                if(userName.equals(UN)) {
                    sendMessage("DuplicateUsername");
                    signedUpBefore = true;
                    break;
                }
            }
            if(!signedUpBefore) {
                FileWriter fw = new FileWriter( "./DataBase/Users.txt",true);
                fw.write(jsonString);
                fw.write('\n');
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(stringMatchWith(jsonString, "\"\\\"password\\\":\\\"(.*?)\\\"\"").getBytes(StandardCharsets.UTF_8));
                String passwordHash = new String(hash, StandardCharsets.UTF_8);
                fw = new FileWriter("./DataBase/UserPass.txt" , true);
                fw.write(userName + " : " + passwordHash + "\n");
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                sendMessage("Account Created Successfully");
            }
        } catch (IOException e) {
            System.out.println("Reading file interrupted");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
