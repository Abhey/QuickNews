package me.abheyrana.quicknews;

import android.provider.BaseColumns;

/**
 * Created by Abhey Rana on 08-06-2017.
 */

public class NewsContract implements BaseColumns {

    private NewsContract(){}

    public static final String TABLE_NAME = "news";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_DESCRIPTION = "description";
    public static final String NEWS_URL = "news_url";
    public static final String NEWS_IMAGE_URL = "news_image_url";

    public static final String NEWS_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            NEWS_TITLE + " TEXT NOT NULL," +
            NEWS_DESCRIPTION + " TEXT NOT NULL," +
            NEWS_URL + " TEXT NOT NULL," +
            NEWS_IMAGE_URL + " TEXT NOT NULL" +
            ");";

}
