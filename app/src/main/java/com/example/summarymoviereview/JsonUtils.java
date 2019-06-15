package com.example.summarymoviereview;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String POSTER_KEY = "poster_path";
    private static final String BACKDROP_KEY = "backdrop_path";
    private static final String GENRES_KEY = "genre_ids";
    private static final String OVERVIEW_KEY = "overview";
    private static final String YEAR_KEY = "release_date";
    private static final String RATING_KEY = "vote_average";
    private static final String CONTENT_KEY = "content";
    private static final String AUTHOR_KEY = "author";
    private static final String URL_KEY = "url";
    private static final String RESULT_KEY = "results";

    public static MovieObject convertJsonToMovieObject(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        int ID = jsonObject.getInt(ID_KEY);
        String title = jsonObject.getString(TITLE_KEY);
        String posterPath = jsonObject.getString(POSTER_KEY);
        String backdropPath = jsonObject.getString(BACKDROP_KEY);
        JSONArray jGenreIds = jsonObject.getJSONArray(GENRES_KEY);
        ArrayList<Integer> genreIDs = new ArrayList<>();
        for (int j = 0; j < jGenreIds.length(); ++j) {
            int id;
            id = jGenreIds.getInt(j);
            genreIDs.add(id);
        }
        String overview = jsonObject.getString(OVERVIEW_KEY);
        String releaseDate = jsonObject.getString(YEAR_KEY);
        Double rating = jsonObject.getDouble(RATING_KEY);

        MovieObject movieObject = new MovieObject(ID, title, posterPath, backdropPath, genreIDs, overview, releaseDate, rating);
        return movieObject;
    }

    public static ArrayList<MovieObject> convertJsonToMovieObjectList(String json) throws JSONException {
        ArrayList<MovieObject> movieObjects = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        JSONArray results = jsonObject.getJSONArray(RESULT_KEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            MovieObject movieObject = convertJsonToMovieObject(result.toString());
            movieObjects.add(movieObject);
        }
        return movieObjects;
    }

    public static ReviewObject convertJsonToReviewObject(String json) throws JSONException {
        JSONObject jsonObject = new JSONObject(json);
        String ID = jsonObject.getString(ID_KEY);
        String author = jsonObject.getString(AUTHOR_KEY);
        String content = jsonObject.getString(CONTENT_KEY);
        String url = jsonObject.getString(URL_KEY);

        ReviewObject reviewObject = new ReviewObject(ID, author, content, url);
        return reviewObject;
    }

    public static ArrayList<ReviewObject> convertJsonToReviewObjectList(String json) throws JSONException {
        ArrayList<ReviewObject> reviewObjects = new ArrayList<>();

        JSONObject jsonObject = new JSONObject(json);

        JSONArray results = jsonObject.getJSONArray(RESULT_KEY);
        for (int i = 0; i < results.length(); i++) {
            JSONObject result = results.getJSONObject(i);
            ReviewObject reviewObject = convertJsonToReviewObject(result.toString());
            reviewObjects.add(reviewObject);
        }
        return reviewObjects;
    }
}
