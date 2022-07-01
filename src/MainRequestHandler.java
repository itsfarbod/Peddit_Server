import RequestHandlers.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainRequestHandler extends Thread{
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;

    public MainRequestHandler(Socket socket) {
        this.socket = socket;
        try {
            dis = new DataInputStream(socket.getInputStream());
            System.err.println("Data input stream created successfully!");
        } catch (IOException e) {
            System.err.println("Failed to make data input stream for socket : " + socket.toString());
        }
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            System.err.println("Data output stream created successfully!");
        } catch (IOException e) {
            System.err.println("Failed to make data output stream for socket : " + socket.toString());
        }
    }

    private String stringMatchWith(String str, String regex){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    String readString() throws IOException{
        StringBuilder ans = new StringBuilder();
        int i;
        while ((i=dis.read())!=0){
            ans.append((char) i);
        }
        return ans.toString();
    }


    @Override
    public void run() {
        String request = null;
        try {
            request = readString();
            System.err.println("Request received successfully!");
        } catch (IOException e) {
            System.err.println("Failed to receive request from client");
        }

//        String userName;
        String requestCommand = stringMatchWith(request, "/(.*?)#");
        String jsonString = stringMatchWith(request, "#(.*)");
        System.out.println(request);
        switch (requestCommand) {
            case "SignUp" :
                new SignUpRequestHandler(request , socket).start();
                break;
            case "LogIn" :
                new LoginRequestHandler(request , socket).start();
                break;
            case "ForumsPosts":
                new ForumsPostsRequest(request , socket).start();
                break;
            case "AllForumsList" :
                new AllForumsListRequestHandler(request , socket).start();
                break;
            case "StarredForumsList" :
                new StarredForumsListRequestHandler(request , socket).start();
                break;
            case "FollowedForumsList" :
                new FollowedForumsListRequestHandler(request , socket).start();
                break;
            case "SavedPosts" :
                new SavedPostsRequestHandler(request , socket).start();
                break;
            case "FeedPagePosts" :
                new FeedPagePostsRequestHandler(request , socket).start();
                break;
            case "GetUser" :
                new GetUserRequest(request , socket).start();
                break;
            case "CreateNewForum" :
                new CreateNewForumRequestHandler(request , socket).start();
                break;
            case "UpdateForums" :
                new UpdateForumObjectRequest(request , socket).start();
                break;
            case "GetDatas" :
                new SendDatasRequestHandler(request , socket).start();
                break;
            case "SendDatas" :
                new DatasUpdateRequestHandler(request , socket).start();
                break;
        }
    }
}
