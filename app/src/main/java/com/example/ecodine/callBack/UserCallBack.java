package com.example.ecodine.callBack;


import com.example.ecodine.entity.User;

public interface UserCallBack {
    void onUserDataSaveComplete(boolean statue, String msg);
    void onFetchUserDataComplete(User user);
}
