package com.example.summarymoviereview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.willy.ratingbar.ScaleRatingBar;

import java.util.ArrayList;
import java.util.TreeMap;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.ViewHolder> {

    public static final String MOVIE_OBJECT_INTENT = "movie_object_intent";
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
        ArrayList<MovieObject> filterMovies = new ArrayList<>();
        for (int i = 0; i < movies.size(); i++) {
            if (!movies.get(i).backdropPath.equals("null"))
                filterMovies.add(movies.get(i));
        }
        mMovies = filterMovies;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mBackdropImageView;
        private ScaleRatingBar mRatingBar;
        private TextView mTitleTextView;
        private int id = -1;


        public ViewHolder(View v) {
            super(v);
            mBackdropImageView = v.findViewById(R.id.movie_cardview_backdrop);
            mRatingBar = v.findViewById(R.id.movie_cardview_ratingbar);
            mTitleTextView = v.findViewById(R.id.movie_cardview_title);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Intent intent = new Intent(mContext, MovieInfoActivity.class);
                    intent.putExtra(MOVIE_OBJECT_INTENT, mMovies.get(pos));
                    mContext.startActivity(intent);
                }
            });
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
        viewHolder.mRatingBar.setRating((float) movieObject.rating.doubleValue() / 2);
        viewHolder.mTitleTextView.setText(movieObject.tilte);
        if (viewHolder.id != i) {
            viewHolder.mTitleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            viewHolder.mBackdropImageView.setImageBitmap(null);
            viewHolder.mBackdropImageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        if (mBitmaps.containsKey(i) && viewHolder.id != i) {
            viewHolder.mBackdropImageView.setImageBitmap(mBitmaps.get(i));
            viewHolder.id = i;
        } else if (!mBitmaps.containsKey(i) && !movieObject.backdropPath.equals("null")) {
            new NetworkUtils.DownloadPosterTask(mUpdateBackdrop, i).execute(movieObject.backdropPath);
        }
        else if (movieObject.backdropPath.equals("null")) {
            viewHolder.mTitleTextView.setTextColor(Color.parseColor("#000000"));
            viewHolder.id = i;
        }
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
