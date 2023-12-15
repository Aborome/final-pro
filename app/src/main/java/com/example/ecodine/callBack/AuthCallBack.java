package com.example.ecodine.callBack;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

public interface AuthCallBack {
    void onCreateAccountComplete(boolean status, String msg);
    void onLoginComplete( Task<AuthResult> task);

}
