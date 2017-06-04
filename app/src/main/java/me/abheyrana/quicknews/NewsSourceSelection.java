package me.abheyrana.quicknews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewsSourceSelection extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source_selection);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView news_source = (RecyclerView) findViewById(R.id.rv_source_selection);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        news_source.setLayoutManager(layoutManager);
        news_source.setHasFixedSize(true);

        final NewsSourceAdapter newsSourceAdapter = new NewsSourceAdapter(this);
        news_source.setAdapter(newsSourceAdapter);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count = newsSourceAdapter.getSelectedNewsSourceCount();
                if(count < 10){
                    String source = "Sources";
                    if(count == 9)
                        source = "Source";
                    Toast.makeText(NewsSourceSelection.this, "Please Select " +(10 - count) + " More "+source, Toast.LENGTH_SHORT).show();
                }
                else{
                    newsSourceAdapter.saveNewsSources();
                    SharedPreferences sourceSetting = getSharedPreferences("Source Setting", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sourceSetting.edit();
                    editor.putBoolean("selected",true);
                    editor.commit();
                    startActivity(new Intent(NewsSourceSelection.this,MainActivity.class));
                    finish();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }
}
