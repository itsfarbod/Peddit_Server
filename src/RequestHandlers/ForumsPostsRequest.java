package RequestHandlers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
        String forumName = matchWith("#(.*)$");

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Forums.txt"))) {
            String line;
            String FN;
            boolean forumFound = false;
            while ((line = br.readLine()) != null) {
                FN=stringMatchWith(line,"\"\\\"forumName\\\":\\\"(.*?)\\\"\"");
                if(forumName.equals(FN)) {
                    sendMessage(line);
                    forumFound = true;
                    break;
                }
            }
            if(!forumFound) {
                sendMessage("ForumDidNotfound");
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
