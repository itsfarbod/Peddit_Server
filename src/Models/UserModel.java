package Models;

import java.util.ArrayList;

public class UserModel {
    String userName;
    String email;
    String password;
    ArrayList<ForumModel> followedForums;
    int commentsCount;
    int userPostsCount;
    ArrayList<PostModel> savedPosts;
    ArrayList<ForumModel> starredForums;
    String userProfileImage;

    public UserModel(String userName, String email, String password, ArrayList<ForumModel> followedForums, int commentsCount, int userPostsCount, ArrayList<PostModel> savedPosts, ArrayList<ForumModel> starredForums, String userProfileImage) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.followedForums = followedForums;
        this.commentsCount = commentsCount;
        this.userPostsCount = userPostsCount;
        this.savedPosts = savedPosts;
        this.starredForums = starredForums;
        this.userProfileImage = userProfileImage;
    }
}
