package com.example.summarymoviereview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ReviewObject> mReviews;
    private Context mContext;

    public ReviewAdapter(Context context, ArrayList<ReviewObject> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder viewHolder, int i) {
        ReviewObject currentReview = mReviews.get(i);
        viewHolder.mUserTV.setText(currentReview.author);
        viewHolder.mReviewTV.setText(currentReview.content);
        boolean positive = currentReview.sentiment >= 0;
        int sentiment_score_scale = (int) Math.abs(currentReview.sentiment * 100);
        String sentiment_score_string = sentiment_score_scale + "%";
        viewHolder.mSentimentTV.setText(sentiment_score_string);

        if (positive) {

        }


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mSentimentIV;
        private TextView mUserTV;
        private TextView mSentimentTV;
        private TextView mReviewTV;

        ViewHolder(View v) {
            super(v);
            mReviewTV = v.findViewById(R.id.review_content);
            mSentimentTV = v.findViewById(R.id.review_sentiment_textview);
            mUserTV = v.findViewById(R.id.review_user);
            mSentimentTV = v.findViewById(R.id.review_sentiment_imageview);
        }
    }
}
