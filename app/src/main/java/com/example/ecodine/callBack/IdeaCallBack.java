package com.example.ecodine.callBack;

import com.example.ecodine.entity.Idea;

import java.util.ArrayList;

public interface IdeaCallBack {
    void onFetchIdeasComplete(ArrayList<Idea> ideas);
}
