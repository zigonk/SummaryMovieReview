package com.example.summarymoviereview;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;


public class MovieInfoActivity extends AppCompatActivity {
    private FrameLayout mLeftLayout;
    private FrameLayout mRightLayout;
    private MovieObject mMovieObject;
    private ArrayList<ReviewObject> mReviews;
    private UpdateSentiment mUpdateSentiment;
    private UpdateReviewList mUpdateReviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = getIntent();
        mMovieObject = (MovieObject) intent.getSerializableExtra(MovieInfoAdapter.MOVIE_OBJECT_INTENT);


        mReviews = new ArrayList<>();

        mUpdateSentiment = new UpdateSentiment() {
            @Override
            public void updateSentiment(ReviewObject result, int pos) {
                mReviews.set(pos, result);
            }
        };

        mUpdateReviewList = new UpdateReviewList() {
            @Override
            public void update(ArrayList<ReviewObject> reviews) {
                mReviews = reviews;
                // mReviewAdapter.updateData(reviews);
            }
        };

        new NetworkUtils.FetchReviewByMovieId(mUpdateReviewList).execute(mMovieObject.ID);


        mLeftLayout = findViewById(R.id.left_layout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.left_layout, MovieInfoFragment.newInstance(mMovieObject));

        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mRightLayout = findViewById(R.id.right_layout);
            fragmentTransaction.replace(R.id.right_layout, ReviewFragment.newInstance(mReviews));
        }
        mRightLayout = findViewById(R.id.right_layout);
        Log.d("Reviews", String.valueOf(mReviews.size()));
        fragmentTransaction.replace(R.id.right_layout, ReviewFragment.newInstance(mReviews));
        fragmentTransaction.commit();


    }


    public interface UpdateReviewList {
        void update(ArrayList<ReviewObject> reviews);
    }
}
