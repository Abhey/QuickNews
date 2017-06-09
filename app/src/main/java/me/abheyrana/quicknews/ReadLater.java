package me.abheyrana.quicknews;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class ReadLater extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);

        recyclerView = (RecyclerView) findViewById(R.id.rv_read_later);

    }

    private Cursor getAllNews(){
        return database.query(
                NewsContract.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                NewsContract.NEWS_TIMESTAMP
        );
    }

    private boolean deleteNews(long id){
        return database.delete(
                NewsContract.TABLE_NAME,
                NewsContract._ID + "=" +id,
                null
        ) > 0;
    }

}
