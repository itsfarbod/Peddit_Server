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
            dos.write(message.getBytes());
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String userName = matchWith("@(.*?)/");
        String password = matchWith("#(.*)\"");

        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/UserPass.txt"))) {
            String line;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                String passwordsTemp = stringMatchWith(line, ": (.*?)$");
                if (password.trim().equals(passwordsTemp.trim())) {
                    sendMessage("UserFound");
//                        while ((line = nbr.readLine()) != null) {
//                            String userNameTemp=stringMatchWith(line, "\"\\\"userName\\\":\\\"(.*?)\\\"\"");
//                            if(userNameTemp.equals(userName)){
//                                sendMessage(line.trim());
//                                break;
//                            }
//                        }
//                    }catch (IOException e) {}
//                    try(BufferedReader nbr = new BufferedReader(new FileReader("src/DataBase/Users.txt"))) {
                    userFound = true;
                    break;
                }
            }
            if(userFound == false) {
                sendMessage("UserDidNotFound");
            }
        }catch (IOException e){}
    }
}