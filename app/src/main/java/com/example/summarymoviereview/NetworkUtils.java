package com.example.summarymoviereview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String BASE_URL = "https://api.themoviedb.org/3";

    private static final String API_PARAM = "api_key";

    private static final String TYPE_MOVIE_PARAMS = "movie";

    private static final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500";

    private static final String API_KEY = "9a6908a57b8db1273e38a6e15bc07af8";

    public static URL buildGetMovieTrendingUrl(String movieType, String movieTime) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("trending")
                .appendPath(movieType)
                .appendPath(movieTime)
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildGetMovieByTitle(String movieTitle, int page) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath("search")
                .appendPath(TYPE_MOVIE_PARAMS)
                .appendQueryParameter(API_PARAM, API_KEY)
                .appendQueryParameter("query", movieTitle)
                .appendQueryParameter("page", String.valueOf(page))
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildGetMovieReview(Integer movieId) {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendPath(TYPE_MOVIE_PARAMS)
                .appendPath(String.valueOf(movieId))
                .appendPath("reviews")
                .appendQueryParameter(API_PARAM, API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }

    }

    public static class DownloadPosterTask extends AsyncTask<String, Void, Bitmap> {
        ImageView mImageView;

        DownloadPosterTask(ImageView imageView) {
            mImageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            if (strings == null) return null;
            String posterUrl = IMAGE_BASE_URL + strings[0];
            Bitmap bitmap = null;
            try {
                InputStream in = new java.net.URL(posterUrl).openStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }

    public static class FetchMovieByTitle extends AsyncTask<String, Void, ArrayList<MovieObject>> {

        @Override
        protected ArrayList<MovieObject> doInBackground(String... strings) {
            if (strings.length == 0) return null;

            String query = strings[0];
            URL searchUrl = buildGetMovieByTitle(query, 1);

            try {
                String response = getResponseFromHttpUrl(searchUrl);
                return JsonUtils.convertJsonToMovieObjectList(response);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<MovieObject> movieObjects) {
            if (movieObjects == null) ;
            else {
                Log.d("MovieTitle", movieObjects.get(0).tilte);
            }
        }
    }

    public static class FetchReviewByMovieId extends AsyncTask<Integer, Void, ArrayList<ReviewObject>> {

        @Override
        protected ArrayList<ReviewObject> doInBackground(Integer... integers) {
            if (integers.length == 0) return null;
            Integer query = integers[0];
            URL reviewsUrl = buildGetMovieReview(query);

            try {
                String response = getResponseFromHttpUrl(reviewsUrl);
                return JsonUtils.convertJsonToReviewObjectList(response);
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(ArrayList<ReviewObject> reviewObjects) {
            if (reviewObjects == null) ;
            else {
                Log.d("Review content", reviewObjects.get(0).content);
            }
        }
    }
}