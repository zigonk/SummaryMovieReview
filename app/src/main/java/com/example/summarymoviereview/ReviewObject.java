package com.example.summarymoviereview;

import com.google.api.services.language.v1beta2.model.Entity;

import java.util.ArrayList;

public class ReviewObject {
    public String ID;
    public String author;
    public String content;
    public String url;
    public ArrayList<Entity> entities;
    public float sentiment;

    public ReviewObject(String id, String author, String content, String url) {
        this.ID = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }
}
