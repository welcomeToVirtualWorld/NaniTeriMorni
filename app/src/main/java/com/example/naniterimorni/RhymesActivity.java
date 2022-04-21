package com.example.naniterimorni;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RhymesActivity extends AppCompatActivity implements RhymesAdapter.ClickListener {
    RecyclerView recyclerView,recommendedView;
    RhymesAdapter rhymesAdapter;
    ImageView nav_Stories;
    MenuItem searchItem;
    ProgressDialog progressDialog;
    androidx.appcompat.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rhymes);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colororange));
        }
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        recyclerView = findViewById(R.id.rhym_1);
        recommendedView = findViewById(R.id.recommend_rhymes);
        getDate();
        nav_Stories = findViewById(R.id.nav_Stories);
        nav_Stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RhymesActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        progressDialog = new ProgressDialog(RhymesActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        //goStories();

    }
    private void getDate()
    {
        Call<List<Display_Rhymes>> call = ApiClient2.getApiService2().getVideosData();
        call.enqueue(new Callback<List<Display_Rhymes>>() {
            @Override
            public void onResponse(Call<List<Display_Rhymes>> call, Response<List<Display_Rhymes>> response) {
                progressDialog.dismiss();
                List<Display_Rhymes> data = response.body();
                rhymesAdapter = new RhymesAdapter(RhymesActivity.this,data,RhymesActivity.this);
                recyclerView.setLayoutManager(new LinearLayoutManager(RhymesActivity.this,RecyclerView.HORIZONTAL,false));
                recyclerView.setAdapter(rhymesAdapter);
                recommendedView.setLayoutManager(new GridLayoutManager(RhymesActivity.this,2));
               recommendedView.setAdapter(rhymesAdapter);
            }

            @Override
            public void onFailure(Call<List<Display_Rhymes>> call, Throwable t) {

            }
        });
    }
    @Override
    public void onRoWClick(Display_Rhymes data) {
        Intent intent = new Intent(RhymesActivity.this,VideoActivity.class);
        intent.putExtra("url",data.getUrl());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        searchItem = menu.findItem(R.id.search);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.search:
                SearchView searchView = (SearchView) searchItem.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        rhymesAdapter.getFilter().filter(s);
                        return false;
                    }
                });
                break;
            case R.id.profile:
                Intent intent = new Intent(RhymesActivity.this,Profile.class);
                startActivity(intent);
                break;
            case R.id.favourite:
                Intent intent2 = new Intent(RhymesActivity.this,ShowFavouriteList.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
