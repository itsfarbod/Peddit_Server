package RequestHandlers;

import Models.ForumModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateNewForumRequestHandler extends Thread{
    String request;
    Socket socket;

    public CreateNewForumRequestHandler(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        Pattern pattern = Pattern.compile("#(.*)$");
        Matcher matcher = pattern.matcher(request);
        String newForumJsonString = matcher.group(1);
        ForumModel newForumModel = gson.fromJson(newForumJsonString.trim() , ForumModel.class);
        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Forums.txt"))) {
            String line;
            String FN;
            String regex = "\"\\\"forumName\\\":\\\"(.*?)\\\"\"";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                FN = matcher.group(1);
                if(newForumModel.getForumName().equals(FN)) {
                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                    dos.write("ForumAlreadyExists".getBytes());
                    dos.close();
                    return;
                }
            }
            FileWriter fw = new FileWriter( "./DataBase/Forums.txt",true);
            fw.write(newForumJsonString);
            fw.write('\n');
            fw.flush();
            fw.close();
            DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
            dos.write("Success".getBytes());
            dos.close();
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
