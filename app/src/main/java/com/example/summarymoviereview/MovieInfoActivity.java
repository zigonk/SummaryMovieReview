package com.example.summarymoviereview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;


public class MovieInfoActivity extends AppCompatActivity {
    private FrameLayout mLeftLayout;
    private MovieObject mMovieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        Intent intent = getIntent();
        mMovieObject = (MovieObject) intent.getSerializableExtra(MovieInfoAdapter.MOVIE_OBJECT_INTENT);


//        ArrayList<Tag> tags = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            if (i % 3 == 0)
//                tags.add(new Tag("Oscar"));
//            else if (i % 2 == 0)
//                tags.add(new Tag("Not oscars"));
//            else
//                tags.add(new Tag("Fucking Long Text, I don't Know"));
//        }
//        TagView oscar = findViewById(R.id.movie_info_oscar);
//        oscar.addTags(tags);
//
        mLeftLayout = findViewById(R.id.left_layout);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.left_layout, MovieInfoFragment.newInstance(mMovieObject));
        fragmentTransaction.commit();
    }
}
