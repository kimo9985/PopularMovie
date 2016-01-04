package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import com.jimtrinh9985gmail.popularmovie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 12/20/2015.
 */
public class MyMovieAdapter extends ArrayAdapter<MyMovieDataModel> {

    Context context;
    int layoutResourceId;
    List<MyMovieDataModel> myMovieDataModel = new ArrayList<MyMovieDataModel>();

    public MyMovieAdapter(Context context, int layoutResourceId, List<MyMovieDataModel> object) {
        super(context, layoutResourceId, object);
        this.layoutResourceId = layoutResourceId;
        this.myMovieDataModel = object;
        this.context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.my_collection_row, parent, false);

        CheckBox checkBox = (CheckBox) row.findViewById(R.id.deleteCheckBox);
        MyMovieDataModel current = myMovieDataModel.get(position);
        checkBox.setText(current.getMyTitle());
        checkBox.setChecked(current.getMyStatus() == 1 ? true : false);
        return row;
    }
}