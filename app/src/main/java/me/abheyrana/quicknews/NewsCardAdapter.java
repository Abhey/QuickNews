package me.abheyrana.quicknews;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Abhey Rana on 05-06-2017.
 */

public class NewsCardAdapter extends RecyclerView.Adapter<NewsCardAdapter.NewsCardViewHolder> {

    private ArrayList<News> news;
    private Context context;
    private int newsCardCount;

    public NewsCardAdapter(Context context){
        this.context = context;
        news = new ArrayList<>();
        newsCardCount = 0;
    }

    @Override
    public NewsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card,parent,false);
        return new NewsCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsCardViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return newsCardCount;
    }

    class NewsCardViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;

        public NewsCardViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_news_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_headline_text);
            itemView.setOnClickListener(this);
        }

        public void bind(int index){
            textView.setText(news.get(index).getTitle());
            Picasso.with(context).load(news.get(index).getUrlToImage()).into(imageView);
        }

        @Override
        public void onClick(View view) {
            int index = getAdapterPosition();
            Intent intent = new Intent(context,DisplayNews.class);
            News localNews = news.get(index);
            intent.putExtra("TITLE",localNews.getTitle());
            intent.putExtra("DESCRIPTION",localNews.getDescription());
            intent.putExtra("URL",localNews.getUrlToNews());
            intent.putExtra("URL_IMAGE",localNews.getUrlToImage());
            context.startActivity(intent);
        }
    }

    public void setDataSource(ArrayList<News> news){
        this.news = news;
        this.newsCardCount = news.size();

        //This line of code is the source of error ........
        notifyDataSetChanged();
    }

}
