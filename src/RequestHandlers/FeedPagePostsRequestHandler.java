package RequestHandlers;

import Models.ForumModel;
import Models.PostModel;
import Models.UserModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
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
//                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
//                    byte[] messageBytes = line.getBytes("UTF-8");
//                    dos.write(messageBytes);
//                    dos.close();
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