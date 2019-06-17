package com.example.summarymoviereview;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class ReviewFragment extends Fragment {

    private static final String REVIEWS_PARAM = "reviews_param";

    private ArrayList<ReviewObject> mReviews;
    private RecyclerView mReviewLists;
    private ReviewAdapter mReviewAdapter;
    private int mID;


    public void updateReviews(ArrayList<ReviewObject> reviews) {
        mReviews = reviews;
        Log.d("Review_Fragment", String.valueOf(mReviews.size()));
        mReviewAdapter.updateData(mReviews);
    }



    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(ArrayList<ReviewObject> reviews) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(REVIEWS_PARAM, reviews);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReviews = new ArrayList<>();
        if (getArguments() != null) {
            mReviews = (ArrayList<ReviewObject>) getArguments().getSerializable(REVIEWS_PARAM);
        }

        Log.d("Review_Fragment", String.valueOf(mReviews.size()));
        Log.d("Review_Fragment", "Created");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_review, container, false);
        mReviewLists = v.findViewById(R.id.fragment_review_list);


        mReviewAdapter = new ReviewAdapter(container.getContext(), mReviews);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false);
        mReviewLists.setAdapter(mReviewAdapter);
        mReviewLists.setLayoutManager(layoutManager);

        Log.d("Review_Fragment", "View Created");


//        Log.d("Review_Fragment", String.valueOf(mID));

//        Log.d("Review_Fragment", String.valueOf(mReviews));
        return v;
    }


}
