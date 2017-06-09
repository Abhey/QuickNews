
//Dude you just need to wait if you want to play with screen rotation.
//I have implemented most the logic from my side let' see how it works.

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private NewsCardAdapter newsCardAdapter;
    private ImageView imageView;

    public static ArrayList<News> news_card = new ArrayList<>();
    private static URL url[] = new URL[58];
    private static AsyncTask<URL,Void,String> threadPool[] = new AsyncTask[58];
    private static int sourceCount;
    private static final String DEBUG_TAG = "QuickNewsTag";
    private static String fileName = "NewsSource";
    private static int tempVariable ;
    private static int nullCount ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialising temporary variables
        tempVariable = 1;
        nullCount = 0;

        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
        recyclerView = (RecyclerView) findViewById(R.id.rv_news_view);
        imageView = (ImageView) findViewById(R.id.iv_no_internet);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        newsCardAdapter = new NewsCardAdapter(this);
        recyclerView.setAdapter(newsCardAdapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
                for(int i = 0; i < sourceCount ; i++ ){
                    threadPool[i] =  new LoadData().execute(url[i]);
                }
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
            String str = "";
            try {
                InputStream inputStream = openFileInput(fileName);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String read = br.readLine();
                int k = 0;
                while(k < 58){
                    if(read != null && NewsSourceAdapter.getSelectedNewsSourceParam(k).compareTo(read) == 0){
                        read = br.readLine();
                        str = str + "1";
                    }
                    else
                        str = str + "0";
                    k++;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this,NewsSourceSelection.class);
            intent.putExtra("CounterData",str);
            startActivity(intent);
        }
        if(id == R.id.read_news_later){
            startActivity(new Intent(this,ReadLater.class));
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
            if(tempVariable == 1) {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        protected String doInBackground(URL... urls) {
            try {
                String result =  NetworkUtils.getResponseFromHTTPUrlConnection(urls[0]);
                if(isCancelled())
                    return null;
                else
                    return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(tempVariable == 1) {
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                tempVariable = 0;
            }
            JSONObject jsonResponse;
            try {
                if(s != null ) {
                    jsonResponse = new JSONObject(s);
                    JSONArray jsonArticle = jsonResponse.getJSONArray("articles");
                    int itemCount = jsonArticle.length();
                    String title, description, url_to_news, url_to_image;
                    for (int i = 0; i < itemCount; i++) {
                        JSONObject article = jsonArticle.getJSONObject(i);
                        title = article.getString("title");
                        description = article.getString("description");
                        url_to_news = article.getString("url");
                        url_to_image = article.getString("urlToImage");
                        news_card.add(new News(title, description, url_to_news, url_to_image));
                        newsCardAdapter.setDataSource(news_card);
                    }
                }
                else{
                    nullCount ++;
                    if(nullCount == sourceCount){
                        recyclerView.setVisibility(View.INVISIBLE);
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            if(tempVariable == 1){
                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);
                tempVariable = 0;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i = 0; i < sourceCount ; i++)
            threadPool[i].cancel(true);
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
            Toast.makeText(this, "Crunching the latest data. Hang tight.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
