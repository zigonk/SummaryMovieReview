package com.example.summarymoviereview;

import android.app.SearchManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class SearchResultActivity extends AppCompatActivity {
    private ArrayList<MovieObject> mResult;
    private ArrayList<Bitmap> mResultPosters;
    private MovieInfoAdapter mResultAdapter;
    private RecyclerView mResultRecyclerView;
    private String mQuery;

    private UpdateBackdrops mUpdateBackdrops;
    private UpdateSearchResult mUpdateSearchResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        Log.d(SearchResultActivity.class.getSimpleName(), "Result Start");

        Intent intent = getIntent();

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            mQuery = intent.getStringExtra(SearchManager.QUERY);
            Log.d(SearchResultActivity.class.getSimpleName(), "Query: " + mQuery);
        }



//        try {
//            mResult = new NetworkUtils.FetchMovieByTitle(mUpdateSearchResults, 1).execute(mQuery).get();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        new NetworkUtils.FetchMovieByTitle(mUpdateSearchResults, 1).execute(mQuery);

        mResultRecyclerView = findViewById(R.id.result_recyclerview);
        mResultAdapter = new MovieInfoAdapter(mResult, mResultPosters);
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mResultRecyclerView.setAdapter(mResultAdapter);
        mResultRecyclerView.setLayoutManager(layout);

        mUpdateBackdrops = new UpdateBackdrops() {
            @Override
            public void updateBackdrops(ArrayList<Bitmap> results) {
                mResultPosters = results;
                mResultAdapter = new MovieInfoAdapter(mResult, mResultPosters);
                mResultRecyclerView.setAdapter(mResultAdapter);
            }
        };

        mUpdateSearchResults = new UpdateSearchResult() {
            @Override
            public void update(ArrayList<MovieObject> result) {
//                Log.d("Size", String.valueOf(mResult.size()));
                mResult  = result;
                Log.d("Size", String.valueOf(mResult.size()));
                mResultAdapter.update(result);
                mResultAdapter.notifyDataSetChanged();
//                mResultRecyclerView.setAdapter(mResultAdapter);
            }
        };
        new NetworkUtils.FetchMovieByTitle(mUpdateSearchResults, 1).execute(mQuery);





//        Log.d(SearchResultActivity.class.getSimpleName(), String.valueOf(mResult.size()));
//
//        String[] backdropUrl =  new String[mResult.size()];
//
//        for (int i = 0; i < mResult.size(); i++) {
//            backdropUrl[i] = mResult.get(i).posterPath;
//        }
//        new NetworkUtils.DownloadPosterTask(mUpdateBackdrops).execute(backdropUrl);



    }

//    private void getResultFromQueryAsync(String query, int page) {
//       new NetworkUtils.FetchMovieByTitle(mResultAdapter, mResult,page).execute(query);
//    }
//
//    private void getResultPoster() {
//        mResultPosters = new ArrayList<>();
//        for (MovieObject movie : mResult) {
//            try {
//                mResultPosters.add(new NetworkUtils.DownloadPosterTask().execute(movie.backdropPath).get());
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
