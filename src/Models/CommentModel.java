package Models;

import java.util.ArrayList;

public class CommentModel {
    UserModel publisher;
    String publishTime;
    String commentDesc;
    ArrayList<UserModel> upVotedUsers;
    ArrayList<UserModel> downVotedUsers;
    ArrayList<CommentModel> repliedComments;

    public CommentModel(UserModel publisher, String publishTime, String commentDesc, ArrayList<UserModel> upVotedUsers, ArrayList<UserModel> downVotedUsers, ArrayList<CommentModel> repliedComments) {
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.commentDesc = commentDesc;
        this.upVotedUsers = upVotedUsers;
        this.downVotedUsers = downVotedUsers;
        this.repliedComments = repliedComments;
    }
}
