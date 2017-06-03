package me.abheyrana.quicknews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewsSourceSelection extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source_selection);

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
                if(newsSourceAdapter.getSelectedNewsSourceCount() < 10){
                    Toast.makeText(NewsSourceSelection.this, "Please Select " +(10 - newsSourceAdapter.getSelectedNewsSourceCount()) + " More Sources", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NewsSourceSelection.this, "Ready to roll", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
