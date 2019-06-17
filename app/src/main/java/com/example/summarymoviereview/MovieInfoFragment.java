package com.example.summarymoviereview;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;


///**
// * A simple {@link Fragment} subclass.
// * Activities that contain this fragment must implement the
// * {@link MovieInfoFragment.OnFragmentInteractionListener} interface
// * to handle interaction events.
// * Use the {@link MovieInfoFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MovieInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String MOVIE_OBJECT_PARAM = "param1";


    private MovieObject mMovieObject;
    private Bitmap moviePoster, movieBackdrop;

//    private OnFragmentInteractionListener mListener;

    public MovieInfoFragment() {
        // Required empty public constructor
    }


    public static MovieInfoFragment newInstance(MovieObject movieObject) {
        MovieInfoFragment fragment = new MovieInfoFragment();
        Bundle args = new Bundle();
        args.putSerializable(MOVIE_OBJECT_PARAM, movieObject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMovieObject = (MovieObject) getArguments().getSerializable(MOVIE_OBJECT_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_movie_info, container, false);
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0)
                tags.add(new Tag("Oscar"));
            else if (i % 2 == 0)
                tags.add(new Tag("Not oscars"));
            else
                tags.add(new Tag("Fucking Long Text, I don't Know"));
        }
        TagView oscar = v.findViewById(R.id.movie_info_oscar);
        oscar.addTags(tags);

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

        return v;
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
