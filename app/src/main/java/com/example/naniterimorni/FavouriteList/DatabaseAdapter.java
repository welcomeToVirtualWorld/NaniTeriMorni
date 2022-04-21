package com.example.naniterimorni.FavouriteList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseAdapter extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "NaniTeriMorni";
    private static final int DATABASE_VERSION = 1;
    public DatabaseAdapter(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(Favourite.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public long insertFavourite(Favourite favourite)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Favourite.TITLE,favourite.getTitle());
        contentValues.put(Favourite.IMAGE,favourite.getImage());
        contentValues.put(Favourite.Description,favourite.getDescription());
        long last_id = db.insert(Favourite.TABLE_NAME,null,contentValues);
        db.close();
        return last_id;
    }
    public List<Favourite> getAllList()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        List<Favourite> favouriteList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + Favourite.TABLE_NAME , null);
        if(cursor.moveToFirst())
        {
            do {
                Favourite favourite = new Favourite();
                favourite.setId(cursor.getInt(cursor.getColumnIndex(Favourite.COLUMN_ID)));
                favourite.setTitle(cursor.getString(cursor.getColumnIndex(Favourite.TITLE)));
                favourite.setImage(cursor.getString(cursor.getColumnIndex(Favourite.IMAGE)));
                favourite.setDescription(cursor.getString(cursor.getColumnIndex(Favourite.Description)));
                favouriteList.add(favourite);
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return favouriteList;
    }
    public int getColumnValue(String title)
    {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            String[] columns = {Favourite.COLUMN_ID,Favourite.TITLE,Favourite.IMAGE,Favourite.Description};
            Cursor cursor = db.query(Favourite.TABLE_NAME,columns,Favourite.TITLE + " = " + '\'' + title +'\'' ,null,null,null,null);
            if(cursor.moveToFirst()) {
                cursor.close();
                return 1;
            }
        }
        catch (Exception e)
        {
            Log.e("dberror",e.getLocalizedMessage());
        }
        //cursor.close();
        return 0;
    }
    public int deleteData(String id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String[] whereargs = {id};
        int count_delete = db.delete(Favourite.TABLE_NAME,Favourite.COLUMN_ID + " = ?",whereargs);
        db.close();
        return count_delete;
    }
}
