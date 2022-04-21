package com.example.naniterimorni;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends AppCompatActivity {

    SwitchCompat aSwitch;
    AudioManager aManager;
    EditText edt_name,edt_child,edt_age;
    androidx.appcompat.widget.Toolbar toolbar;
    String txt_Name,txt_CName,txt_Age;
    Boolean switchOnOff;
    CircleImageView img_profile;
    LinearLayout linearLayout;
    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        linearLayout = findViewById(R.id.gotoFavourite);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,ShowFavouriteList.class);
                startActivity(intent);
            }
        });
        if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(this.getResources().getColor(R.color.colororange));
        }
        toolbar =findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        img_profile = findViewById(R.id.img_profile);
        //img_profile.setImageURI(Uri.parse(Image));
        img_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // check runtime permission
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==
                            PackageManager.PERMISSION_DENIED){
                        //permission not granted
                        String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permission,PERMISSION_CODE);
                    }
                    else
                    {
                        pickImageFromGallery();
                    }
                }else
                {
                    //system os is less than marshmallow
                    pickImageFromGallery();
                }
            }
        });
        edt_name = findViewById(R.id.edt_name);
        edt_child = findViewById(R.id.edt_childname);
        edt_age = findViewById(R.id.edt_age);
        aSwitch = findViewById(R.id.switchScound);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b == true)
                {
                    Toast.makeText(Profile.this, "On", Toast.LENGTH_SHORT).show();
                    aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
                    aManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                }
                else
                {
                    aManager=(AudioManager)getSystemService(AUDIO_SERVICE);
                    aManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    Toast.makeText(Profile.this, "Off", Toast.LENGTH_SHORT).show();
                }
            }
        });
        loadData();
        updateData();
        edt_name.setEnabled(false);
        edt_name.setFocusable(false);
        edt_name.setFocusableInTouchMode(false);
        edt_child.setEnabled(false);
        edt_child.setFocusable(false);
        edt_child.setFocusableInTouchMode(false);
        edt_age.setEnabled(false);
        edt_age.setFocusable(false);
        edt_age.setFocusableInTouchMode(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.edit:
                Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
                edt_name.setEnabled(true);
                edt_name.setFocusable(true);
                edt_name.setFocusableInTouchMode(true);
                edt_child.setEnabled(true);
                edt_child.setFocusable(true);
                edt_child.setFocusableInTouchMode(true);
                edt_age.setEnabled(true);
                edt_age.setFocusable(true);
                edt_age.setFocusableInTouchMode(true);
                break;
            case R.id.save:
                Toast.makeText(this, "Save", Toast.LENGTH_SHORT).show();
                edt_name.setEnabled(false);
                edt_name.setFocusable(false);
                edt_name.setFocusableInTouchMode(false);
                edt_child.setEnabled(false);
                edt_child.setFocusable(false);
                edt_child.setFocusableInTouchMode(false);
                edt_age.setEnabled(false);
                edt_age.setFocusable(false);
                edt_age.setFocusableInTouchMode(false);
                saveData();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,IMAGE_PICK_CODE);
    }

    //handle runtime permission


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case PERMISSION_CODE:
            {
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    //permission was granted
                    pickImageFromGallery();
                }else{
                    //permission was Denied
                    Toast.makeText(this, "Permission Denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //handle result of picked image

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK && requestCode==IMAGE_PICK_CODE)
        {
            //set image to imageView
            mImageUri = data.getData();
            img_profile.setImageURI(mImageUri);
            img_profile.invalidate();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Name",edt_name.getText().toString());
        editor.putString("ChildName",edt_child.getText().toString());
        editor.putString("Age",edt_age.getText().toString());
        editor.putBoolean("switch",aSwitch.isChecked());
        editor.putString("Image",String.valueOf(mImageUri));
        editor.apply();
    }
    private void loadData()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("shared",MODE_PRIVATE);
        txt_Name = sharedPreferences.getString("Name","");
        txt_CName = sharedPreferences.getString("ChildName","");
        txt_Age = sharedPreferences.getString("Age","");
        switchOnOff = sharedPreferences.getBoolean("switch",false);
        mImageUri = Uri.parse(sharedPreferences.getString("Image",null));
    }
    private void updateData()
    {
        edt_name.setText(txt_Name);
        edt_child.setText(txt_CName);
        edt_age.setText(txt_Age);
        aSwitch.setChecked(switchOnOff);
        img_profile.setImageURI(mImageUri);
    }
}
