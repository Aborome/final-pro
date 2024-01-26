package com.example.ecodine.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ecodine.R;
import com.example.ecodine.adapter.IdeaAdapter;
import com.example.ecodine.callBack.IdeaCallBack;
import com.example.ecodine.controller.IdeaController;
import com.example.ecodine.entity.Idea;

import java.util.ArrayList;


public class IdeasFragment extends Fragment {

    private RecyclerView fIdeas_RV_ideas;
    private Context context;
    private IdeaController ideaController;
    public IdeasFragment( Context context) {
        // Required empty public constructor
        this.context = context;
        ideaController = new IdeaController();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ideas, container, false);
        findViews(view);
        initVars();
        return view;
    }

    private void initVars() {
        ideaController.setIdeaCallBack(new IdeaCallBack() {
            @Override
            public void onFetchIdeasComplete(ArrayList<Idea> ideas) {
                IdeaAdapter ideaAdapter = new IdeaAdapter(context, ideas);
                fIdeas_RV_ideas.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                fIdeas_RV_ideas.setHasFixedSize(true);
                fIdeas_RV_ideas.setItemAnimator(new DefaultItemAnimator());
                fIdeas_RV_ideas.setAdapter(ideaAdapter);
            }
        });

        ideaController.fetchIdeas();
    }

    private void findViews(View view) {
        fIdeas_RV_ideas = view.findViewById(R.id.fIdeas_RV_ideas);
    }
}