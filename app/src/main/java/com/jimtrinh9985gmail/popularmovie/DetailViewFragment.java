package com.jimtrinh9985gmail.popularmovie;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jimtrinh9985gmail.popularmovie.MyMovieCollection.MyDatabase;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailViewFragment extends Fragment implements View.OnClickListener {

    private MovieDbAPI movieDbAPI;
    private DetailViewAdapter detailViewAdapter;
    private DetailViewReviewAdapter detailViewReviewAdapter;
    protected MyDatabase myDatabase;
    private Serializable dataModelMovie;
    public static final String DETAIL_MOVIE = "DETAIL_MOVIE";
    public static final String DETAILTAG = DetailViewFragment.class.getSimpleName();

    private ImageView detailPoster;
    private TextView detailTitle;
    public int myStatus = 0;
    private TextView detailDate;
    private TextView detailRating;
    private TextView detailSynopsis;
    private TextView detailID;
    public static String movieID;
    public static String detailPosterUrl;
    public static String largeDetailPosterUrl;
    private TextView detailTrailer;
    private TextView detailReview;
    public Button myCollectionButton;
    private Context mContext;

    private ListView listView1;
    private ListView listView2;

    public DetailViewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        movieID = null;
        detailPosterUrl = null;

        Bundle arguments = getArguments();
        if (arguments !=null) {
            Bundle bundle = getArguments();
            dataModelMovie = bundle.getSerializable(DetailViewFragment.DETAIL_MOVIE);
        }

        final View rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);

        //Link Detail Views//
        detailID = (TextView) rootView.findViewById(R.id.detailID);
        detailPoster = (ImageView) rootView.findViewById(R.id.detailPoster);
        detailTitle = (TextView) rootView.findViewById(R.id.detailTitle);
        detailDate = (TextView) rootView.findViewById(R.id.detailDate);
        detailRating = (TextView) rootView.findViewById(R.id.detailRating);
        detailSynopsis = (TextView) rootView.findViewById(R.id.detailSynopsis);

        detailTrailer = (TextView) rootView.findViewById(R.id.detail_trailer_text);
        detailReview = (TextView) rootView.findViewById(R.id.detail_review_text);

        myCollectionButton = (Button) rootView.findViewById(R.id.rating_button);
        myCollectionButton.setOnClickListener(this);

        if (dataModelMovie != null) {
            insertData((DataModelMovies) dataModelMovie);
        } else {
            clearPane();
        }

        //ListView1 for Trailers//
        listView1 = (ListView) rootView.findViewById(R.id.listView1);
        ArrayList<DataModelTrailers> dataModelTrailers = new ArrayList<DataModelTrailers>();
        detailViewAdapter = new DetailViewAdapter(getActivity(), dataModelTrailers);
        listView1.setAdapter(detailViewAdapter);

        getMovieTrailers();

        //ListView2 for Reviews//
        listView2 = (ListView) rootView.findViewById(R.id.listView2);
        ArrayList<DataModelReviews> dataModelReviews = new ArrayList<DataModelReviews>();
        detailViewReviewAdapter = new DetailViewReviewAdapter(getActivity(), dataModelReviews);
        listView2.setAdapter(detailViewReviewAdapter);

        getMovieReviews();

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    //If no movie selected, clear side pane//
    public void clearPane() {
        detailPoster.setVisibility(View.INVISIBLE);
        detailDate.setVisibility(View.INVISIBLE);
        detailRating.setVisibility(View.INVISIBLE);
        detailSynopsis.setVisibility(View.INVISIBLE);
        myCollectionButton.setVisibility(View.INVISIBLE);
        detailReview.setVisibility(View.INVISIBLE);
        detailTrailer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rating_button:
                Toast.makeText(getActivity(), "Saved to My Collection!", Toast.LENGTH_LONG).show();
                addMovie(view);
                break;
        }
    }

    //Add to Movie to Personal Collection//
    public void addMovie(View view) {

        String detailTrailer1 = MovieDbAPI.trailerUrl;
        String detailReview1 = MovieDbAPI.reviewUrl;

        MyDatabase myDatabase = new MyDatabase(mContext);

        String myTitle = detailTitle.getText().toString();
        String myReleaseDate = detailDate.getText().toString();
        String myRating = detailRating.getText().toString();
        String mySynopsis = detailSynopsis.getText().toString();
        String myPoster = detailPosterUrl;
        String myTrailer = detailTrailer1;
        String myReview = detailReview1;

        long id = myDatabase.insertMovie(myTitle, mySynopsis, myReleaseDate, myRating, myPoster,
                myTrailer, myReview, myStatus);
        detailViewAdapter.notifyDataSetChanged();

        saveFile(detailPosterUrl);

        if (id < 0) {
        } else {
        }
    }

    //Save image poster to SD Card//
    public void saveFile(String detailPosterUrl) {
        File direct = new File(Environment.getExternalStorageDirectory() + "/PopularMovie");
        if (!direct.exists()) {
            direct.mkdirs();
        }

        //Split movie title with colon//
        String movieTitle = detailTitle.getText().toString();
        String[] mTitle = movieTitle.split(":");

        DownloadManager mgr = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(largeDetailPosterUrl);
        DownloadManager.Request request = new DownloadManager.Request(downloadUri);

        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI
                | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle("Movies")
                .setDescription("Popular Movies Download")
                .setDestinationInExternalPublicDir("/PopularMovie", mTitle[0] + ".jpg");
        mgr.enqueue(request);
    }

    //Insert data for DetailView//
    public void insertData(DataModelMovies dataModelMovie) {

        detailID.setText(dataModelMovie.getMovieID());
        movieID = detailID.getText().toString();

        detailPosterUrl = dataModelMovie.getPosterSmallThumbnail().toString();
        largeDetailPosterUrl = dataModelMovie.getPosterLargeThumbnail().toString();

        detailTitle.setText(dataModelMovie.getOriginalTitle());
        detailDate.setText("Release: " + dataModelMovie.getReleaseDate());
        detailRating.setText("Rating: " + dataModelMovie.getAverageVote() + "/10");
        detailSynopsis.setText(dataModelMovie.getOverview());
        Picasso.with(getActivity())
                .load(dataModelMovie.getPosterSmallThumbnail())
                .error(R.drawable.no_data_image)
                .into(detailPoster);
    }

    private void getMovieTrailers() {
        movieDbAPI = new MovieDbAPI();
        movieDbAPI.getMovieTrailers(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray trailerData = null;

                try {
                    //Get JSON Data//
                    trailerData = response.getJSONArray("results");

                    //Parse JSON Data//
                    ArrayList<DataModelTrailers> dataModelTrailers = DataModelTrailers.fromJson(trailerData);

                    //Load Data into Adapter//
                    detailViewAdapter.addAll(dataModelTrailers);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void getMovieReviews() {
        movieDbAPI = new MovieDbAPI();
        movieDbAPI.getMovieReviews(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray reviewData = null;

                try {
                    //Get JSON Data//
                    reviewData = response.getJSONArray("results");

                    //Parse JSON Data//
                    ArrayList<DataModelReviews> dataModelReviews = DataModelReviews.fromJson(reviewData);

                    //Load Data into Adapter//
                    detailViewReviewAdapter.addAll(dataModelReviews);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
