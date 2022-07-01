package RequestHandlers;

import Models.ForumModel;
import Models.PostModel;
import Models.UserModel;
import com.google.gson.Gson;
import utils.SortByTime;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedPagePostsRequestHandler extends Thread{
    String request;
    Socket socket;

    public FeedPagePostsRequestHandler(String request, Socket socket) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String userName = matchWith("@(.*?)/");

        try (BufferedReader br = new BufferedReader(new FileReader("src/DataBase/Users.txt"))) {
            String line;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                String UN=stringMatchWith(line, "\"userName\":\"(.*?)\"");  // "\"\\\"userName\\\":\\\"(.*?)\\\"\""
                if(userName.equals(UN)) {
//                    sendMessage(line);
                    userFound = true;
                    break;
                }
            }
            if(!userFound) {
                sendMessage("UserDidNotfound");
            }
            else{
                sendMessage(GetPostsForUser(line));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String GetPostsForUser (String userJson) {
        Gson gson = new Gson();
        UserModel userTempModel = gson.fromJson(userJson, UserModel.class);
        ArrayList<ForumModel> userForums = userTempModel.getFollowedForums();
        ArrayList<String> userForumsNames = new ArrayList<>(0);
        for(ForumModel forum : userForums) {
            userForumsNames.add(forum.getForumName());
        }
        userForums = new ArrayList<>(0);
        for (String FN : userForumsNames) {
            userForums.add(getForumObjectFromJsonString(FN));
        }
        ArrayList<PostModel> postsForClient = new ArrayList<>(0);
        for(ForumModel forum : userForums) {
            postsForClient.addAll(forum.getPosts());
        }
        postsForClient.sort(new SortByTime());
        return gson.toJson(postsForClient);
    }


    private ForumModel getForumObjectFromJsonString(String forumName) {
        Gson gson = new Gson();
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
                    return gson.fromJson(line.trim() , ForumModel.class);
                }
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return null;
    }
}