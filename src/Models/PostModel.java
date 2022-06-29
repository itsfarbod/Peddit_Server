package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class PostModel implements Comparable<PostModel>{
    private String title;
    private String desc;
    private UserModel publisher;
    private ForumModel forum;
    private String publishTime;
    private ArrayList<UserModel> upVotedUsers;
    private ArrayList<UserModel> downVotedUsers;
    private ArrayList<CommentModel> comments;
    private String postImage;
    private int commentsCount;

    public PostModel(String title, String desc, UserModel publisher, ForumModel forum, String publishTime, ArrayList<UserModel> upVotedUsers, ArrayList<UserModel> downVotedUsers, ArrayList<CommentModel> comments, String postImage, int commentsCount) {
        this.title = title;
        this.desc = desc;
        this.publisher = publisher;
        this.forum = forum;
        this.publishTime = publishTime;
        this.upVotedUsers = upVotedUsers;
        this.downVotedUsers = downVotedUsers;
        this.comments = comments;
        this.postImage = postImage;
        this.commentsCount = commentsCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public UserModel getPublisher() {
        return publisher;
    }

    public void setPublisher(UserModel publisher) {
        this.publisher = publisher;
    }

    public ForumModel getForum() {
        return forum;
    }

    public void setForum(ForumModel forum) {
        this.forum = forum;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public ArrayList<UserModel> getUpVotedUsers() {
        return upVotedUsers;
    }

    public void setUpVotedUsers(ArrayList<UserModel> upVotedUsers) {
        this.upVotedUsers = upVotedUsers;
    }

    public ArrayList<UserModel> getDownVotedUsers() {
        return downVotedUsers;
    }

    public void setDownVotedUsers(ArrayList<UserModel> downVotedUsers) {
        this.downVotedUsers = downVotedUsers;
    }

    public ArrayList<CommentModel> getComments() {
        return comments;
    }

    public void setComments(ArrayList<CommentModel> comments) {
        this.comments = comments;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", publisher=" + publisher +
                ", forum=" + forum +
                ", publishTime='" + publishTime + '\'' +
                ", upVotedUsers=" + upVotedUsers +
                ", downVotedUsers=" + downVotedUsers +
                ", comments=" + comments +
                ", postImage='" + postImage + '\'' +
                ", commentsCount=" + commentsCount +
                '}';
    }

    @Override
    public int compareTo(PostModel postModel) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        LocalDate thisDate = LocalDate.parse(this.publishTime.substring(0,19), formatter);
        LocalDate postModelDate = LocalDate.parse(postModel.publishTime.substring(0,19), formatter);
        return thisDate.compareTo(postModelDate);
    }
}
