package RequestHandlers;

import Models.ForumModel;
import Models.PostModel;
import Models.UserModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
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
            DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
            dos.write(message.getBytes("UTF-8"));
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String userName = matchWith("@(.*?)/");

        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Users.txt"))) {
            String line;
            boolean userFound = false;
            while ((line = br.readLine()) != null) {
                String UN=stringMatchWith(line, "\"\\\"userName\\\":\\\"(.*?)\\\"\"");
                if(userName.equals(UN)) {
                    sendMessage(line);
                    userFound = true;
                    break;
                }
            }
            if(!userFound) {
                sendMessage("UserDidNotfound");
            }
            else  {
                String outputForClient = GetPostsForUser(line);
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                dos.write(outputForClient.getBytes("UTF-8"));
                dos.close();
            }
        }catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
    private String GetPostsForUser (String userJson) {
        Gson gson = new Gson();
        UserModel userTempModel = gson.fromJson(userJson , UserModel.class);
        ArrayList<ForumModel> userForums = userTempModel.getFollowedForums();
        ArrayList<PostModel> postsForClient = new ArrayList<>(0);
        ArrayList<PostModel> tempForumPosts;
        for(ForumModel forum : userForums) {
            tempForumPosts = forum.getPosts();
            for (PostModel post : tempForumPosts){
                postsForClient.add(post);
            }
        }
        Collections.sort(postsForClient);
        return gson.toJson(postsForClient);
    }

}