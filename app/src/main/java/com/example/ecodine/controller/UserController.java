package com.example.ecodine.controller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ecodine.callBack.UserCallBack;
import com.example.ecodine.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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
        user.save(database).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    userCallBack.onUserDataSaveComplete(true, "");
                }else {
                    userCallBack.onUserDataSaveComplete(false, task.getException().getMessage().toString());
                }
            }
        });
    }

    public void updateRestaurantId(String uid, String restaurantId){
        database.collection(User.UserTable).document(uid).update("restaurantId", restaurantId);
    }

    public void getUserData(String uid){
        StorageController storageController = new StorageController();
        database.collection(User.UserTable).document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null){
                    User user = value.toObject(User.class);
                    if(user.getImagePath() != null){
                        String imageUrl = storageController.downloadImageUrl(user.getImagePath());
                        user.setImageUrl(imageUrl);
                    }
                    user.setUid(value.getId());
                    userCallBack.onFetchUserDataComplete(user);
                }
            }
        });
    }

    public User getUserDataSync(String uid){
        StorageController storageController = new StorageController();
        Task<DocumentSnapshot> task = database.collection(User.UserTable).document(uid).get() ;
        while (!task.isComplete());
        if(task.getResult() == null)
            return null;
        User user = task.getResult().toObject(User.class);
        if(user.getImagePath() != null){
            String imageUrl = storageController.downloadImageUrl(user.getImagePath());
            user.setImageUrl(imageUrl);
        }
        user.setUid(task.getResult().getId());
        return user;
    }
}
