package Models;

import java.util.ArrayList;

public class DatasModel {
    private ArrayList<ForumModel> forumsList;

    public DatasModel(ArrayList<ForumModel> forumsList) {
        this.forumsList = forumsList;
    }

    public ArrayList<ForumModel> getForumsList() {
        return forumsList;
    }

    public void setForumsList(ArrayList<ForumModel> forumsList) {
        this.forumsList = forumsList;
    }

    @Override
    public String toString() {
        return "DatasModel{" +
                "forumsList=" + forumsList +
                '}';
    }
}