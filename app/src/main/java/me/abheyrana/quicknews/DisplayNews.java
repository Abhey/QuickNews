
//All the work that need to be done in this class file is over . So Closing this file
//Wait what you still need to implement chaching of webpage for screen rotation.
//If i will rotate on destroy will be called

package me.abheyrana.quicknews;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

public class DisplayNews extends AppCompatActivity {

    private WebView webview;
    private ShareActionProvider actionProvider;
    private ProgressBar progressBar;
    private SQLiteDatabase database;

    private static String title;
    private static String description;
    private static String url;
    private static String url_image;

    private static int readLater;
    private static long id = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.pb_display_news);

        webview = (WebView) findViewById(R.id.wv_diplay_news);

        NewsDbHelper newsHelper = new NewsDbHelper(this);
        database = newsHelper.getWritableDatabase();

        Intent intent = getIntent();
        title = intent.getStringExtra("TITLE");
        description = intent.getStringExtra("DESCRIPTION");
        url = intent.getStringExtra("URL");
        url_image = intent.getStringExtra("URL_IMAGE");

        String selectionClause = NewsContract.NEWS_URL + " = ?" ;
        String condition[] = {url};

        Cursor cursor = database.query(
                NewsContract.TABLE_NAME,
                null,
                selectionClause,
                condition,
                null,
                null,
                NewsContract.NEWS_TIMESTAMP
        );

        if(cursor.getCount() < 1) {
            readLater = 0;
            id = -1;
        }
        else {
            readLater = 1;
            cursor.moveToPosition(0);
            id = cursor.getInt(cursor.getColumnIndex(NewsContract._ID));
        }

        webview.setWebViewClient(new NewsWebViewClient());
        webview.setWebChromeClient(new NewsWebChromeClient());

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

        if(savedInstanceState == null)
            webview.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.display_news_menu,menu);

        // Code for Read Later menu item

        MenuItem readLaterMenu = (MenuItem) menu.findItem(R.id.read_later);

        if(readLater == 0)
            readLaterMenu.setIcon(R.drawable.read_later);
        else
            readLaterMenu.setIcon(R.drawable.read_later_saved);

        // Code for Share menu item.

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
        if(id == android.R.id.home){
            finish();
        }
        return true;
    }

    public void setSharedIntent(Intent intent){
        if(actionProvider != null){
            actionProvider.setShareIntent(intent);
        }
    }

    private class NewsWebViewClient extends WebViewClient{

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(0);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view,url);
            progressBar.setVisibility(View.GONE);
            progressBar.setProgress(100);
        }

    }

    private class NewsWebChromeClient extends WebChromeClient{

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            progressBar.setProgress(newProgress);
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webview.saveState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        webview.restoreState(savedInstanceState);
    }

    @Override
    protected void onDestroy() {

        if(readLater == 0 && id != -1)
            deleteNews(id);
        else if(readLater == 1 && id == -1)
            insertNews();

        super.onDestroy();
    }

    private long insertNews(){

        ContentValues contentValue = new ContentValues();
        contentValue.put(NewsContract.NEWS_TITLE,title);
        contentValue.put(NewsContract.NEWS_DESCRIPTION,description);
        contentValue.put(NewsContract.NEWS_URL,url);
        contentValue.put(NewsContract.NEWS_IMAGE_URL,url_image);

        return database.insert(NewsContract.TABLE_NAME,null,contentValue);

    }

    private boolean deleteNews(long id){
        return database.delete(
                NewsContract.TABLE_NAME,
                NewsContract._ID + "=" +id,
                null
        ) > 0;
    }

}

