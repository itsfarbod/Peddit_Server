package Models;

import java.util.ArrayList;

public class UserModel {
    private String userName;
    private String email;
    private String password;
    private ArrayList<ForumModel> followedForums;
    private int commentsCount;
    private int userPostsCount;
    private ArrayList<PostModel> savedPosts;
    private ArrayList<ForumModel> starredForums;
    private String userProfileImage;

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<ForumModel> getFollowedForums() {
        return followedForums;
    }

    public void setFollowedForums(ArrayList<ForumModel> followedForums) {
        this.followedForums = followedForums;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getUserPostsCount() {
        return userPostsCount;
    }

    public void setUserPostsCount(int userPostsCount) {
        this.userPostsCount = userPostsCount;
    }

    public ArrayList<PostModel> getSavedPosts() {
        return savedPosts;
    }

    public void setSavedPosts(ArrayList<PostModel> savedPosts) {
        this.savedPosts = savedPosts;
    }

    public ArrayList<ForumModel> getStarredForums() {
        return starredForums;
    }

    public void setStarredForums(ArrayList<ForumModel> starredForums) {
        this.starredForums = starredForums;
    }

    public String getUserProfileImage() {
        return userProfileImage;
    }

    public void setUserProfileImage(String userProfileImage) {
        this.userProfileImage = userProfileImage;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", followedForums=" + followedForums +
                ", commentsCount=" + commentsCount +
                ", userPostsCount=" + userPostsCount +
                ", savedPosts=" + savedPosts +
                ", starredForums=" + starredForums +
                ", userProfileImage='" + userProfileImage + '\'' +
                '}';
    }
}
