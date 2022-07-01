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
        String jsonString = matchWith("#(.*)$");
        System.out.println(jsonString);

        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/UserPass.txt"))) {
            String line;
            String UN;
            boolean signedUpBefore = false;
            while ((line = br.readLine()) != null) {
                UN=stringMatchWith(line.trim(), "^(.*?) :");
                if(userName.equals(UN)) {
                    sendMessage("DuplicateUsername");
                    signedUpBefore = true;
                    break;
                }
            }
            if(!signedUpBefore) {
                Gson gson = new Gson();
                UserModel user = gson.fromJson(jsonString.trim() , UserModel.class);
                FileWriter fw = new FileWriter( "src/DataBase/Users.txt",true);
                fw.write(jsonString);
                fw.write('\n');
                fw.flush();
                fw.close();
                String passwordField;
                fw = new FileWriter("src/DataBase/UserPass.txt" , true);
                passwordField = user.getUserName() + " : " + user.getPassword() + "\n";
                System.out.println(user.getPassword());
                fw.write(passwordField);
                fw.flush();
                sendMessage("Account Created Successfully");
            }
        } catch (IOException e) {
            System.out.println("Reading file interrupted");
        }
    }
}
