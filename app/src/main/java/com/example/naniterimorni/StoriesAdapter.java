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
class StoriesAdapter extends RecyclerView.Adapter<StoriesAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<Datum> data;
    private List<Datum> fulldata;
    private ClickListener listener;

    public StoriesAdapter(Context context, List<Datum> data, ClickListener listener) {
        this.context = context;
        this.data = data;
        fulldata = new ArrayList<>(data);
        this.listener = listener;
    }
    public interface ClickListener
    {
        void onRoWClick(Datum data);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stories,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(holder.img_rhymesthumb.getContext())
                .load(data.get(position).getStoryImage())
                .into(holder.img_rhymesthumb);
        holder.txt_title.setText(data.get(position).getStoryTitle());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_rhymesthumb;
        TextView txt_title;
        RelativeLayout relativeLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_rhymesthumb = itemView.findViewById(R.id.rhymesthumbimage);
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
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            //background thread
            List<Datum> filtereddata = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0)
            {
                filtereddata.addAll(fulldata);
            }
            else
            {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for(Datum data : fulldata)
                {
                    if(data.getStoryTitle().toLowerCase().contains(filterPattern)){
                        filtereddata.add(data);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtereddata;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            //Ui thread
            data.clear();
            data.addAll((List)filterResults.values);
            notifyDataSetChanged();
        }
    };
}