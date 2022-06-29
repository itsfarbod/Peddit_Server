import Models.UserModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Properties;

public class Test {
    public static void main(String[] args) {
        Gson gson = new Gson();
        UserModel um = gson.fromJson("{\"userName\":\"username\",\"email\":\"email@site.com\",\"password\":\"Aaaa1234\",\"followedForums\":[],\"commentsCount\":0,\"userPostsCount\":0,\"savedPosts\":[],\"starredForums\":[],\"userProfileImage\":\"\"}" , UserModel.class);
        System.out.println(um.toString());

        JsonObject jsonObject = JsonParser.parseString("{\"userName\":\"username\",\"email\":\"email@site.com\",\"password\":\"Aaaa1234\",\"followedForums\":[],\"commentsCount\":0,\"userPostsCount\":0,\"savedPosts\":[],\"starredForums\":[],\"userProfileImage\":\"\"}").getAsJsonObject();
        String imgurl = jsonObject.get("savedPosts").getAsString();
        System.out.println(imgurl);
    }
}