package com.example.ecodine.controller;

import androidx.annotation.NonNull;

import com.example.ecodine.callBack.AuthCallBack;
import com.example.ecodine.entity.AuthUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthController {
    private FirebaseAuth mAuth;
    private AuthCallBack authCallBack;

    public AuthController(){
        mAuth = FirebaseAuth.getInstance();
    }

    public void setAuthCallBack(AuthCallBack authCallBack){
        this.authCallBack = authCallBack;
    }

    public void login(AuthUser authUser){
        mAuth.signInWithEmailAndPassword(authUser.getEmail(), authUser.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        authCallBack.onLoginComplete(task);

                    }
                });
    }
    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void createAccount(AuthUser authUser){

        mAuth.createUserWithEmailAndPassword(authUser.getEmail(), authUser.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   authCallBack.
                           onCreateAccountComplete(true, "create account success");
               }else {
                    String error = task.getException().getMessage().toString();
                    authCallBack.onCreateAccountComplete(false, error);
               }
            }
        });

    }

    public void sendResetPasswordEmail(String email){
        mAuth.sendPasswordResetEmail(email);
    }

    public void logout(){
        mAuth.signOut();
    }
}
