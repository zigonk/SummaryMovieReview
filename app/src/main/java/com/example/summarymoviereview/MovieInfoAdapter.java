package com.example.summarymoviereview;

import android.content.Context;
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
import java.util.TreeMap;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.ViewHolder> {

    private TreeMap<Integer, Bitmap> mBitmaps;
    private ArrayList<MovieObject> mMovies;
    private Context mContext;
    private UpdateBackdrops mUpdateBackdrop;

    public MovieInfoAdapter(Context context, ArrayList<MovieObject> movies) {
        mMovies = movies;
        mContext = context;
        mBitmaps = new TreeMap<>();

        mUpdateBackdrop = new UpdateBackdrops() {
            @Override
            public void updateBackdropOfImageView() {
                notifyDataSetChanged();
            }

            @Override
            public void updateBackdrops(Bitmap result, int position) {
                mBitmaps.put(position, result);
            }
        };
    }

    public void updateMovies(ArrayList<MovieObject> movies) {
        mMovies = movies;
        notifyDataSetChanged();
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
        Log.d("Movie Info", String.valueOf(movieObject.tilte));
        Log.d("Movie Info", String.valueOf(movieObject.rating));
        Log.d("Movie Info", String.valueOf(movieObject.backdropPath));
        viewHolder.mRatingBar.setRating((float) movieObject.rating.doubleValue() / 2);
        viewHolder.mTitleTextView.setText(movieObject.tilte);
        viewHolder.mTitleTextView.setTextColor(Color.parseColor("#FFFFFF"));
        viewHolder.mBackdropImageView.setBackgroundColor(Color.parseColor("#FFFFFF"));

        if (mBitmaps.containsKey(i)) {
            viewHolder.mBackdropImageView.setImageBitmap(mBitmaps.get(i));
        } else if (!movieObject.backdropPath.equals("null"))
            new NetworkUtils.DownloadPosterTask(viewHolder.mBackdropImageView, mUpdateBackdrop, i).execute(movieObject.backdropPath);
        else
            viewHolder.mTitleTextView.setTextColor(Color.parseColor("#000000"));
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
