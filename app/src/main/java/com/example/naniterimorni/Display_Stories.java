package com.example.naniterimorni;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.ShareActionProvider;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.naniterimorni.FavouriteList.DatabaseAdapter;
import com.example.naniterimorni.FavouriteList.Favourite;

public class Display_Stories extends AppCompatActivity {
    androidx.appcompat.widget.Toolbar toolbar;
    ImageView img_story;
    TextView txt_Desc;
    String image;
    String Description;
    String Title;
    DatabaseAdapter databaseAdapter;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__stories);
        databaseAdapter = new DatabaseAdapter(Display_Stories.this);
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colororange));
        }

        toolbar = findViewById(R.id.toolbar);
        TextView mtitle = toolbar.findViewById(R.id.story_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        image = intent.getStringExtra("Image");
        Description = intent.getStringExtra("Desc");
        Title = intent.getStringExtra("Title");
        mtitle.setText(Title);
        img_story = findViewById(R.id.img_Story);
        txt_Desc = findViewById(R.id.story_Description);
        txt_Desc.setText(Description);
        Glide.with(Display_Stories.this)
                .load(image)
                .into(img_story);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        switch (item.getItemId()) {
            case R.id.share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Read all amazing stories here.For Download Click here...";
                String shareSub = "Download the app now";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
                break;
            case R.id.like:
                int value = databaseAdapter.getColumnValue(Title);
                if(value == 1)
                {
                    Toast.makeText(this, "Already added", Toast.LENGTH_SHORT).show();
                }
                else {
                    Favourite favourite = new Favourite();
                    favourite.setTitle(Title);
                    favourite.setImage(image);
                    favourite.setDescription(Description);
                    long id = databaseAdapter.insertFavourite(favourite);
                    if (id > 0) {
                        Toast.makeText(this, "Added to favourite list", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Not Added", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }
}
