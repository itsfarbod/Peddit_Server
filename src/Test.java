import Models.ForumModel;
import Models.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        ForumModel fm = gson.fromJson("{\"forumName\":\"name1\",\"owner\":{\"userName\":\"username\",\"email\":\"email@site.com\",\"password\":\"Aaaa1234\",\"followedForums\":[],\"commentsCount\":0,\"userPostsCount\":0,\"savedPosts\":[],\"starredForums\":[],\"userProfileImage\":\"\"},\"admins\":[],\"blockedUsers\":[],\"members\":[],\"posts\":[],\"forumDesc\":\"\",\"forumCreateTime\":\"2022-06-29T17:32:58.272700\",\"profileImage\":\"\",\"headerImage\":\"\"}\n" , ForumModel.class);
        System.out.println(fm.toString());
    }
}