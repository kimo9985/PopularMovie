package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

/**
 * Created by Kimo on 12/1/2015.
 */
public class MyMovieDataModel {

    private int _id;
    private int myStatus;
    private String myTitle;
    private String myReleaseDate;
    private String myRating;
    private String mySynopsis;

    public MyMovieDataModel() {
        this.myTitle = null;
        this.myReleaseDate = null;
        this.myRating = null;
        this.mySynopsis = null;
        this.myStatus = 0;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getMyStatus() {
        return myStatus;
    }

    public void setMyStatus(int myStatus) {
        this.myStatus = myStatus;
    }

    public String getMyTitle() {
        return myTitle;
    }

    public void setMyTitle(String myTitle) {
        this.myTitle = myTitle;
    }

    public String getMyReleaseDate() {
        return myReleaseDate;
    }

    public void setMyReleaseDate(String myReleaseDate) {
        this.myReleaseDate = myReleaseDate;
    }

    public String getMyRating() {
        return myRating;
    }

    public void setMyRating(String myRating) {
        this.myRating = myRating;
    }

    public String getMySynopsis() {
        return mySynopsis;
    }

    public void setMySynopsis(String mySynopsis) {
        this.mySynopsis = mySynopsis;
    }
}