package com.eftar.eftarapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eftar.eftarapplication.R;
import com.eftar.eftarapplication.model.Video;

import java.util.ArrayList;
import java.util.List;

public class YoutubeRecyclerViewAdapter extends RecyclerView.Adapter<YoutubeRecyclerViewAdapter.MyViewHolder>{
    List<Video> videoList = new ArrayList<>();
    Context context;
    int numToShowVideo;
    public YoutubeRecyclerViewAdapter(List<Video> videoList, Context context, int numToShowVideo) {
        this.videoList = videoList;
        this.context = context;
        this.numToShowVideo = numToShowVideo;
    }

    @NonNull
    @Override
    public YoutubeRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_video,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YoutubeRecyclerViewAdapter.MyViewHolder holder, int position) {



        holder.txtTitle.setText(videoList.get(position).getTitle());
        holder.txtChannelName.setText(videoList.get(position).getChannelTitle());
        holder.txtViews.setText("");
        Glide.with(context)
                .asBitmap()
                .load(videoList.get(position).getUrl())
                .into(holder.imageView);

            holder.imageView.setOnClickListener(e ->{
                String url = "https://www.youtube.com/watch?v="+videoList.get(position).getVideoId();
                // Create the intent
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                // Start the activity
                context.startActivity(intent);


                // Verify if there's an app available to handle the intent
               /* if (intent.resolveActivity(context.getPackageManager()) != null) {

                }*/
            });

    }

    @Override
    public int getItemCount() {
        return Math.min(videoList.size(),numToShowVideo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;
        private TextView txtTitle,txtChannelName,txtViews;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtChannelName = itemView.findViewById(R.id.txtChannelName);
            txtViews = itemView.findViewById(R.id.txtViews);
        }
    }
}
