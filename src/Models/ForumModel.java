package Models;

import java.util.ArrayList;

public class ForumModel {
    private String forumName;
    private UserModel owner;
    private ArrayList<UserModel> admins;
    private ArrayList<UserModel> blockedUsers;
    private ArrayList<UserModel> members;
    private ArrayList<PostModel> posts;
    private String forumDesc;
    private String forumCreateTime;
    private String profileImage;
    private String headerImage;

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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public ArrayList<UserModel> getAdmins() {
        return admins;
    }

    public void setAdmins(ArrayList<UserModel> admins) {
        this.admins = admins;
    }

    public ArrayList<UserModel> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(ArrayList<UserModel> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public ArrayList<UserModel> getMembers() {
        return members;
    }

    public void setMembers(ArrayList<UserModel> members) {
        this.members = members;
    }

    public ArrayList<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    public String getForumName() {
        return forumName;
    }

    public void setForumName(String forumName) {
        this.forumName = forumName;
    }

    public String getForumDesc() {
        return forumDesc;
    }

    public void setForumDesc(String forumDesc) {
        this.forumDesc = forumDesc;
    }

    public String getForumCreateTime() {
        return forumCreateTime;
    }

    public void setForumCreateTime(String forumCreateTime) {
        this.forumCreateTime = forumCreateTime;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getHeaderImage() {
        return headerImage;
    }

    public void setHeaderImage(String headerImage) {
        this.headerImage = headerImage;
    }

    public void addPost(PostModel post) {
        this.posts.add(post);
    }

    @Override
    public String toString() {
        return "ForumModel{" +
                "owner=" + owner +
                ", admins=" + admins +
                ", blockedUsers=" + blockedUsers +
                ", members=" + members +
                ", posts=" + posts +
                ", forumName='" + forumName + '\'' +
                ", forumDesc='" + forumDesc + '\'' +
                ", forumCreateTime='" + forumCreateTime + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", headerImage='" + headerImage + '\'' +
                '}';
    }
}
