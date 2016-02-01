package com.jimtrinh9985gmail.popularmovie;

import android.app.Activity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by Kimo on 12/1/2015.
 */
public class MovieDbAPI extends Activity {

    private final String API_KEY = "Insert your Key Here";
    private final String API_BASE_URL = "http://api.themoviedb.org/3";
    private AsyncHttpClient client;
    private String movieKey = DetailViewFragment.movieID;
    private String trailerKey = movieKey + "/videos?";
    private String reviewKey = movieKey + "/reviews?";
    public static String trailerUrl;
    public static String reviewUrl;

    public MovieDbAPI() {
        this.client = new AsyncHttpClient();
    }

    private String getApiUrl(String relativeUrl) {
        return API_BASE_URL + relativeUrl;
    }

    public void getPopularMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("/discover/movie?sort_by=popularity.desc&");
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
    }

    public void getHighestRatedMovies(JsonHttpResponseHandler handler) {
        String url = getApiUrl("/discover/movie?sort_by=vote_count.desc&");
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
    }

    public void getMovieTrailers(JsonHttpResponseHandler handler) {
        String url = getApiUrl(trailerKey);
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
        trailerUrl = url + params;
    }

    public void getMovieReviews(JsonHttpResponseHandler handler) {
        String url = getApiUrl(reviewKey);
        RequestParams params = new RequestParams("api_key", API_KEY);
        client.get(url, params, handler);
        reviewUrl = url + params;
    }
}