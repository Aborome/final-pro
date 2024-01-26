package com.example.ecodine.controller;

import androidx.annotation.Nullable;

import com.example.ecodine.callBack.IdeaCallBack;
import com.example.ecodine.entity.Idea;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class IdeaController {
    private FirebaseFirestore database;
    private IdeaCallBack ideaCallBack;
    public IdeaController(){
        database = FirebaseFirestore.getInstance();
    }

    public void setIdeaCallBack(IdeaCallBack ideaCallBack){
        this.ideaCallBack = ideaCallBack;
    }

    public void fetchIdeas(){
        database.collection(Idea.IDEAS_TABLE).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                ArrayList<Idea> ideas = new ArrayList<>();
                for(DocumentSnapshot snapshot : value.getDocuments()){
                    Idea idea = snapshot.toObject(Idea.class);
                    idea.setUid(snapshot.getId());
                    ideas.add(idea);
                }
                ideaCallBack.onFetchIdeasComplete(ideas);
            }
        });
    }

}
