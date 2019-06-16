package com.example.summarymoviereview;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import java.util.ArrayList;


public class MovieInfoActivity extends AppCompatActivity {
    private FrameLayout mLeftLayout;
    private FrameLayout mRightLayout;
    private MovieObject mMovieObject;
    private ArrayList<ReviewObject> mReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = getIntent();
        mMovieObject = (MovieObject) intent.getSerializableExtra(MovieInfoAdapter.MOVIE_OBJECT_INTENT);



        mLeftLayout = findViewById(R.id.left_layout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.left_layout, MovieInfoFragment.newInstance(mMovieObject));

        if ((getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK) ==
                Configuration.SCREENLAYOUT_SIZE_LARGE) {
            mRightLayout = findViewById(R.id.right_layout);
            fragmentTransaction.replace(R.id.right_layout, ReviewFragment.newInstance(mMovieObject.ID));
        }
        fragmentTransaction.commit();


    }
}
