package com.jimtrinh9985gmail.popularmovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jimtrinh9985gmail.popularmovie.MyMovieCollection.MyCollection;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.Callback {

    private boolean mTwoPane;
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    public static final String DETAIL_MOVIE = "result";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (findViewById(R.id.detail_view_container) !=null ) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_view_container, new DetailViewFragment(),
                                DetailViewFragment.DETAILTAG).commit();
            }
        } else {
            mTwoPane = false;
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_main, new MainActivityFragment()).commit();
        }
    }

    @Override
    public void onItemSelected(DataModelMovies b) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(DetailViewFragment.DETAIL_MOVIE, b);

            DetailViewFragment detailViewFragment = new DetailViewFragment();
            detailViewFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_view_container, detailViewFragment,
                            DETAILFRAGMENT_TAG).commit();
        } else {
            Intent intent = new Intent(this, DetailView.class)
                    .putExtra(DetailViewFragment.DETAIL_MOVIE, b);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_most_popular:
                Intent intent = new Intent(this, MainActivityFragment.class);
                startActivity(intent);
                break;
            case R.id.menu_highest_rated:
                Intent intent1 = new Intent(this, HighestRating.class);
                startActivity(intent1);
                break;
            case R.id.menu_my_movies:
                Intent intent2 = new Intent(this, MyCollection.class);
                startActivity(intent2);
                return false;
            default:
                break;
        }
        return true;
    }
}
