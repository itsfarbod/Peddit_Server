package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SavedPostsRequestHandler extends Thread{
    String request;
    Socket socket;

    public SavedPostsRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        String userName = null;

        String regex = "@(.*?)/";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            userName = matcher.group(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Users.txt"))) {
            String line;
            String UN;
            boolean flag = false;
            regex = "\"\\\"userName\\\":\\\"(.*?)\\\"\"";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                UN = matcher.group(1);
                if(userName.equals(UN)) {
                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                    byte[] messageBytes = line.getBytes("UTF-8");
                    dos.write(messageBytes);
                    dos.close();
                    flag = true;
                    break;
                }
            }
            if(flag == false) {
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                String message = "UserDidNotfound";
                byte[] messageBytes = message.getBytes("UTF-8");
                dos.write(messageBytes);
                dos.close();
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
