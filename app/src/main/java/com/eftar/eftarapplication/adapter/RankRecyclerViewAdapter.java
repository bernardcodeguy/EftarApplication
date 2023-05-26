package com.eftar.eftarapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.DecimalFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.eftar.eftarapplication.IndividualBondDetailActivity;
import com.eftar.eftarapplication.R;
import com.eftar.eftarapplication.model.Bond;



import java.util.ArrayList;
import java.util.List;

public class RankRecyclerViewAdapter extends RecyclerView.Adapter<RankRecyclerViewAdapter.MyViewHolder>{
    List<Bond> rankList = new ArrayList<>();
    Context context;
    private int numItemsToShow;

    public RankRecyclerViewAdapter(List<Bond> rankList, Context context, int numItemsToShow) {
        this.rankList = rankList;
        this.context = context;
        this.numItemsToShow = numItemsToShow;
    }

    @NonNull
    @Override
    public RankRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.one_line_rank,parent,false);
        RankRecyclerViewAdapter.MyViewHolder holder = new RankRecyclerViewAdapter.MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RankRecyclerViewAdapter.MyViewHolder holder, int position) {
        holder.txtRank.setText(rankList.get(position).getRank()+"");
        holder.txtBondName.setText(rankList.get(position).getIs_name());
        DecimalFormat decimalFormat = new DecimalFormat("#,###");
        holder.txtPrice.setText(decimalFormat.format(rankList.get(position).getPrice()));

        holder.imgSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, IndividualBondDetailActivity.class);
                intent.putExtra("is_name",rankList.get(position).getIs_name());
                // Start the activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            }
        });


        if(rankList.get(position).getChange() == 0.0){
            Glide.with(context)
                    .asBitmap()
                    .load(R.mipmap.neutral)
                    .into(holder.imgChange);
        }else if(rankList.get(position).getChange() < 0){
            Glide.with(context)
                    .asBitmap()
                    .load(R.mipmap.down)
                    .into(holder.imgChange);
        }else{
            Glide.with(context)
                    .asBitmap()
                    .load(R.mipmap.up)
                    .into(holder.imgChange);
        }
    }

    @Override
    public int getItemCount() {
        return Math.min(rankList.size(),numItemsToShow);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgChange,imgSelect;
        private TextView txtRank,txtBondName,txtPrice;
        private LinearLayout one_line_rank;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgChange = itemView.findViewById(R.id.imgChange);
            imgSelect = itemView.findViewById(R.id.imgSelect);
            txtRank = itemView.findViewById(R.id.txtRank);
            txtBondName = itemView.findViewById(R.id.txtBondName);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            one_line_rank = itemView.findViewById(R.id.one_line_rank);
        }
    }
}
