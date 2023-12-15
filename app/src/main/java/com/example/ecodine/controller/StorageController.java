package com.example.ecodine.controller;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;


public class StorageController {

    FirebaseStorage storage;

    public StorageController(){
        storage = FirebaseStorage.getInstance();
    }

    public String downloadImageUrl(String imagePath){
        Task<Uri> downloadImageTask = storage.getReference().child(imagePath).getDownloadUrl();
        while (!downloadImageTask.isComplete() && !downloadImageTask.isCanceled());
        return downloadImageTask.getResult().toString();
    }
}
