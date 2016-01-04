package com.jimtrinh9985gmail.popularmovie;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kimo on 12/1/2015.
 */
public class MovieAdapter extends ArrayAdapter<DataModelMovies> {

    public MovieAdapter(Context context, ArrayList<DataModelMovies> dataModelMovies) {
        super(context, 0, dataModelMovies);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Data item for this position//
        DataModelMovies dataModelMovie = getItem(position);

        //Check if view exists, otherwise inflate view//
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.data_model, null);
        }

        //Lookup View//
        ImageView posterImage = (ImageView) convertView.findViewById(R.id.gridPoster);

        //Populate View//
        Picasso.with(getContext())
                .load(dataModelMovie.getPosterSmallThumbnail())
                .into(posterImage);

        return convertView;
    }
}
