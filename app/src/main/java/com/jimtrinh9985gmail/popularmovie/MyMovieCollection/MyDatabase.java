package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 12/1/2015.
 */
public class MyDatabase extends SQLiteOpenHelper {

    private Context mContext;
    SQLiteDatabase database;
    private static final String TAG = "popularmovies";

    //Database info:  dbase version/dbase name/dbase table name//
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "myMovieCollection";
    public static final String DATABASE_TABLE = "myMovies";

    //Database Table Columns//
    private static final String KEY_ID = "_id";
    private static final String KEY_TITLE = "movie_title";
    private static final String KEY_RELEASE_DATE = "movie_release_date";
    private static final String KEY_RATING = "movie_rating";
    private static final String KEY_SYNOPSIS = "movie_synopsis";
    private static final String KEY_POSTER = "movie_poster";
    private static final String KEY_TRAILERS = "movie_trailers";
    private static final String KEY_REVIEWS = "movie_reviews";
    private static final String KEY_STATUS = "status";
    private static final String CREATE_MOVIE_TABLE = "CREATE TABLE " + DATABASE_TABLE + "("
            + KEY_ID + " INTEGER PRIMARY KEY, "
            + KEY_TITLE + " TEXT,"
            + KEY_RELEASE_DATE + " TEXT,"
            + KEY_RATING + " TEXT,"
            + KEY_SYNOPSIS + " TEXT,"
            + KEY_POSTER + " TEXT,"
            + KEY_TRAILERS + " TEXT,"
            + KEY_REVIEWS + " TEXT,"
            + KEY_STATUS + " TEXT)";
    private static final String DROP_MOVIE_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    //Creating Database Table//
    public void onCreate(SQLiteDatabase db) {
        //CREATE MOVIE_TABLE myMovies (_id INTEGER PRIMARY KEY AUTOINCREMENT)//
        try {
            db.execSQL(CREATE_MOVIE_TABLE);
            Log.d(TAG, "Database Created");
        } catch (SQLException e) {
        }
    }

    //Upgrading Database//
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(DROP_MOVIE_TABLE);
            onCreate(db);
            Log.d(TAG, "Database Upgraded");
        } catch (SQLException e) {
        }
    }

    //Insert Movie to Database//
    public long insertMovie(String detailTitle, String detailSynopsis, String detailDate,
                            String detailRating, String detailPoster, String detailTrailer,
                            String detailReview, int detailStatus) {

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, detailTitle);
        contentValues.put(KEY_RELEASE_DATE, detailDate);
        contentValues.put(KEY_RATING, detailRating);
        contentValues.put(KEY_SYNOPSIS, detailSynopsis);
        contentValues.put(KEY_POSTER, detailPoster);
        contentValues.put(KEY_TRAILERS, detailTrailer);
        contentValues.put(KEY_REVIEWS, detailReview);
        contentValues.put(KEY_STATUS, detailStatus);
        database.insert(DATABASE_TABLE, null, contentValues);
        Log.d(TAG, "insertMovie - Movie Data Inserted!");
        database.close();
        return 0;
    }

    //Get Views//
    public List<MyMovieDataModel> getAllItems() {
        List<MyMovieDataModel> myMovieDataModels = new ArrayList<MyMovieDataModel>();

        //Query all//
        String selectQuery = "SELECT  * FROM " + DATABASE_TABLE;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        //Loop through all Rows and add to ListView//
        if (cursor.moveToFirst()) {
            do {
                MyMovieDataModel myMovieDataModel = new MyMovieDataModel();
                myMovieDataModel.set_id(cursor.getInt(0));
                myMovieDataModel.setMyTitle(cursor.getString(1));
                myMovieDataModel.setMyStatus(cursor.getInt(2));

                myMovieDataModels.add(myMovieDataModel);
            } while (cursor.moveToNext());
        }
        return myMovieDataModels;
    }

    //Update Checkbox status//
    public void updateList(MyMovieDataModel myMovieDataModel) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, myMovieDataModel.getMyTitle());
        contentValues.put(KEY_STATUS, myMovieDataModel.getMyStatus());
        database.update(DATABASE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(myMovieDataModel.get_id())});
    }

    //Delete Checkbox with 1//
    public void deleteItem(View view) {
        getDeletedPoster();
        deleteSQL();
    }

    //Get and Delete Movie Poster from SD Card//
    public void getDeletedPoster() {

        //Iterate to find movies to delete//
        Cursor cursor = null;
        String selectQuery = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + KEY_STATUS + " = 1";
        SQLiteDatabase database = this.getWritableDatabase();
        cursor = database.rawQuery(selectQuery, null);

        while (cursor !=null && cursor.moveToNext()) {
            Log.e(TAG, "Query: " + selectQuery);

            //Split movie title with colon//
            String movieTitle = cursor.getString(cursor.getColumnIndex(KEY_TITLE));
            String[] mTitle = movieTitle.split(":");
            String deleteMovie = "/PopularMovie/" + mTitle[0] + ".jpg";

            Log.e(TAG, "DeletePoster movieTitle: " + movieTitle);
            Log.e(TAG, "DeletePoster deleteMovie: " + deleteMovie);

            //Delete movie from External SD Card//
            File file = new File(Environment.getExternalStorageDirectory() + deleteMovie);
            boolean deleted = file.delete();
        }
    }

    //Delete SQL with Checkbox 1//
    public void deleteSQL() {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(DATABASE_TABLE, KEY_STATUS + "=" + "1", null);
        database.close();
    }

}