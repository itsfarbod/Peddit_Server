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

    @Override
    public void run() {
        String userName = null;
        String password = null;

        String regex = "@(.*?)/";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            userName = matcher.group(1);
        }

        regex = "#(.*)\"";

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(request);

        if(matcher.find()) {
            password = matcher.group(1);
        }

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
            String passwrodHashesTemp = null;
            regex = ": (.*?)$";
            boolean flag = false;
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                passwrodHashesTemp = matcher.group(1);
                if (passwordHash.equals(passwrodHashesTemp)) {
                    String userNameTemp = null;
                    regex = "\"\\\"userName\\\":\\\"(.*?)\\\"\"";
                    try(BufferedReader nbr = new BufferedReader(new FileReader("./DataBase/Users.txt"))) {
                        while ((line = br.readLine()) != null) {
                            pattern = Pattern.compile(regex);
                            matcher = pattern.matcher(line);
                            userNameTemp = matcher.group(1);
                            if(userNameTemp.equals(userName)){
                                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                                dos.write(line.getBytes("UTF-8"));
                                break;
                            }
                        }
                    }catch (IOException e) {}
                    flag = true;
                    break;
                }
            }
            if(flag == false) {
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                dos.write("User did not found".getBytes("UTF-8"));
            }
        }catch (IOException e){}
    }
}
