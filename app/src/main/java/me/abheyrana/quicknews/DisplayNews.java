package me.abheyrana.quicknews;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class DisplayNews extends AppCompatActivity {

    private WebView webview;
    private ShareActionProvider actionProvider;
    private static String url;
    private static int readLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        readLater = 0;

        webview = (WebView) findViewById(R.id.wv_diplay_news);

        Intent intent = getIntent();
        url = intent.getStringExtra("URL");

        webview.setWebViewClient(new WebViewClient());

        WebSettings webSettings = webview.getSettings();


        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);

        webview.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_news_menu,menu);
        MenuItem shareItem = menu.findItem(R.id.share_item);
        actionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,url);
        intent.setType("text/plain");
        setSharedIntent(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.read_later){
            // TODO(2) Add functionality to this menu item.
            if(readLater == 0) {
                // Here the code for adding article to read list will be presenet
                item.setIcon(R.drawable.read_later_saved);
                readLater = 1;
            }
            else{
                // Here the code for removing article from read list will be present
                item.setIcon(R.drawable.read_later);
                readLater = 0;
            }
        }
        if(id == R.id.show_original){
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        }
        return true;
    }

    public void setSharedIntent(Intent intent){
        if(actionProvider != null){
            actionProvider.setShareIntent(intent);
        }
    }

}
