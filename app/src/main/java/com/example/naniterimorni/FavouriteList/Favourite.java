package com.example.naniterimorni.FavouriteList;

public class Favourite {
    public static final String TABLE_NAME = "FavouriteList";
    public static final String COLUMN_ID = "c_id";
    public static final String TITLE = "t_name";
    public static final String IMAGE = "image";
    public static final String Description = "Desc";

    private int id;
    private String title;
    private String image;
    private String description;

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            TITLE + " TEXT , " +
            IMAGE + " TEXT , " +
            Description + " TEXT )";

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
