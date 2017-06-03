package me.abheyrana.quicknews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private ProgressBar progressBar;
    private static URL url[] = new URL[58];
    private static int sourceCount;
    private static final String DEBUG_TAG = "QuickNewsTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView =  (TextView) findViewById(R.id.tv_demo);
        progressBar = (ProgressBar) findViewById(R.id.pb_loader);
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
                Log.d(DEBUG_TAG,url[0].toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    public class LoadData extends AsyncTask<URL,Void,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            textView.setVisibility(View.INVISIBLE);
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
            textView.setVisibility(View.VISIBLE);
            textView.append(s);
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
