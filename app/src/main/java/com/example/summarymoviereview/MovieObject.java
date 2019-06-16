package com.example.summarymoviereview;

import java.io.Serializable;
import java.util.ArrayList;

public class MovieObject implements Serializable {
    public int ID;
    public String tilte;
    public String posterPath;
    public String backdropPath;
    public ArrayList<Integer> genreIDs;
    public String overview;
    public String releaseDate;
    public Double rating;

    public MovieObject(int ID, String title, String posterPath, String backdropPath, ArrayList<Integer> genreIDs, String overview, String releaseDate, Double rating) {
        this.ID = ID;
        this.tilte = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.genreIDs = genreIDs;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.rating = rating;
    }
}
