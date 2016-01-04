package com.jimtrinh9985gmail.popularmovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kimo on 12/1/2015.
 */
public class DataModelReviews implements Serializable {

    private String author;
    private String url;

    public static DataModelReviews fromJson(JSONObject jsonObject) {
        DataModelReviews dataModelReview = new DataModelReviews();

        try {
            //Deserialize JSON//
            dataModelReview.author = jsonObject.getString("author");
            dataModelReview.url = jsonObject.getString("url");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dataModelReview;
    }

    //Decode JSONArray into data model//
    public static ArrayList<DataModelReviews> fromJson(JSONArray jsonArray) {
        ArrayList<DataModelReviews> dataModelReviews = new ArrayList<DataModelReviews>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            DataModelReviews dataModelReview = DataModelReviews.fromJson(object);
            if (dataModelReview !=null) {
                dataModelReviews.add(dataModelReview);
            }
        }
        return dataModelReviews;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}