package com.example.ecodine.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ecodine.R;
import com.example.ecodine.entity.Post;
import com.example.ecodine.utils.Generic;
import com.example.ecodine.utils.PostRestaurant;
import com.example.ecodine.utils.PostUser;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<Post> posts;
    private Context context;

    public PostAdapter(Context context, ArrayList<Post> posts){
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Post post = getItem(position);
        PostViewHolder postViewHolder = (PostViewHolder) holder;

        if(post instanceof PostUser){
            // user post
            PostUser p = (PostUser) post;
            postViewHolder.post_TV_name.setText(p.getUser().getFullName());
            if(p.getUser().getImageUrl() != null){
                Glide.with(context).load(p.getUser().getImageUrl()).into(postViewHolder.post_IV_profileImage);
            }
        }else{
            // restaurant post
            PostRestaurant p = (PostRestaurant) post;
            postViewHolder.post_TV_name.setText(p.getRestaurant().getName());
            if(p.getRestaurant().getImageUrl() != null){
                Glide.with(context).load(p.getRestaurant().getImageUrl()).into(postViewHolder.post_IV_profileImage);
            }
        }

        postViewHolder.post_TV_date.setText(Generic.formatDate(post.getDate()));
        postViewHolder.post_TV_post.setText(post.getPost());
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public Post getItem(int i){
        return posts.get(i);
    }

    public class PostViewHolder extends  RecyclerView.ViewHolder{
        public CircleImageView post_IV_profileImage;
        public TextView post_TV_name;
        public TextView post_TV_date;
        public TextView post_TV_post;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            post_IV_profileImage = itemView.findViewById(R.id.post_IV_profileImage);
            post_TV_name = itemView.findViewById(R.id.post_TV_name);
            post_TV_date = itemView.findViewById(R.id.post_TV_date);
            post_TV_post = itemView.findViewById(R.id.post_TV_post);

        }
    }
}
