package me.abheyrana.quicknews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Abhey Rana on 08-06-2017.
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "QuickNews.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(NewsContract.NEWS_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
