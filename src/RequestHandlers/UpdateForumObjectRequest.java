package RequestHandlers;

import Models.ForumModel;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdateForumObjectRequest extends Thread{

    String request;
    Socket socket;

    public UpdateForumObjectRequest(String request, Socket socket) {
        this.request = request;
        this.socket = socket;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        Pattern pattern = Pattern.compile("#(.*)$");
        Matcher matcher = pattern.matcher(request);
        String newForumJsonString = matcher.group(1);
        ForumModel newForum = gson.fromJson(newForumJsonString.trim() , ForumModel.class);
        changeForumJsonStringInDataBase(newForumJsonString.trim(),findOldForumJasonStringFromDataBase(newForum.getForumName()));
        try {
            DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
            dos.write("ForumUpdated".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void changeForumJsonStringInDataBase(String newForumJsonString , String oldForumJsonString) {
        File inputFile = new File("src/DataBase/Forums.txt");
        File tempFile = new File("src/DataBase/TempForumFile.txt");

        String currentLine;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            while((currentLine = reader.readLine()) != null) {
                if(currentLine.equals(oldForumJsonString)){
                    writer.write(newForumJsonString);
                    writer.write('\n');
                }
                else {
                    writer.write(currentLine);
                }
                writer.flush();
            }
            writer.close();
            reader.close();
            inputFile.delete();
            boolean successful = tempFile.renameTo(inputFile);
        }catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private String findOldForumJasonStringFromDataBase(String forumName) {
        Pattern pattern;
        Matcher matcher;
        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/Forums.txt"))) {
            String line;
            String FN;
            String regex = "\"\\\"forumName\\\":\\\"(.*?)\\\"\"";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                FN = matcher.group(1);
                if(forumName.equals(FN)) {
                    return line;
                }
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return "ForumDidNotfound";
    }
}
