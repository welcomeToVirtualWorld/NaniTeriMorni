package com.example.naniterimorni;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.naniterimorni.FavouriteList.Favourite;

import java.util.List;

public class FavouriteListAdapter extends RecyclerView.Adapter<FavouriteListAdapter.MyViewHolder> {
    private Context context;
    private List<Favourite> data;
    private ClickListener listener;

    public FavouriteListAdapter(Context context, List<Favourite> data,ClickListener listener) {
        this.context = context;
        this.data = data;
        this.listener = listener;
    }

    interface ClickListener
    {
        void onDeleteClick(String id,int position);
        void onRowClick(Favourite favourite);
    }
    public void deleteValue(int position)
    {
        data.remove(position);
        notifyItemRemoved(position);
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.favourite_adapter,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            Glide.with(holder.img_favourite.getContext())
                    .load(data.get(position).getImage())
                    .into(holder.img_favourite);
            holder.txt_title.setText(data.get(position).getTitle());
        }
        catch(Exception e) {
            Log.e("Error",e.getLocalizedMessage());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_favourite;
        TextView txt_title;
        Button btn_delete;
        LinearLayout linearLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_favourite = itemView.findViewById(R.id.favouriteStoryImage);
            txt_title = itemView.findViewById(R.id.favouriteStoryTitle);
            btn_delete = itemView.findViewById(R.id.delete);
            linearLayout = itemView.findViewById(R.id.llfavourite);
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDeleteClick(String.valueOf(data.get(getAdapterPosition()).getId()),getAdapterPosition());
                }
            });
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRowClick(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
