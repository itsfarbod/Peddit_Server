package Models;

import java.util.ArrayList;

public class UserModel {
    String userName;
    String email;
    String password;
    ArrayList<ForumModel> followedForums;
    ArrayList<CommentModel> comments;
    ArrayList<PostModel> userPosts;
    ArrayList<PostModel> upVotedPosts;
    ArrayList<PostModel> downVotedPosts;
    ArrayList<PostModel> savedPosts;
    ArrayList<CommentModel> likedComments;
    ArrayList<CommentModel> disLikedComments;
    ArrayList<ForumModel> starredForums;
    String userProfileImage;

    public UserModel(String userName, String email, String password, ArrayList<ForumModel> followedForums, ArrayList<CommentModel> comments, ArrayList<PostModel> userPosts, ArrayList<PostModel> upVotedPosts, ArrayList<PostModel> downVotedPosts, ArrayList<PostModel> savedPosts, ArrayList<CommentModel> likedComments, ArrayList<CommentModel> disLikedComments, ArrayList<ForumModel> starredForums, String userProfileImage) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.followedForums = followedForums;
        this.comments = comments;
        this.userPosts = userPosts;
        this.upVotedPosts = upVotedPosts;
        this.downVotedPosts = downVotedPosts;
        this.savedPosts = savedPosts;
        this.likedComments = likedComments;
        this.disLikedComments = disLikedComments;
        this.starredForums = starredForums;
        this.userProfileImage = userProfileImage;
    }
}
