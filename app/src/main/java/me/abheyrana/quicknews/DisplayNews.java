package me.abheyrana.quicknews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class DisplayNews extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        webview = (WebView) findViewById(R.id.wv_diplay_news);

        Intent intent = getIntent();
        String url = intent.getStringExtra("URL");

        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setDomStorageEnabled(true);

        webview.loadUrl(url);
    }
}
