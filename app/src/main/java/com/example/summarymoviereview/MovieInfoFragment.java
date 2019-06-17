package com.example.summarymoviereview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;
import com.google.api.services.language.v1beta2.model.Entity;

import java.util.ArrayList;
import java.util.Comparator;


public class MovieInfoFragment extends Fragment {
    public static final String REVIEW_INTENT = "review_intent";
    private static final String MOVIE_OBJECT_PARAM = "movie_object";


    private MovieObject mMovieObject;
    private Bitmap moviePoster, movieBackdrop;
    private static final String LARGE_SCREEN = "large_screen";
    private ArrayList<ReviewObject> mReviews;
    private ArrayList<Tag> positiveTags;
    private ArrayList<Tag> negativeTags;
    private TagView mOscars;
    private TagView mNegativeTagView;
    private TextView mPreview;
    private boolean mLargeScreen;
    private TextView mSeeAll;

    public static MovieInfoFragment newInstance(MovieObject movieObject, boolean largeScreen) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_OBJECT_PARAM, movieObject);
        args.putBoolean(LARGE_SCREEN, largeScreen);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateSentiment(ArrayList<ReviewObject> reviews) {
        mReviews = reviews;
        combineSentiment(reviews);
        mOscars.addTags(positiveTags);
        mNegativeTagView.addTags(negativeTags);

        if (mReviews.size() > 0)
            mPreview.setText(mReviews.get(0).content);
        else
            mPreview.setText("No Reviews");
    }

    public MovieInfoFragment() {
        // Required empty public constructor
    }

    private void combineSentiment(ArrayList<ReviewObject> reviews) {
        ArrayList<Entity> entityList = new ArrayList<>();
        for (ReviewObject review : reviews) {
            for (Entity entity : review.entities)
                entityList.add(entity);
        }

        ArrayList<Entity> combinedEntity = SentimentUtils.combineEntities(entityList);
        ArrayList<Entity> positiveEntities = new ArrayList<>();
        ArrayList<Entity> negativeEntities = new ArrayList<>();
        for (Entity entity : combinedEntity) {
            if (entity.getSentiment().getScore() > 0)
                positiveEntities.add(entity);
            else
                negativeEntities.add(entity);
        }
        positiveEntities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                if (o2.getSentiment().getScore() > o1.getSentiment().getScore())
                    return 1;
                else if (o2.getSentiment().getScore() < o1.getSentiment().getScore())
                    return -1;
                else
                    return 0;
            }
        });

        negativeEntities.sort(new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                if (o1.getSentiment().getScore() > o2.getSentiment().getScore())
                    return 1;
                else if (o1.getSentiment().getScore() < o2.getSentiment().getScore())
                    return -1;
                else
                    return 0;
            }
        });

        for (int i = 0; i < 5 && i < positiveEntities.size(); i++) {
            Tag tag = new Tag(positiveEntities.get(i).getName());
            tag.layoutColor = Color.parseColor("#16c100");
            positiveTags.add(tag);
        }

        for (int i = 0; i < 5 && i < negativeEntities.size(); i++) {
            Tag tag = new Tag(negativeEntities.get(i).getName());
            tag.layoutColor = Color.parseColor("#bf0019");
            negativeTags.add(tag);
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieObject = (MovieObject) getArguments().getSerializable(MOVIE_OBJECT_PARAM);
            mLargeScreen = getArguments().getBoolean(LARGE_SCREEN);
        }
        negativeTags = new ArrayList<>();
        positiveTags = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_movie_info, container, false);

        mOscars = v.findViewById(R.id.movie_info_oscar);
        mNegativeTagView = v.findViewById(R.id.movie_info_razzies);

        UpdatePoster updatePoster = new UpdatePoster() {
            @Override
            public void updatePoster(Bitmap result) {
                ImageView imageView = v.findViewById(R.id.movie_info_poster);
                imageView.setImageBitmap(result);
            }
        };

        UpdateBackdrops updateBackdrops = new UpdateBackdrops() {
            @Override
            public void updateBackdrops(Bitmap result, int position) {
                ImageView imageView = v.findViewById(R.id.movie_info_backdrop);
                imageView.setImageBitmap(result);
            }

            @Override
            public void updateBackdropOfImageView() {

            }
        };

        new NetworkUtils.downloadBackdropMovieList(updateBackdrops, -1).execute(mMovieObject.backdropPath);
        new NetworkUtils.downloadPosterMovie(updatePoster).execute(mMovieObject.posterPath);

        TextView titleTV = v.findViewById(R.id.movie_info_title);
        titleTV.setText(mMovieObject.tilte);

        TextView releaseTV = v.findViewById(R.id.movie_info_release_date);
        releaseTV.setText(mMovieObject.releaseDate);

        TextView summaryTV = v.findViewById(R.id.movie_info_summary);
        summaryTV.setText(mMovieObject.overview);

        mPreview = v.findViewById(R.id.movie_info_preview_review);
        mSeeAll = v.findViewById(R.id.movie_info_seeall);

        if (mLargeScreen) {
            TextView userreview = v.findViewById(R.id.textView7);
            userreview.setVisibility(View.INVISIBLE);
            mSeeAll.setVisibility(View.INVISIBLE);
            mPreview.setVisibility(View.INVISIBLE);
        } else {
            mSeeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ReviewActivity.class);

                    ArrayList<ReviewObject> filterEntity = mReviews;
                    for (ReviewObject review : filterEntity) {
                        review.entities = null;
                    }

                    intent.putExtra(REVIEW_INTENT, filterEntity);
                    startActivity(intent);
                }
            });
        }
        return v;
    }


}
