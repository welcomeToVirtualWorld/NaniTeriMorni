package com.example.naniterimorni;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.naniterimorni.FavouriteList.DatabaseAdapter;
import com.example.naniterimorni.FavouriteList.Favourite;

import java.util.List;

public class ShowFavouriteList extends AppCompatActivity implements FavouriteListAdapter.ClickListener {
    RecyclerView recyclerView;
    FavouriteListAdapter favouriteListAdapter;
    DatabaseAdapter databaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colororange));
        }
        databaseAdapter = new DatabaseAdapter(ShowFavouriteList.this);
        setContentView(R.layout.activity_show_favourite_list);
        recyclerView = findViewById(R.id.recycleFavourite);
        setData();
    }

    private void setData() {
        List<Favourite> data = databaseAdapter.getAllList();
        recyclerView.setLayoutManager(new GridLayoutManager(ShowFavouriteList.this,2));
        favouriteListAdapter = new FavouriteListAdapter(ShowFavouriteList.this,data,ShowFavouriteList.this);
        recyclerView.setAdapter(favouriteListAdapter);
    }

    @Override
    public void onDeleteClick(String id, int position) {
        int count = databaseAdapter.deleteData(id);
        if(count>0)
        {
            favouriteListAdapter.deleteValue(position);
        }
    }

    @Override
    public void onRowClick(Favourite favourite) {
        Intent intent = new Intent(ShowFavouriteList.this,Display_Stories.class);
        intent.putExtra("Image",favourite.getImage());
        intent.putExtra("Desc",favourite.getDescription());
        intent.putExtra("Title",favourite.getTitle());
        startActivity(intent);
    }
}
