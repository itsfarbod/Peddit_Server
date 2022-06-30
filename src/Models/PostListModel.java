package Models;

import java.util.ArrayList;

public class PostListModel {
    private ArrayList<PostModel> posts;

    public PostListModel(ArrayList<PostModel> posts) {
        this.posts = posts;
    }

    public ArrayList<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<PostModel> posts) {
        this.posts = posts;
    }
}
