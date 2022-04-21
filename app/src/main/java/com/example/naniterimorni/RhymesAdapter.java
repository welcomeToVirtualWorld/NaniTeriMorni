package com.example.naniterimorni;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RhymesAdapter extends RecyclerView.Adapter<RhymesAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Display_Rhymes> data;
    private List<Display_Rhymes> fulldate;
    private ClickListener listener;
    public RhymesAdapter(Context context, List<Display_Rhymes> data,ClickListener listener) {
        this.context = context;
        this.data = data;
        fulldate = new ArrayList<>(data);
        this.listener = listener;
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            //background thread
            List<Display_Rhymes> filtereddate = new ArrayList<>();
            if(charSequence == null || charSequence.length() == 0 )
            {
                filtereddate.addAll(fulldate);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Display_Rhymes data:fulldate)
                {
                    if(data.getTitle().toLowerCase().trim().contains(filterPattern))
                    {
                        filtereddate.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtereddate;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //ui thread
            data.clear();
            data.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };

    public interface ClickListener
    {
        void onRoWClick(Display_Rhymes data);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stories_horizontal,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.img_Rhymes.getContext())
                .load(data.get(position).getImage())
                .into(holder.img_Rhymes);
        holder.txt_title.setText(data.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Rhymes;
        TextView txt_title;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_Rhymes = itemView.findViewById(R.id.rhymesthumbimage);
            txt_title = itemView.findViewById(R.id.rhymestitle);
            relativeLayout = itemView.findViewById(R.id.rl);
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onRoWClick(data.get(getAdapterPosition()));
                }
            });
        }
    }
}
