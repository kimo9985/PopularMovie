package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jimtrinh9985gmail.popularmovie.DataModelReviews;
import com.jimtrinh9985gmail.popularmovie.DataModelTrailers;
import com.jimtrinh9985gmail.popularmovie.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * Created by Kimo on 12/20/2015.
 */
public class MyDetailView extends Activity {

    private SQLiteDatabase database;
    private ArrayList<Integer> idList = new ArrayList<>();
    ArrayList<String> list = new ArrayList<String>();
    private AsyncHttpClient client;
    private MyDetailViewAdapter myDetailViewAdapter;
    private MyDetailViewReviewAdapter myDetailViewReviewAdapter;

    private TextView detailTitle;
    private TextView detailDate;
    private TextView detailRating;
    private TextView detailSynopsis;
    private TextView detailID;
    private ImageView detailPoster;
    private String detailTrailer;
    public static String mDetailTrailer;
    private String detailReview;
    public static String mDetailReview;
    String mMovie;
    private MyDetailView myDetailView;

    private ListView listView3;
    private ListView listView4;

    public MyDetailView() {
        this.client = new AsyncHttpClient();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_detail);

        Bundle bundle = getIntent().getExtras();
        mMovie = bundle.getString("movie");

        MyDatabase myDatabase = new MyDatabase(this);
        database = myDatabase.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + MyDatabase.DATABASE_TABLE
                + " WHERE movie_title = '" + mMovie + "';";
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                idList.add(cursor.getInt(0));
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));
                list.add(cursor.getString(4));
                list.add(cursor.getString(5));
                list.add(cursor.getString(6));
                list.add(cursor.getString(7));
            } while (cursor.moveToNext());
        }
        cursor.moveToFirst();

        //Link & Set Detail Views//
        detailID = (TextView) findViewById(R.id.detailID);
        detailID.setText(cursor.getString(0));

        detailTitle = (TextView) findViewById(R.id.detailTitle);
        detailTitle.setText(cursor.getString(1));

        //Split movie title with colon//
        String movieTitle = detailTitle.getText().toString();
        String[] mTitle = movieTitle.split(":");

        detailDate = (TextView) findViewById(R.id.detailDate);
        detailDate.setText(cursor.getString(2));

        detailRating = (TextView) findViewById(R.id.detailRating);
        detailRating.setText(cursor.getString(3));

        detailSynopsis = (TextView) findViewById(R.id.detailSynopsis);
        detailSynopsis.setText(cursor.getString(4));

        detailPoster = (ImageView) findViewById(R.id.detailPoster);
        Picasso.with(this)
                .load(new File(Environment.getExternalStorageDirectory() + "/PopularMovie/" + mTitle[0] + ".jpg"))
                .error(R.drawable.no_data_image)
                .into(detailPoster);

        detailTrailer = cursor.getString(cursor.getColumnIndex("movie_trailers"));
        detailReview = cursor.getString(cursor.getColumnIndex("movie_reviews"));

        mDetailTrailer = detailTrailer;
        mDetailReview = detailReview;

        //ListView3 for My_Trailers//
        listView3 = (ListView) findViewById(R.id.listView3);
        ArrayList<DataModelTrailers> dataModelTrailers = new ArrayList<DataModelTrailers>();
        myDetailViewAdapter = new MyDetailViewAdapter(this, dataModelTrailers);
        listView3.setAdapter(myDetailViewAdapter);

        getMyMovieTrailers();

        //ListView4 for My_Reviews//
        listView4 = (ListView) findViewById(R.id.listView4);
        ArrayList<DataModelReviews> dataModelReviews = new ArrayList<DataModelReviews>();
        myDetailViewReviewAdapter = new MyDetailViewReviewAdapter(this, dataModelReviews);
        listView4.setAdapter(myDetailViewReviewAdapter);

        getMovieReviews();
    }

    public void getMyMovieTrailer(JsonHttpResponseHandler handler) {
        String url = mDetailTrailer;
        client.get(url, handler);
    }

    public void getMovieReview(JsonHttpResponseHandler handler) {
        String url = mDetailReview;
        client.get(url, handler);
    }

    //Fetch and Parse My Movie Trailers//
    private void getMyMovieTrailers() {
        myDetailView = new MyDetailView();
        myDetailView.getMyMovieTrailer(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray myTrailerData = null;

                try {
                    //Get JSON Data//
                    myTrailerData = response.getJSONArray("results");

                    //Parse JSON Data//
                    ArrayList<DataModelTrailers> dataModelTrailers = DataModelTrailers.fromJson(myTrailerData);

                    //Load Data into Adapter//
                    myDetailViewAdapter.addAll(dataModelTrailers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //Fetch and Parse My Movie Reviews//
    private void getMovieReviews() {
        myDetailView = new MyDetailView();
        myDetailView.getMovieReview(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray reviewData = null;

                try {
                    //Get JSON Data//
                    reviewData = response.getJSONArray("results");

                    //Parse JSON Data//
                    ArrayList<DataModelReviews> dataModelReviews = DataModelReviews.fromJson(reviewData);

                    //Load Data into Adapter//
                    myDetailViewReviewAdapter.addAll(dataModelReviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}