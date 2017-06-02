package me.abheyrana.quicknews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class NewsSourceSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source_selection);

        RecyclerView news_source = (RecyclerView) findViewById(R.id.rv_source_selection);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        news_source.setLayoutManager(layoutManager);
        news_source.setHasFixedSize(true);

        NewsSourceAdapter newsSourceAdapter = new NewsSourceAdapter(this);
        news_source.setAdapter(newsSourceAdapter);

    }
}
