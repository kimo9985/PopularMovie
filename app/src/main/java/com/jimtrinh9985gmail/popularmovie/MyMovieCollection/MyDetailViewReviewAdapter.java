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

import com.jimtrinh9985gmail.popularmovie.DataModelReviews;
import com.jimtrinh9985gmail.popularmovie.R;

import java.util.ArrayList;

/**
 * Created by Kimo on 12/25/2015.
 */
public class MyDetailViewReviewAdapter extends ArrayAdapter<DataModelReviews> {

    public MyDetailViewReviewAdapter(Context context, ArrayList<DataModelReviews> dataModelReviews) {
        super(context, 0, dataModelReviews);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //Data item for this position//
        final DataModelReviews dataModelReviews = getItem(position);

        //Check if view exists, otherwise inflate view//
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.data_model_reviews, null);
        }

        //Lookup view//
        TextView author = (TextView) convertView.findViewById(R.id.author);
        ImageButton readReview = (ImageButton) convertView.findViewById(R.id.readReview);

        //Populate view//
        author.setText(dataModelReviews.getAuthor());
        readReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataModelReviews.getUrl()));
                view.getContext().startActivity(intent);
            }
        });

        return convertView;
    }

}