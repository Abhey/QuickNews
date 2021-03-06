package me.abheyrana.quicknews;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by Abhey Rana on 02-06-2017.
 */

public class NewsSourceAdapter extends RecyclerView.Adapter<NewsSourceAdapter.NewsSourceViewHolder> {

    private static String newsSourceName[];
    private static Bitmap newsSourceIcon[];
    private static Context context;
    private int  counter [];
    private int newsSourceCount;

    private static final int MAX_NEWS_SOURCE = 58;

    public NewsSourceAdapter(Context context,int counter[]){
        NewsSourceAdapter.context = context;
        newsSourceName = getNewsSourceName();
        newsSourceIcon = getNewsSourceIcon();
        this.counter = new int[58];
        newsSourceCount = 0;
        for(int i = 0; i < 58 ; i++) {
            this.counter[i] = counter[i];
            if(counter[i] == 1)
                newsSourceCount ++;
        }
    }

    @Override
    public NewsSourceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_source_item,parent,false);
        NewsSourceViewHolder holder = new NewsSourceViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(NewsSourceViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return MAX_NEWS_SOURCE;
    }

    class NewsSourceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;

        public NewsSourceViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_source_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_source_text);
            itemView.setOnClickListener(this);
        }

        public void bind(int Index){
            imageView.setImageBitmap(newsSourceIcon[Index]);
            textView.setText(newsSourceName[Index]);
            if(counter[Index] == 0)
                textView.setBackgroundResource(R.color.cardview_light_background);
            else
                textView.setBackgroundResource(R.color.black_overlay);
        }

        @Override
        public void onClick(View view) {
            // Write code for checking if a view is selected or not ........
            int index = getAdapterPosition();
            if(counter[index] == 0){
                textView.setBackgroundResource(R.color.black_overlay);
                counter[index] = 1;
                newsSourceCount ++;
            }
            else{
                textView.setBackgroundResource(R.color.cardview_light_background);
                counter[index] = 0;
                newsSourceCount --;
            }
        }

    }

    public static String[] getNewsSourceName(){
        
        String source[] = new String[MAX_NEWS_SOURCE];
        
        source[0]  = "ABC News";
        source[1]  = "Al Jazeera English";
        source[2]  = "Ars Technica";
        source[3]  = "Associated Press";
        source[4]  = "BBC News";
        source[5]  = "BBC Sports";
        source[6]  = "Boolmberg";
        source[7]  = "Breitbart";
        source[8]  = "Business Insider";
        source[9]  = "Business Insider UK";
        source[10] = "Buzzfeed";
        source[11] = "CNBC";
        source[12] = "CNN";
        source[13] = "Daily Mail";
        source[14] = "Engadget";
        source[15] = "Entertainment Weekly";
        source[16] = "ESPN";
        source[17] = "ESPN Cricket";
        source[18] = "Financial Times";
        source[19] = "Fortune";
        source[20] = "Four Four Two";
        source[21] = "Fox Sports";
        source[22] = "Google News";
        source[23] = "IGN";
        source[24] = "Independent";
        source[25] = "Mashable";
        source[26] = "Metro";
        source[27] = "Mirror";
        source[28] = "MTV News";
        source[29] = "MTV News UK";
        source[30] = "National Geographic";
        source[31] = "New Scientist";
        source[32] = "New York Magazine";
        source[33] = "Newsweek";
        source[34] = "NFL News";
        source[35] = "Polygon";
        source[36] = "Redcode";
        source[37] = "Reddit";
        source[38] = "Reuters";
        source[39] = "TalkSport";
        source[40] = "TechCrunch";
        source[41] = "TechRadar";
        source[42] = "The Economist";
        source[43] = "The Gaurdian";
        source[44] = "The Gaurdian UK";
        source[45] = "The Hindu";
        source[46] = "The Huffington Post";
        source[47] = "The Lad Bible";
        source[48] = "The New York Times";
        source[49] = "The Next Web";
        source[50] = "The Sports Bible";
        source[51] = "The Telegraph";
        source[52] = "The Times Of India";
        source[53] = "The Verge";
        source[54] = "The Wall Street Journey";
        source[55] = "The Washington Post";
        source[56] = "Time";
        source[57] = "USA Today";
        
        return source;
    }

    public static Bitmap[] getNewsSourceIcon(){
        
        Bitmap bitmap[] = new Bitmap[MAX_NEWS_SOURCE];
        Resources resource = context.getResources();
        
        bitmap[0]  = BitmapFactory.decodeResource(resource,R.drawable.abc_news_au);
        bitmap[1]  = BitmapFactory.decodeResource(resource,R.drawable.al_jazeera_english);
        bitmap[2]  = BitmapFactory.decodeResource(resource,R.drawable.ars_technica);
        bitmap[3]  = BitmapFactory.decodeResource(resource,R.drawable.associated_press);
        bitmap[4]  = BitmapFactory.decodeResource(resource,R.drawable.bbc_news);
        bitmap[5]  = BitmapFactory.decodeResource(resource,R.drawable.bbc_sport);
        bitmap[6]  = BitmapFactory.decodeResource(resource,R.drawable.bloomberg);
        bitmap[7]  = BitmapFactory.decodeResource(resource,R.drawable.breitbart_news);
        bitmap[8]  = BitmapFactory.decodeResource(resource,R.drawable.business_insider);
        bitmap[9]  = BitmapFactory.decodeResource(resource,R.drawable.business_insider_uk);
        bitmap[10] = BitmapFactory.decodeResource(resource,R.drawable.buzzfeed);
        bitmap[11] = BitmapFactory.decodeResource(resource,R.drawable.cnbc);
        bitmap[12] = BitmapFactory.decodeResource(resource,R.drawable.cnn);
        bitmap[13] = BitmapFactory.decodeResource(resource,R.drawable.daily_mail);
        bitmap[14] = BitmapFactory.decodeResource(resource,R.drawable.engadget);
        bitmap[15] = BitmapFactory.decodeResource(resource,R.drawable.entertainment_weekly);
        bitmap[16] = BitmapFactory.decodeResource(resource,R.drawable.espn);
        bitmap[17] = BitmapFactory.decodeResource(resource,R.drawable.espn_cric_info);
        bitmap[18] = BitmapFactory.decodeResource(resource,R.drawable.financial_times);
        bitmap[19] = BitmapFactory.decodeResource(resource,R.drawable.fortune);
        bitmap[20] = BitmapFactory.decodeResource(resource,R.drawable.four_four_two);
        bitmap[21] = BitmapFactory.decodeResource(resource,R.drawable.fox_sports);
        bitmap[22] = BitmapFactory.decodeResource(resource,R.drawable.google_news);
        bitmap[23] = BitmapFactory.decodeResource(resource,R.drawable.ign);
        bitmap[24] = BitmapFactory.decodeResource(resource,R.drawable.independent);
        bitmap[25] = BitmapFactory.decodeResource(resource,R.drawable.mashable);
        bitmap[26] = BitmapFactory.decodeResource(resource,R.drawable.metro);
        bitmap[27] = BitmapFactory.decodeResource(resource,R.drawable.mirror);
        bitmap[28] = BitmapFactory.decodeResource(resource,R.drawable.mtv_news);
        bitmap[29] = BitmapFactory.decodeResource(resource,R.drawable.mtv_news_uk);
        bitmap[30] = BitmapFactory.decodeResource(resource,R.drawable.national_geographic);
        bitmap[31] = BitmapFactory.decodeResource(resource,R.drawable.new_scientist);
        bitmap[32] = BitmapFactory.decodeResource(resource,R.drawable.new_york_magazine);
        bitmap[33] = BitmapFactory.decodeResource(resource,R.drawable.newsweek);
        bitmap[34] = BitmapFactory.decodeResource(resource,R.drawable.nfl_news);
        bitmap[35] = BitmapFactory.decodeResource(resource,R.drawable.polygon);
        bitmap[36] = BitmapFactory.decodeResource(resource,R.drawable.recode);
        bitmap[37] = BitmapFactory.decodeResource(resource,R.drawable.reddit_r_all);
        bitmap[38] = BitmapFactory.decodeResource(resource,R.drawable.reuters);
        bitmap[39] = BitmapFactory.decodeResource(resource,R.drawable.talksport);
        bitmap[40] = BitmapFactory.decodeResource(resource,R.drawable.techcrunch);
        bitmap[41] = BitmapFactory.decodeResource(resource,R.drawable.techradar);
        bitmap[42] = BitmapFactory.decodeResource(resource,R.drawable.the_economist);
        bitmap[43] = BitmapFactory.decodeResource(resource,R.drawable.the_gaurdian_au);
        bitmap[44] = BitmapFactory.decodeResource(resource,R.drawable.the_gaurdian_uk);
        bitmap[45] = BitmapFactory.decodeResource(resource,R.drawable.the_hindu);
        bitmap[46] = BitmapFactory.decodeResource(resource,R.drawable.the_huffington_post);
        bitmap[47] = BitmapFactory.decodeResource(resource,R.drawable.the_lad_bible);
        bitmap[48] = BitmapFactory.decodeResource(resource,R.drawable.the_new_york_times);
        bitmap[49] = BitmapFactory.decodeResource(resource,R.drawable.the_next_web);
        bitmap[50] = BitmapFactory.decodeResource(resource,R.drawable.the_sports_bible);
        bitmap[51] = BitmapFactory.decodeResource(resource,R.drawable.the_telegraph);
        bitmap[52] = BitmapFactory.decodeResource(resource,R.drawable.the_times_of_india);
        bitmap[53] = BitmapFactory.decodeResource(resource,R.drawable.the_verge);
        bitmap[54] = BitmapFactory.decodeResource(resource,R.drawable.the_wall_street_journal);
        bitmap[55] = BitmapFactory.decodeResource(resource,R.drawable.the_washington_post);
        bitmap[56] = BitmapFactory.decodeResource(resource,R.drawable.time);
        bitmap[57] = BitmapFactory.decodeResource(resource,R.drawable.usa_today);
        
        return bitmap;
    }

    public static String getSelectedNewsSourceParam(int index){

        index ++;

        switch(index){
            case  1: return "abc-news-au";
            case  2: return "al-jazeera-english";
            case  3: return "ars-technica";
            case  4: return "associated-press";
            case  5: return "bbc-news";
            case  6: return "bbc-sports";
            case  7: return "bloomberg";
            case  8: return "breitbart";
            case  9: return "business-insider";
            case 10: return "business-insider-uk";
            case 11: return "buzzfeed";
            case 12: return "cnbc";
            case 13: return "cnn";
            case 14: return "daily-mail";
            case 15: return "engadget";
            case 16: return "entertainment-weekly";
            case 17: return "espn";
            case 18: return "espn-cric-info";
            case 19: return "financial-times";
            case 20: return "fortune";
            case 21: return "four-four-two";
            case 22: return "fox-sports";
            case 23: return "google-news";
            case 24: return "ign";
            case 25: return "independent";
            case 26: return "mashable";
            case 27: return "metro";
            case 28: return "mirror";
            case 29: return "mtv-news";
            case 30: return "mtv-news-uk";
            case 31: return "national-geographic";
            case 32: return "new-scientist";
            case 33: return "new-york-magazine";
            case 34: return "newsweek";
            case 35: return "nfl-news";
            case 36: return "polygon";
            case 37: return "recode";
            case 38: return "reddit-r-all";
            case 39: return "reuters";
            case 40: return "talksport";
            case 41: return "techcrunch";
            case 42: return "techradar";
            case 43: return "the-economist";
            case 44: return "the-gaurdian-au";
            case 45: return "the-gaurdian-uk";
            case 46: return "the-hindu";
            case 47: return "the-huffington-post";
            case 48: return "the-lad-bible";
            case 49: return "the-new-york-times";
            case 50: return "the-next-web";
            case 51: return "the-sports-bible";
            case 52: return "the-telegraph";
            case 53: return "the-times-of-india";
            case 54: return "the-verge";
            case 55: return "the-wall-street-journal";
            case 56: return "the-washington-post";
            case 57: return "time";
            case 58: return "usa-today";
            default : return "";
        }

    }

    public int getSelectedNewsSourceCount(){
        return newsSourceCount;
    }

    public void saveNewsSources(){
        String fileName = "NewsSource";
        try {
            FileOutputStream out = context.openFileOutput(fileName,Context.MODE_PRIVATE);
            for(int i=0;i<58;i++){
                if(counter[i] == 1){
                    String str = getSelectedNewsSourceParam(i) + "\n";
                    out.write(str.getBytes());
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }

    public int[] getCounterData(){
        return this.counter;
    }

}