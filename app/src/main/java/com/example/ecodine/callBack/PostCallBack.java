package com.example.ecodine.callBack;

import com.example.ecodine.entity.Post;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;

public interface PostCallBack {
    void onPostSaveComplete(Task<Void> task);
    void onPostsFetchComplete(ArrayList<Post> posts);
}
