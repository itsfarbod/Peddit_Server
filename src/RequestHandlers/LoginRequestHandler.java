package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginRequestHandler extends Thread{
    String request;
    Socket socket;

    public LoginRequestHandler(String request, Socket socket) {
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
        String password = matchWith("#(.*)\"");

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String passwordHash = new String(hash, StandardCharsets.UTF_8);

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/UserPass.txt"))) {
            String line;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                String passwrodHashesTemp = stringMatchWith(line, ": (.*?)$");
                if (passwordHash.equals(passwrodHashesTemp)) {
                    try(BufferedReader nbr = new BufferedReader(new FileReader("./DataBase/Users.txt"))) {
                        while ((line = br.readLine()) != null) {
                            String userNameTemp=stringMatchWith(line, "\"\\\"userName\\\":\\\"(.*?)\\\"\"");
                            if(userNameTemp.equals(userName)){
                                sendMessage(line.trim());
                                break;
                            }
                        }
                    }catch (IOException e) {}
                    userFound = true;
                    break;
                }
            }
            if(userFound == false) {
                sendMessage("User did not found");
            }
        }catch (IOException e){}
    }
}
