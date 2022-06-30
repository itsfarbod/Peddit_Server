package RequestHandlers;

import Models.ForumListModel;
import Models.ForumModel;
import Models.UserModel;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FollowedForumsListRequestHandler extends Thread{
    String request;
    Socket socket;

    public FollowedForumsListRequestHandler(String request, Socket socket) {
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
            Gson gson = new Gson();
            boolean flag = false;
            regex = "\"\\\"userName\\\":\\\"(.*?)\\\"\"";
            while ((line = br.readLine()) != null) {
                pattern = Pattern.compile(regex);
                matcher = pattern.matcher(line);
                UN = matcher.group(1);
                if (userName.equals(UN)) {

//                    UserModel um = gson.fromJson(line.trim() , UserModel.class);
//                    allFollowedForums.setForums(gson.fromJson(getForumsForUser(line.trim())));
                    DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                    dos.write(gson.toJson(getForumsForUser(line.trim())).getBytes());
                    flag = true;
                    break;
                }
            }
            if (flag == false) {
                DataOutputStream dos = (DataOutputStream) socket.getOutputStream();
                String message = "UserDidNotfound";
                byte[] messageBytes = message.getBytes("UTF-8");
                dos.write(messageBytes);
                dos.close();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private ForumListModel getForumsForUser(String userJson) {
        Gson gson = new Gson();
        UserModel userTempModel = gson.fromJson(userJson, UserModel.class);
        ArrayList<ForumModel> userFollowedForums = userTempModel.getFollowedForums();
        ArrayList<String> userForumsNames = new ArrayList<>(0);
        for (ForumModel forum : userFollowedForums) {
            userForumsNames.add(forum.getForumName());
        }
        userFollowedForums = new ArrayList<>(0);
        for (String FN : userForumsNames) {
            userFollowedForums.add(getForumObjectFromJsonString(FN));
        }
        ForumListModel forumListObj = new ForumListModel();
        forumListObj.setForums(userFollowedForums);
        return forumListObj;
    }

    private ForumModel getForumObjectFromJsonString(String forumName) {
        Gson gson = new Gson();
        Pattern pattern;
        Matcher matcher;
        try (BufferedReader br = new BufferedReader(new FileReader("./DataBase/Forums.txt"))) {
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
