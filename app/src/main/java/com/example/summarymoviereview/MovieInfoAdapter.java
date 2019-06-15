package com.example.summarymoviereview;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.ViewHolder> {

    private ArrayList<MovieObject> mMovies;
    private ArrayList<Bitmap> mBackdrops;

    public void update(ArrayList<MovieObject> movies) {
        mMovies = movies;
    }

    public MovieInfoAdapter(ArrayList<MovieObject> movies, ArrayList<Bitmap> backdrops) {
        mMovies = movies;
        mBackdrops = backdrops;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mBackdropImageView;
        private RatingBar mRatingBar;
        private TextView mTitleTextView;


        public ViewHolder(View v) {
            super(v);
            mBackdropImageView = v.findViewById(R.id.movie_cardview_backdrop);
            mRatingBar = v.findViewById(R.id.movie_cardview_ratingbar);
            mTitleTextView = v.findViewById(R.id.movie_cardview_title);
        }
    }

    @NonNull
    @Override
    public MovieInfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_cardview,
                viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieInfoAdapter.ViewHolder viewHolder, int i) {
        MovieObject movieObject = mMovies.get(i);
        Log.d(MovieInfoAdapter.class.getSimpleName(), String.valueOf(movieObject.tilte));
        Bitmap backdrop = null;
        if (mBackdrops != null)
             backdrop = mBackdrops.get(i);
        viewHolder.mRatingBar.setRating((float) movieObject.rating.doubleValue());
        viewHolder.mTitleTextView.setText(movieObject.tilte);
        if (backdrop != null)
            viewHolder.mBackdropImageView.setImageBitmap(backdrop);
        else
            viewHolder.mBackdropImageView.setBackgroundColor(Color.parseColor("#000000"));
    }

    @Override
    public int getItemCount() {
//        Log.d(MovieInfoAdapter.class.getSimpleName(), String.valueOf(mMovies.size()));
        if (mMovies == null)
            return 0;
        else
            return mMovies.size();

    }
}
