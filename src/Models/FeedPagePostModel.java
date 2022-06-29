package Models;

import java.util.ArrayList;

public class FeedPagePostModel{
    private ArrayList<PostModel> posts;

    public FeedPagePostModel(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    public ArrayList<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
    }
}
