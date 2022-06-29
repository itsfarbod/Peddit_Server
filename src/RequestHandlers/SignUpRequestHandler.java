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


    @Override
    public void run() {
        String userName = null;
        String requestCommand = null;
        String jsonString = null;

        String regex = "@(.*?)/";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            userName = matcher.group(1);
        }

        regex = "/(.*?)#";

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(request);

        if(matcher.find()) {
            requestCommand = matcher.group(1);
        }

        regex = "#(.*)$";

        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(request);

        if(matcher.find()) {
            jsonString = matcher.group(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/UserPass.txt"))) {
            String line;
            String UN;
            boolean flag = false;
            regex = "^(.*?) :";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                UN = matcher.group(1);
                if(userName.equals(UN)) {
                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                    String message = "DuplicateUsername";
                    byte[] messageBytes = message.getBytes("UTF-8");
                    dos.write(messageBytes);
                    dos.close();
                    flag = true;
                    break;
                }
            }
            if(flag == false) {
                FileWriter fw = new FileWriter( "./DataBase/Users.txt",true);
                fw.write(jsonString);
                fw.write('\n');
                regex = "\"\\\"password\\\":\\\"(.*?)\\\"\"";
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(jsonString);
                MessageDigest digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(matcher.group(1).getBytes(StandardCharsets.UTF_8));
                String passwordHash = new String(hash, StandardCharsets.UTF_8);
                fw = new FileWriter("./DataBase/UserPass.txt" , true);
                fw.write(userName + " : " + passwordHash + "\n");
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                byte[] messageBytes = "Account Created Successfully".getBytes("UTF-8");
                dos.write(messageBytes);
                dos.close();
            }
        } catch (IOException e) {
            System.out.println("Reading file interrupted");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
