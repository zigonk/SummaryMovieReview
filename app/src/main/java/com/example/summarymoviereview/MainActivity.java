package com.example.summarymoviereview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        demo();
    }

    private void demo() {
        ArrayList<MovieObject> movieObjects = null;
        try {
            movieObjects = new NetworkUtils.FetchMovieByTitle().execute("Nemo").get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new NetworkUtils.FetchReviewByMovieId().execute(movieObjects.get(0).ID);
    }
}
