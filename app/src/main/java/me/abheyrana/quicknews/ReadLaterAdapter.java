package me.abheyrana.quicknews;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Abhey Rana on 08-06-2017.
 */

public class ReadLaterAdapter extends RecyclerView.Adapter<ReadLaterAdapter.ReadLaterHolder> {

    private Cursor cursor;
    private Context context;
    public static ReadLater readLater;

    public ReadLaterAdapter(Context context,Cursor cursor){
        this.context = context;
        this.cursor = cursor;
    }

    @Override
    public ReadLaterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.news_card,parent,false);
        return new ReadLaterHolder(view);
    }

    @Override
    public void onBindViewHolder(ReadLaterHolder holder, int position) {
        if(!cursor.moveToPosition(position))
            return;
        holder.textView.setText(cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_TITLE)));
        Picasso.with(context).load(cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_IMAGE_URL))).into(holder.imageView);
        holder.itemView.setTag(cursor.getLong(cursor.getColumnIndex(NewsContract._ID)));
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
            int index = getAdapterPosition();
            cursor.moveToPosition(index);
            String title = cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_DESCRIPTION));
            String url_to_news = cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_URL));
            String url_to_image = cursor.getString(cursor.getColumnIndex(NewsContract.NEWS_IMAGE_URL));
            Intent intent = new Intent(context,DisplayNews.class);
            intent.putExtra("TITLE",title);
            intent.putExtra("DESCRIPTION",description);
            intent.putExtra("URL",url_to_news);
            intent.putExtra("URL_IMAGE",url_to_image);
            intent.putExtra("STARTER","ReadLaterActivity");
            context.startActivity(intent);
            readLater.finish();
        }

    }

    public void swapCursor(Cursor newCursor){
        if(cursor != null)
            cursor.close();
        cursor = newCursor;
        if(cursor != null)
            notifyDataSetChanged();
    }

}
