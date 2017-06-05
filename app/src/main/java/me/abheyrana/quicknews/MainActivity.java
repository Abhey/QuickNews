package me.abheyrana.quicknews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    private static URL url[] = new URL[58];
    private static int sourceCount;
    private static final String DEBUG_TAG = "QuickNewsTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        recyclerView = (RecyclerView) findViewById(R.id.rv_news_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String fileName = "NewsSource";
        try {
            FileInputStream input = openFileInput(fileName);
            if(input != null){
                BufferedReader br = new BufferedReader(new InputStreamReader(input));
                String read;
                int k = 0;
                while((read = br.readLine()) != null) {
                    url[k++] = NetworkUtils.buildURL(read);
                }
                sourceCount = k;
                //new LoadData().execute(url[0]);
                Intent intent = new Intent(this,DisplayNews.class);
                intent.putExtra("URL","http://gadgets.ndtv.com/360daily/news/oneplus-5-nokia-android-phones-india-moto-c-price-1707311");
                startActivity(intent);
                Log.d(DEBUG_TAG,url[0].toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        int id = item.getItemId();
        if(id == R.id.select_news_sources){
            Intent intent = new Intent(this,NewsSourceSelection.class);
            startActivity(intent);
        }
        if(id == R.id.read_news_later){
            // TODO(3) Add functionality to this Read Later Section
            Toast.makeText(this,"This functionality will be added soon", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.about){
            startActivity(new Intent(this,About.class));
        }
        if(id == R.id.share_app){
            Toast.makeText(this,"This functionality will be added soon", Toast.LENGTH_SHORT).show();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class LoadData extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                return NetworkUtils.getResponseFromHTTPUrlConnection(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_refresh) {
            // TODO(1) add logic for selection of refresh menu item ...
            Toast.makeText(this, "Crunching the latest data. Hang tight.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
