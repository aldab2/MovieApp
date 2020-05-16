package com.example.abdullah.movieproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {
    public ReviewAdapter mAdapter;
    RecyclerView mRecyclerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        mRecyclerList = findViewById(R.id.rv_list_review);
        mRecyclerList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ReviewAdapter(ReviewContainer.getReview());
        mRecyclerList.setAdapter(mAdapter);
        Log.d("YYYYYYYYY", "onCreate: Notifying DSCH" );
        mAdapter.notifyDataSetChanged();
    }

}
