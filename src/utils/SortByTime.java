package utils;

import Models.PostModel;

import java.util.Comparator;

public class SortByTime implements Comparator<PostModel> {
    public int compare(PostModel a, PostModel b){
        return a.getPublishTime().compareTo(b.getPublishTime());
    }
}
