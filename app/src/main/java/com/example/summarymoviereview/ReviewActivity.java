package com.example.summarymoviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class ReviewActivity extends AppCompatActivity {

    private FrameLayout mFrameLayout;
    private ReviewFragment mReviewFragment;
    private ArrayList<ReviewObject> mReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        Intent intent = getIntent();
        mReviews = (ArrayList<ReviewObject>) intent.getSerializableExtra(MovieInfoFragment.REVIEW_INTENT);
        Log.d("Review_Activity", String.valueOf(mReviews.size()));
        mFrameLayout = findViewById(R.id.review_frame_layout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        mReviewFragment = ReviewFragment.newInstance(mReviews);
        fragmentTransaction.replace(R.id.review_frame_layout, mReviewFragment);
        fragmentTransaction.commit();
    }
}
