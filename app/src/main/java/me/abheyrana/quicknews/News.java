package me.abheyrana.quicknews;

/**
 * Created by Abhey Rana on 03-06-2017.
 */

//Eureka I got it A use of ArrayList will do it. I know it will do it .

public class News {

    private String title;
    private String description;
    private String url_to_news;
    private String url_to_image;

    public News(String title,String description,String url_to_news,String url_to_image){
        this.title = title;
        this.description = description;
        this.url_to_news = url_to_news;
        this.url_to_image = url_to_image;
    }

    public String getTitle(){
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public String getUrlToNews(){
        return this.url_to_news;
    }

    public String getUrlToImage(){
        return this.url_to_image;
    }

}