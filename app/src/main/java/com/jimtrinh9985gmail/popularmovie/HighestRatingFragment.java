package com.jimtrinh9985gmail.popularmovie;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Kimo on 12/18/2015.
 */
public class HighestRatingFragment extends Fragment {

    private GridView gridView;
    private MovieDbAPI movieDbAPI;
    private MovieAdapter movieAdapter;
    private DataModelMovies dataModelMovie;
    public static final String DETAIL_MOVIE1 = "DETAIL_MOVIE";

    private static final String RESTORE_GRIDVIEW = "restoreView";
    Serializable restoreView;

    public HighestRatingFragment() {
    }

    public interface Callback {
        void onItemSelected(DataModelMovies dataBundle2);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreView = savedInstanceState.getSerializable(RESTORE_GRIDVIEW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        gridView = (GridView) rootView.findViewById(R.id.gridView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRetainInstance(true);

        ArrayList<DataModelMovies> dataModelMovie = new ArrayList<DataModelMovies>();
        movieAdapter = new MovieAdapter(getActivity(), dataModelMovie);
        gridView.setAdapter(movieAdapter);

        //Fetch Highest-Rated Movies//
        fetchHighestRatedMovies();

        clickListener();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(RESTORE_GRIDVIEW, restoreView);
        super.onSaveInstanceState(outState);
    }

    private void fetchHighestRatedMovies() {
        movieDbAPI = new MovieDbAPI();
        movieDbAPI.getHighestRatedMovies(new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray data = null;

                try {
                    //Get JSON Data//
                    data = response.getJSONArray("results");

                    //Parse JSON Data//
                    ArrayList<DataModelMovies> dataModelMovies = DataModelMovies.fromJson(data);

                    //Load Data into Adapter//
                    movieAdapter.addAll(dataModelMovies);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void clickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dataModelMovie = movieAdapter.getItem(position);
                Bundle dataBundle = new Bundle();
                dataBundle.putSerializable(DETAIL_MOVIE1, dataModelMovie);
                DetailViewFragment detailViewFragment = new DetailViewFragment();
                detailViewFragment.setArguments(dataBundle);
                ((Callback)getActivity()).onItemSelected(dataModelMovie);
            }
        });
    }

}