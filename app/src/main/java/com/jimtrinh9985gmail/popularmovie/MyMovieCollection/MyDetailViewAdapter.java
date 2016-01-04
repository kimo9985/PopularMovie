package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jimtrinh9985gmail.popularmovie.DataModelTrailers;
import com.jimtrinh9985gmail.popularmovie.R;

import java.util.ArrayList;

/**
 * Created by Kimo on 12/24/2015.
 */
public class MyDetailViewAdapter extends ArrayAdapter<DataModelTrailers> {

    public MyDetailViewAdapter(Context context, ArrayList<DataModelTrailers> dataModelTrailers) {
        super(context, 0, dataModelTrailers);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Data item for this position//
        final DataModelTrailers dataModelTrailers = getItem(position);

        //Check if view exists, otherwise inflate view//
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.data_model_trailer, null);
        }

        //Lookup view//
        TextView reviewTrailer = (TextView) convertView.findViewById(R.id.trailers);
        TextView resolution = (TextView) convertView.findViewById(R.id.resolution);
        ImageButton playTrailer = (ImageButton) convertView.findViewById(R.id.playTrailer);

        //Populate view//
        reviewTrailer.setText(dataModelTrailers.getTrailers());
        resolution.setText(dataModelTrailers.getTrailerRes());
        playTrailer.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="
                        + dataModelTrailers.getYoutubeKey()));
                view.getContext().startActivity(intent);
            }
        });
        return convertView;
    }
}