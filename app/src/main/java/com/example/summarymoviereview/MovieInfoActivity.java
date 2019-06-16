package com.example.summarymoviereview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import java.util.ArrayList;


public class MovieInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        ArrayList<Tag> tags = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0)
                tags.add(new Tag("Oscar"));
            else if (i % 2 == 0)
                tags.add(new Tag("Not oscars"));
            else
                tags.add(new Tag("Fucking Long Text, I don't Know"));
        }
        TagView oscar = findViewById(R.id.movie_info_oscar);
        oscar.addTags(tags);
    }
}
