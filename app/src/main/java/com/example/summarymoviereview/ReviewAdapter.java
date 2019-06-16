package com.example.summarymoviereview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private ArrayList<ReviewObject> mReviews;
    private Context mContext;

    public ReviewAdapter(Context context, ArrayList<ReviewObject> reviews) {
        mContext = context;
        mReviews = reviews;
    }

    public void updateData(ArrayList<ReviewObject> reviews) {
        mReviews = reviews;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.review_layout, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder viewHolder, int i) {
        final ReviewObject currentReview = mReviews.get(i);
        viewHolder.mUserTV.setText(currentReview.author);
        viewHolder.mExpandableTextView.setText(currentReview.content);
        boolean positive = currentReview.sentiment >= 0;
        int sentiment_score_scale = (int) Math.abs(currentReview.sentiment * 100);

//        UpdateSentiment updateSentiment = new UpdateSentiment() {
//            @Override
//            public void updateSentiment(ReviewObject result) {
//                currentReview.sentiment = result.sentiment;
//                currentReview.entities = result.entities;
//                noti
//            }
//        };

        String sentiment_score_string = sentiment_score_scale + "%";
        viewHolder.mSentimentTV.setText(sentiment_score_string);

        if (positive) {
            viewHolder.mSentimentTV.setTextColor(Color.parseColor("#1eea2f"));
            viewHolder.mSentimentIV.setImageResource(R.drawable.popcorn_good);
        } else {
            viewHolder.mSentimentTV.setTextColor(Color.parseColor("#db1515"));
            viewHolder.mSentimentIV.setImageResource(R.drawable.popcorn_bad);
        }


    }

    @Override
    public int getItemCount() {
        Log.d("Review_Adapter", String.valueOf(mReviews.size()));
        if (mReviews == null)
            return 0;
        else
            return mReviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mSentimentIV;
        private TextView mUserTV;
        private TextView mSentimentTV;
        private ExpandableTextView mExpandableTextView;

        ViewHolder(View v) {
            super(v);
            mSentimentTV = v.findViewById(R.id.review_sentiment_textview);
            mUserTV = v.findViewById(R.id.review_user);
            mSentimentIV = v.findViewById(R.id.review_sentiment_imageview);
            mExpandableTextView = v.findViewById(R.id.expand_text_view);
        }
    }
}
