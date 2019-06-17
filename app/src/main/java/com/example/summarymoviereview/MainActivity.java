package com.example.summarymoviereview;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.SearchView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private SearchView mSearchView;
    private RecyclerView mTredingRecyclerview;
    private MovieInfoAdapter mTredingAdapter;
    private ArrayList<MovieObject> mTrendingMovies;
    private UpdateSearchResult mUpdateTrending;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.logo_with_padding);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        mUpdateTrending = new UpdateSearchResult() {
            @Override
            public void update(ArrayList<MovieObject> result) {
                if (result == null)
                    Log.d("Movie Info", "Null Result");
                Log.d("Movie Info", String.valueOf(result.size()));
                mTrendingMovies = result;
                mTredingAdapter.updateMovies(result);
            }
        };

        mTrendingMovies = new ArrayList<>();

        mTredingRecyclerview = findViewById(R.id.trending_recycler_view);
        mTredingAdapter = new MovieInfoAdapter(this, mTrendingMovies);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mTredingRecyclerview.setAdapter(mTredingAdapter);
        mTredingRecyclerview.setLayoutManager(layout);
        mTredingRecyclerview.setHasFixedSize(true);

        new NetworkUtils.FetchTrendingMovie(mUpdateTrending).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        mSearchView = (SearchView) menu.findItem(R.id.search_action).getActionView();

        mSearchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        mSearchView.setSubmitButtonEnabled(false);
        mSearchView.setQueryHint(getString(R.string.search_hint));
        return super.onCreateOptionsMenu(menu);
    }
}
