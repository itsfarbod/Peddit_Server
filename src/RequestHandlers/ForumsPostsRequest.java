package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForumsPostsRequest extends Thread{

    String request;
    Socket socket;

    public ForumsPostsRequest(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        String forumName = null;

        String regex = "#(.*)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            forumName = matcher.group(1);
        }

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Forums.txt"))) {
            String line;
            String FN;
            boolean flag = false;
            regex = "\"\\\"forumName\\\":\\\"(.*?)\\\"\"";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                FN = matcher.group(1);
                if(forumName.equals(FN)) {
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
                String message = "ForumDidNotfound";
                byte[] messageBytes = message.getBytes("UTF-8");
                dos.write(messageBytes);
                dos.close();
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
