package me.abheyrana.quicknews;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

public class ReadLater extends AppCompatActivity {

    private RecyclerView recyclerView;
    ReadLaterAdapter readLaterAdapter;
    private SQLiteDatabase database;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.rv_read_later);
        imageView = (ImageView) findViewById(R.id.iv_read_later);

        NewsDbHelper newsHelper = new NewsDbHelper(this);
        database = newsHelper.getReadableDatabase();

        Cursor cursor = getAllNews();

        if(cursor.getCount() < 1){
            recyclerView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            /*
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            readLaterAdapter = new ReadLaterAdapter(this,cursor);
            recyclerView.setAdapter(readLaterAdapter);
            */
            // Code will be added soon....
        }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
