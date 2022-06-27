package Models;

import java.util.ArrayList;

public class PostModel {
    String title;
    String desc;
    UserModel publisher;
    ForumModel forum;
    String publishTime;
    ArrayList<UserModel> upVotedUsers;
    ArrayList<UserModel> downVotedUsers;
    ArrayList<CommentModel> comments;
    String postImage;
    int commentsCount;

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
}
