package me.abheyrana.quicknews;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Abhey Rana on 03-06-2017.
 */

public class NetworkUtils {

    private static final String ARTICLE_SOURCE_URL = "https://newsapi.org/v1/articles";
    private static final String NEWS_SOURCE_URL = " https://newsapi.org/v1/sources";

    private static final String SOURCE_QUERY = "source";
    private static final String API_QUERY = "apiKey";
    private static final String SORTBY_QUERY = "sortBy";

    private static final String API_KEY = "b0080dfc22874f0fb67caa775ce07708";
    private static String SORT_BY = "latest";


    /**
     * This method takes advantage of Uri class of android and returns URL
     * @param source  The source of the news
     * @return URL for fetching news from that source
     */
    public static URL buildURL(String source){
        Uri buildUri = Uri.parse(ARTICLE_SOURCE_URL).buildUpon()
                .appendQueryParameter(SOURCE_QUERY,source)
                .appendQueryParameter(SORTBY_QUERY,SORT_BY)
                .appendQueryParameter(API_QUERY,API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(buildUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * This method returns response from an URL in String format
     * @param url The URL from which we need to get response
     * @return The response in String format
     * @throws IOException
     */
    public static String getResponseFromHTTPUrlConnection(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            InputStream input = connection.getInputStream();
            Scanner scan = new Scanner(input);
            scan.useDelimiter("\\A");
            if(scan.hasNext())
                return scan.next();
            else
                return null;
        }finally {
            connection.disconnect();
        }
    }



}
