package Models;

import java.util.ArrayList;

public class ForumModel {
    UserModel owner;
    ArrayList<UserModel> admins;
    ArrayList<UserModel> blockedUsers;
    ArrayList<UserModel> members;
    ArrayList<PostModel> posts;
    String forumName;
    String forumDesc;
    String forumCreateTime;
    String profileImage;
    String headerImage;

    public ForumModel(UserModel owner, ArrayList<UserModel> admins, ArrayList<UserModel> blockedUsers, ArrayList<UserModel> members, ArrayList<PostModel> posts, String forumName, String forumDesc, String forumCreateTime, String profileImage, String headerImage) {
        this.owner = owner;
        this.admins = admins;
        this.blockedUsers = blockedUsers;
        this.members = members;
        this.posts = posts;
        this.forumName = forumName;
        this.forumDesc = forumDesc;
        this.forumCreateTime = forumCreateTime;
        this.profileImage = profileImage;
        this.headerImage = headerImage;
    }
}
