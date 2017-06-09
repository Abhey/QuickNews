package me.abheyrana.quicknews;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Abhey Rana on 08-06-2017.
 */

public class ReadLaterAdapter extends RecyclerView.Adapter<ReadLaterAdapter.ReadLaterHolder> {

    private Cursor cursor;
    private Context context;

    public ReadLaterAdapter(Context context,Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ReadLaterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ReadLaterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class ReadLaterHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView imageView;
        private TextView textView;

        public ReadLaterHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_news_icon);
            textView = (TextView) itemView.findViewById(R.id.tv_headline_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            // Add functionality of Clicking here ......
        }

    }

}
