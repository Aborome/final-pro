package com.example.ecodine.controller;

import androidx.annotation.NonNull;

import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserController {
    private FirebaseFirestore database;
    private UserCallBack userCallBack;

    public UserController(){
        database = FirebaseFirestore.getInstance();
    }

    public void setUserCallBack(UserCallBack userCallBack){
        this.userCallBack = userCallBack;
    }

    public void saveUserData(User user) {
        user.save(database).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    userCallBack.onUserDataSaveComplete(true, "User data saved!");
                }else{
                    String error = task.getException().getMessage().toString();
                    userCallBack.onUserDataSaveComplete(false, error);
                }
            }
        });
    }

    public void updateUserData(){}

    public void getUserData(){}

}
