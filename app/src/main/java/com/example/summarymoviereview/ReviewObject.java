package com.example.summarymoviereview;

public class ReviewObject {
    public String ID;
    public String author;
    public String content;
    public String url;

    public ReviewObject(String id, String author, String content, String url) {
        this.ID = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }
}
