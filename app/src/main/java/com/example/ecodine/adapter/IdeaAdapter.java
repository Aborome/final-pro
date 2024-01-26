package com.example.ecodine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecodine.R;
import com.example.ecodine.entity.Idea;

import java.util.ArrayList;

public class IdeaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Idea> ideas;
    private Context context;
    public IdeaAdapter(Context context, ArrayList<Idea> ideas){
        this.context = context;
        this.ideas = ideas;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.idea, parent, false);
        return new IdeaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Idea idea = getItem(position);
        IdeaViewHolder ideaViewHolder = (IdeaViewHolder) holder;

        ideaViewHolder.idea_TV_title.setText(idea.getTitle());
        ideaViewHolder.idea_TV_description.setText(idea.getDescription());
    }

    @Override
    public int getItemCount() {
        return ideas.size();
    }

    public Idea getItem(int i){
        return this.ideas.get(i);
    }

    public class IdeaViewHolder extends  RecyclerView.ViewHolder{
        public TextView idea_TV_title;
        public TextView idea_TV_description;
        public IdeaViewHolder(@NonNull View itemView) {
            super(itemView);
            idea_TV_title = itemView.findViewById(R.id.idea_TV_title);
            idea_TV_description = itemView.findViewById(R.id.idea_TV_description);
        }
    }
}
