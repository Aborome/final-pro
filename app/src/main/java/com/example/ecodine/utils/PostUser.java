package com.example.ecodine.utils;

import com.example.ecodine.entity.Post;
import com.example.ecodine.entity.User;

public class PostUser extends Post {
    private User user;

    public PostUser() {}

    public PostUser(User user, Post post){
        this.user = user;
        this.setUid(post.getUid());
        this.setUserId(post.getUserId());
        this.setDate(post.getDate());
        this.setPost(post.getPost());
    }

    public User getUser() {
        return user;
    }

    public PostUser setUser(User user) {
        this.user = user;
        return this;
    }
}
