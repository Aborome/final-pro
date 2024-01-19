package com.example.ecodine.entity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class Post extends FirebaseUid implements Comparable<Post> {
    public static final String POST_TABLE = "Posts";
    private String post;
    private Date date;
    private String userId;

    public Post() {}

    public String getPost() {
        return post;
    }

    public Post setPost(String post) {
        this.post = post;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public Post setDate(Date date) {
        this.date = date;
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public Post setUserId(String userId) {
        this.userId = userId;
        return this;
    }
    public Task<Void> save(FirebaseFirestore db){
        return db.collection(POST_TABLE).document().set(this);
    }

    @Override
    public int compareTo(Post post) {
        return post.getDate().compareTo(this.date);
    }
}
