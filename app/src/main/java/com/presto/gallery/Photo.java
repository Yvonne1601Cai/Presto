package com.presto.gallery;

public class Photo {
    public final String id;
    public final String title;
    String width;
    String height;
    String dimemsion;
    String smallImageUrl;
    String fullImageUrl;


    Photo(String id, String title,String dimemsion) {
        this.id = id;
        this.title = title;
        this.dimemsion = dimemsion;
    }




}
