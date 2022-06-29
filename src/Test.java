import Models.UserModel;
import com.google.gson.Gson;

public class Test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        UserModel um = gson.fromJson("{\"userName\":\"username\",\"email\":\"email@site.com\",\"password\":\"Aaaa1234\",\"followedForums\":[],\"commentsCount\":0,\"userPostsCount\":0,\"savedPosts\":[],\"starredForums\":[],\"userProfileImage\":\"\"}" , UserModel.class);
        System.out.println(um.toString());
    }
}
