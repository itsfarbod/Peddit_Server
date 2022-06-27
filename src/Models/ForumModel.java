package Models;

import java.util.ArrayList;

public class ForumModel {
    UserModel owner;
    ArrayList<UserModel> admins;
    ArrayList<PostModel> posts;
    ArrayList<UserModel> members;
    String forumName;
    String forumDesc;
    String forumCreateTime;
    String profileImage;
    String headerImage;

    public ForumModel(UserModel owner, ArrayList<UserModel> admins, ArrayList<PostModel> posts, ArrayList<UserModel> members, String forumName, String forumDesc, String forumCreateTime, String profileImage, String headerImage) {
        this.owner = owner;
        this.admins = admins;
        this.posts = posts;
        this.members = members;
        this.forumName = forumName;
        this.forumDesc = forumDesc;
        this.forumCreateTime = forumCreateTime;
        this.profileImage = profileImage;
        this.headerImage = headerImage;
    }
}
