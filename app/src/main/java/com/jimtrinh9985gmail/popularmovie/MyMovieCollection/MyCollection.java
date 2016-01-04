package com.jimtrinh9985gmail.popularmovie.MyMovieCollection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.jimtrinh9985gmail.popularmovie.HighestRating;
import com.jimtrinh9985gmail.popularmovie.MainActivity;
import com.jimtrinh9985gmail.popularmovie.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kimo on 12/20/2015.
 */
public class MyCollection extends AppCompatActivity {

    private ListView listView;
    List<MyMovieDataModel> movieList;
    TextView textView;
    MyDatabase database;
    MyMovieAdapter myMovieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = new MyDatabase(this);
        movieList = database.getAllItems();
        myMovieAdapter = new MyMovieAdapter(this, R.layout.my_collection_row, movieList);
        listView = (ListView) findViewById(R.id.myCollection_listView);
        listView.setAdapter(myMovieAdapter);

        clickToDetail();
    }

    public void clickToDetail() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(R.id.myCollectionTitle);
                String myMovie = textView.getText().toString();
                Intent intent = new Intent(MyCollection.this, MyDetailView.class);
                intent.putExtra("movie", myMovie);
                startActivity(intent);
            }
        });
    }

    private class MyMovieAdapter extends ArrayAdapter<MyMovieDataModel> {
        Context context;
        List<MyMovieDataModel> myMovieDataModel = new ArrayList<MyMovieDataModel>();
        int layoutResourceId;

        public MyMovieAdapter(Context context, int layoutResourceId, List<MyMovieDataModel> object) {
            super(context, layoutResourceId, object);
            this.layoutResourceId = layoutResourceId;
            this.myMovieDataModel = object;
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            CheckBox checkBox = null;
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.my_collection_row, parent, false);

                textView = (TextView) convertView.findViewById(R.id.myCollectionTitle);
                textView.setVisibility(View.GONE);
                checkBox = (CheckBox) convertView.findViewById(R.id.deleteCheckBox);
                convertView.setTag(checkBox);
                checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        CheckBox check = (CheckBox) view;
                        MyMovieDataModel updateData = (MyMovieDataModel) check.getTag();
                        updateData.setMyStatus(check.isChecked() == true ? 1 : 0);
                        database.updateList(updateData);
                    }
                });
            } else {
                checkBox = (CheckBox) convertView.getTag();
            }
            MyMovieDataModel current = myMovieDataModel.get(position);
            textView.setText(current.getMyTitle());
            checkBox.setText(current.getMyTitle());
            checkBox.setChecked(current.getMyStatus() == 1 ? true : false);
            checkBox.setTag(current);
            return convertView;
        }
    }

    public void removeItem (View view) {
        database.deleteItem(view);
        myMovieAdapter.notifyDataSetChanged();
        recreate();
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
                Intent intent = new Intent(this, MainActivity.class);
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
