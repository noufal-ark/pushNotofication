package com.ark.pushnotificationnewsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ark.pushnotificationnewsapp.R;
import com.ark.pushnotificationnewsapp.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by noufal on 28/2/18.
 */


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<News> newsList;
    private Context context;

    public NewsAdapter(Context context, List<News> newsList) {
        this.newsList = newsList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_news_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        News news = newsList.get(position);
        holder.title.setText(news.getTitle());
        holder.body.setText(news.getBody());
        holder.date.setText(news.getDate());
        holder.source.setText(news.getSorce());
        Picasso.with(context)
                .load(news.getImage())
//                .placeholder(R.drawable.user_placeholder)
//                .error(R.drawable.user_placeholder_error)
                .into(holder.thumb);
        holder.itemView.setOnClickListener(holder);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public TextView body;
        public TextView date;
        public TextView source;
        private ImageView thumb;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.news_title);
            body = view.findViewById(R.id.news_body);
            date = view.findViewById(R.id.news_date);
            source = view.findViewById(R.id.news_source);
            thumb = view.findViewById(R.id.news_pic);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(newsList.get(getAdapterPosition()).getLink())));
        }
    }
}
