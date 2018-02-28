package com.ark.pushnotificationnewsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ark.pushnotificationnewsapp.R;
import com.ark.pushnotificationnewsapp.adapter.NewsAdapter;
import com.ark.pushnotificationnewsapp.model.News;
import com.ark.pushnotificationnewsapp.utils.AppConstant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewsActivity extends AppCompatActivity {

    private static final String TAG = "NewsActivity";
    private DatabaseReference mDatabase;
    private List<News> newsList;
    private RecyclerView newsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initViews();
        initFirebase();
        readNews();
    }

    private void initViews() {
        newsView = findViewById(R.id.news_recycler_view);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        newsView.setHasFixedSize(true);
        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        newsView.setLayoutManager(mLayoutManager);
    }

    private void readNews() {
        // Read from the database
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                showDataFromFirebase(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Do nothing
            }
        });
    }

    private void showDataFromFirebase(DataSnapshot dataSnapshot) {

        newsList = new ArrayList<>();
        newsList.clear();
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            News newsObj = ds.getValue(News.class);
            newsList.add(newsObj);

            Log.e(TAG, "news  : " + newsObj.getTitle());
        }
        setViewAdapter();
    }

    private void setViewAdapter() {
        NewsAdapter nAdapter = new NewsAdapter(this, newsList);
        newsView.setAdapter(nAdapter);
    }

    /**
     * To initalize database
     */
    private void initFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference(AppConstant.NEWS);

    }
}
