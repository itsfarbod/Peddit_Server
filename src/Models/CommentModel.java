package Models;

import java.util.ArrayList;

public class CommentModel {
    private UserModel publisher;
    private String publishTime;
    private String commentDesc;
    private ArrayList<UserModel> upVotedUsers;
    private ArrayList<UserModel> downVotedUsers;
    private ArrayList<CommentModel> repliedComments;

    public CommentModel(UserModel publisher, String publishTime, String commentDesc, ArrayList<UserModel> upVotedUsers, ArrayList<UserModel> downVotedUsers, ArrayList<CommentModel> repliedComments) {
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.commentDesc = commentDesc;
        this.upVotedUsers = upVotedUsers;
        this.downVotedUsers = downVotedUsers;
        this.repliedComments = repliedComments;
    }


    public UserModel getPublisher() {
        return publisher;
    }

    public void setPublisher(UserModel publisher) {
        this.publisher = publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getCommentDesc() {
        return commentDesc;
    }

    public void setCommentDesc(String commentDesc) {
        this.commentDesc = commentDesc;
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

    public ArrayList<CommentModel> getRepliedComments() {
        return repliedComments;
    }

    public void setRepliedComments(ArrayList<CommentModel> repliedComments) {
        this.repliedComments = repliedComments;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "publisher=" + publisher +
                ", publishTime='" + publishTime + '\'' +
                ", commentDesc='" + commentDesc + '\'' +
                ", upVotedUsers=" + upVotedUsers +
                ", downVotedUsers=" + downVotedUsers +
                ", repliedComments=" + repliedComments +
                '}';
    }
}
