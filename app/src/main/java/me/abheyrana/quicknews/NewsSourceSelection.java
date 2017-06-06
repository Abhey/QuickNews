package me.abheyrana.quicknews;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class NewsSourceSelection extends AppCompatActivity {

    private Button button;
    private NewsSourceAdapter newsSourceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_source_selection);

        int counter[] = new int[58];

        if(savedInstanceState != null){
            String str = savedInstanceState.getString("CounterData");
            for(int i = 0; i < 58 ; i++)
                counter[i] = Integer.parseInt(""+str.charAt(i));
        }
        else{
            Intent intent = getIntent();
            String str = intent.getStringExtra("CounterData");
            if(str != null){
                for(int i = 0; i < 58 ; i++)
                    counter[i] = Integer.parseInt(""+str.charAt(i));
            }
            else {
                for (int i = 0; i < 58; i++)
                    counter[i] = 0;
            }
        }

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        RecyclerView news_source = (RecyclerView) findViewById(R.id.rv_source_selection);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        news_source.setLayoutManager(layoutManager);
        news_source.setHasFixedSize(true);

        newsSourceAdapter = new NewsSourceAdapter(this,counter);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int counter[] = newsSourceAdapter.getCounterData();
        String str = "";
        for(int i = 0 ; i < 58 ; i++){
            str = str + counter[i];
        }
        outState.putString("CounterData",str);
    }
}
