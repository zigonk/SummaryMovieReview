package com.example.summarymoviereview;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;


public class MovieInfoActivity extends AppCompatActivity {
    private FrameLayout mLeftLayout;
    private FrameLayout mRightLayout;
    private MovieObject mMovieObject;
    private ArrayList<ReviewObject> mReviews;
    private UpdateReviewList mUpdateReviewList;
    private ReviewFragment mReviewFragment;
    private MovieInfoFragment mMovieInfoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = getIntent();
        mMovieObject = (MovieObject) intent.getSerializableExtra(MovieInfoAdapter.MOVIE_OBJECT_INTENT);

        mUpdateReviewList = new UpdateReviewList() {
            @Override
            public void update(ArrayList<ReviewObject> reviews) {
                mReviews = reviews;
                if (mReviewFragment != null)
                    mReviewFragment.updateReviews(reviews);
                mMovieInfoFragment.updateSentiment(reviews);

            }
        };

        mReviews = new ArrayList<>();

        new NetworkUtils.FetchReviewByMovieId(mUpdateReviewList).execute(mMovieObject.ID);


        mLeftLayout = findViewById(R.id.left_layout);
        mRightLayout = findViewById(R.id.right_layout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        if (diagonalInches>=6.5){
            fragmentTransaction.replace(R.id.right_layout, ReviewFragment.newInstance(mReviews));
            mMovieInfoFragment = MovieInfoFragment.newInstance(mMovieObject, true);
            mRightLayout = findViewById(R.id.right_layout);
            Log.d("Reviews", String.valueOf(mReviews.size()));
            mReviewFragment = ReviewFragment.newInstance(mReviews);
            fragmentTransaction.replace(R.id.right_layout, mReviewFragment);
            // 6.5inch device or bigger
        }else{
            // smaller device
            mMovieInfoFragment = MovieInfoFragment.newInstance(mMovieObject, false);
        }

        fragmentTransaction.replace(R.id.left_layout, mMovieInfoFragment);
//
//        mRightLayout = findViewById(R.id.right_layout);
//        Log.d("Reviews", String.valueOf(mReviews.size()));
//        mReviewFragment = ReviewFragment.newInstance(mReviews);
//        fragmentTransaction.replace(R.id.right_layout, mReviewFragment);
        fragmentTransaction.commit();


    }


    public interface UpdateReviewList {
        void update(ArrayList<ReviewObject> reviews);
    }
}
