package com.presto.gallery;

/**
 * Created by Yvonne on 2018-09-19.
 */

public class Photo {
    public final String id;
    public final String title;
    public int width;
    public int height;

    Photo(String id, String title) {
        this.id = id;
        this.title = title;
    }
    private void setSize( int w, int h){
        this.height = h;
        this.width = w;
    }
}
