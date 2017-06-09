package me.abheyrana.quicknews;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.RequiresPermission;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class ReadLater extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ReadLaterAdapter readLaterAdapter;
    private NewsDbHelper newsHelper;
    private SQLiteDatabase database;
    private ImageView imageView;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_later);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        ReadLaterAdapter.readLater = this;

        recyclerView = (RecyclerView) findViewById(R.id.rv_read_later);
        imageView = (ImageView) findViewById(R.id.iv_read_later);

        newsHelper = new NewsDbHelper(this);
        database = newsHelper.getWritableDatabase();

        cursor = getAllNews();

        if(cursor.getCount() < 1){
            recyclerView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            readLaterAdapter = new ReadLaterAdapter(this,cursor);
            recyclerView.setAdapter(readLaterAdapter);

            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                    0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
            ) {
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    return false;
                }

                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    long id = (long) viewHolder.itemView.getTag();
                    deleteNews(id);
                    readLaterAdapter.swapCursor(getAllNews());
                }
            }).attachToRecyclerView(recyclerView);
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

    @Override
    protected void onPause() {
        super.onPause();
        database.close();
    }

}
