package com.jimtrinh9985gmail.popularmovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kimo on 12/1/2015.
 */
public class DataModelMovies implements Serializable {

    private String originalTitle;
    private String posterSmallThumbnail;
    private String posterLargeThumbnail;
    private String overview;
    private String averageVote;
    private String releaseDate;
    private String movieID;

    public static DataModelMovies fromJson(JSONObject jsonObject) {
        DataModelMovies dataModelMovie = new DataModelMovies();

        try {
            //Deserialize JSON//
            dataModelMovie.originalTitle = jsonObject.getString("original_title");
            dataModelMovie.posterSmallThumbnail = jsonObject.getString("poster_path");
            dataModelMovie.posterLargeThumbnail = jsonObject.getString("poster_path");
            dataModelMovie.overview = jsonObject.getString("overview");
            dataModelMovie.averageVote = jsonObject.getString("vote_average");
            dataModelMovie.releaseDate = jsonObject.getString("release_date");
            dataModelMovie.movieID = jsonObject.getString("id");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dataModelMovie;
    }

    //Decode JSONArray into data model//
    public static ArrayList<DataModelMovies> fromJson(JSONArray jsonArray) {
        ArrayList<DataModelMovies> dataModelMovies = new ArrayList<DataModelMovies>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            DataModelMovies dataModelMovie = DataModelMovies.fromJson(object);
            if (dataModelMovie !=null) {
                dataModelMovies.add(dataModelMovie);
            }
        }
        return dataModelMovies;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterSmallThumbnail() {
        return "http://image.tmdb.org/t/p/w185/" + posterSmallThumbnail;
    }

    public void setPosterSmallThumbnail(String posterSmallThumbnail) {
        this.posterSmallThumbnail = posterSmallThumbnail;
    }

    public String getPosterLargeThumbnail() {
        return "http://image.tmdb.org/t/p/w780/" + posterLargeThumbnail;
    }

    public void setPosterLargeThumbnail(String posterSmallThumbnail) {
        this.posterSmallThumbnail = posterSmallThumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getAverageVote() {
        return averageVote;
    }

    public void setAverageVote(String averageVote) {
        this.averageVote = averageVote;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getMovieID() {
        return "/movie/" + movieID;
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
    }
}