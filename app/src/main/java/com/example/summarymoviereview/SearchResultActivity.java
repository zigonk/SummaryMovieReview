package com.example.summarymoviereview;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class SearchResultActivity extends AppCompatActivity {
    private ArrayList<MovieObject> mResult;
    private MovieInfoAdapter mResultAdapter;
    private RecyclerView mResultRecyclerView;
    private String mQuery;


    private UpdateSearchResult mUpdateSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Log.d(SearchResultActivity.class.getSimpleName(), "Result Start");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_with_padding);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            Log.d(SearchResultActivity.class.getSimpleName(), "Query: " + mQuery);
        }


        mResultRecyclerView = findViewById(R.id.result_recyclerview);
        mResultAdapter = new MovieInfoAdapter(this, mResult);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mResultRecyclerView.setAdapter(mResultAdapter);
        mResultRecyclerView.setLayoutManager(layout);
        mResultRecyclerView.setHasFixedSize(true);


        mUpdateSearchResults = new UpdateSearchResult() {
            @Override
            public void update(ArrayList<MovieObject> result) {
                mResult  = result;
                mResultAdapter.updateMovies(result);
            }
        };
        new NetworkUtils.FetchMovieByTitle(mUpdateSearchResults, 1).execute(mQuery);


    }

}
