package com.jimtrinh9985gmail.popularmovie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_view);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(DetailViewFragment.DETAIL_MOVIE,getIntent()
                    .getSerializableExtra(DetailViewFragment.DETAIL_MOVIE));

            DetailViewFragment detailViewFragment = new DetailViewFragment();
            detailViewFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_view_container, detailViewFragment)
                    .commit();
        }
    }
}
