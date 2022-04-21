package com.example.naniterimorni;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity<orientation> extends AppCompatActivity implements StoriesAdapter.ClickListener {
    RecyclerView recyclerView,recommend;
    StoriesAdapter storiesAdapter;
    StoriesAdapterHorizontal storiesAdapterHorizontal;
    ImageView img_rhymes;
    ProgressDialog progressDialog;
    MenuItem searchItem;
    androidx.appcompat.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colororange));
        }
        img_rhymes = findViewById(R.id.nav_Rhymes);
        img_rhymes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RhymesActivity.class);
                startActivity(intent);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        recyclerView = findViewById(R.id.story_1);
        recommend = findViewById(R.id.recommend_story);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false));
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMax(100);
        progressDialog.setMessage("Its loading....");
        progressDialog.setTitle("Please wait");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        checkOrientation();
        getData();
    }
    public void getData() {
        Call<Stories> call = ApiClient.getApiService().getRhymesData();
        call.enqueue(new Callback<Stories>() {
            @Override
            public void onResponse(Call<Stories> call, Response<Stories> response) {
                progressDialog.dismiss();
                Stories rhymes = response.body();
                final List<Datum> data = rhymes.getData();
                storiesAdapter = new StoriesAdapter(MainActivity.this,data,MainActivity.this);
                storiesAdapterHorizontal = new StoriesAdapterHorizontal(MainActivity.this,data,MainActivity.this);
                recyclerView.setAdapter(storiesAdapterHorizontal);
                recommend.setAdapter(storiesAdapter);
            }
            @Override
            public void onFailure(Call<Stories> call, Throwable t) {

            }
        });
    }

    public void checkOrientation()
    {
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            recommend.setLayoutManager(new GridLayoutManager(MainActivity.this,3));
        }
        else
        {
            recommend.setLayoutManager(new GridLayoutManager(MainActivity.this,2));
        }
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
                        storiesAdapter.getFilter().filter(s);
                        storiesAdapterHorizontal.getFilter().filter(s);
                        return false;
                    }
                });
                break;
            case R.id.profile:
                Intent intent = new Intent(MainActivity.this,Profile.class);
                startActivity(intent);
                break;
            case R.id.favourite:
                Intent intent2 = new Intent(MainActivity.this,ShowFavouriteList.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRoWClick(Datum data) {
        Intent intent = new Intent(MainActivity.this,Display_Stories.class);
        intent.putExtra("Image",data.getStoryImage());
        intent.putExtra("Desc",data.getStoryDesc());
        intent.putExtra("Title",data.getStoryTitle());
        startActivity(intent);
    }
}
