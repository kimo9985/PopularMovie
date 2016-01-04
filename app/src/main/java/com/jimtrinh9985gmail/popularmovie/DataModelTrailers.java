package com.jimtrinh9985gmail.popularmovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kimo on 12/1/2015.
 */
public class DataModelTrailers implements Serializable {

    private String trailers;
    private String youtubeKey;
    private String trailerRes;

    public static DataModelTrailers fromJson(JSONObject jsonObject) {
        DataModelTrailers dataModelTrailer = new DataModelTrailers();

        try {
            //Deserialize JSON//
            dataModelTrailer.trailers = jsonObject.getString("name");
            dataModelTrailer.youtubeKey = jsonObject.getString("key");
            dataModelTrailer.trailerRes = jsonObject.getString("size");

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return dataModelTrailer;
    }

    //Decode JSONArray into data model//
    public static ArrayList<DataModelTrailers> fromJson(JSONArray jsonArray) {
        ArrayList<DataModelTrailers> dataModelTrailers = new ArrayList<DataModelTrailers>(jsonArray.length());

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject object = null;
            try {
                object = jsonArray.getJSONObject(i);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }

            DataModelTrailers dataModelTrailer = DataModelTrailers.fromJson(object);
            if (dataModelTrailer !=null) {
                dataModelTrailers.add(dataModelTrailer);
            }
        }
        return dataModelTrailers;
    }

    public String getTrailers() {
        return trailers;
    }

    public void setTrailers(String trailers) {
        this.trailers = trailers;
    }

    public String getYoutubeKey() {
        return youtubeKey;
    }

    public void setYoutubeKey(String youtubeKey) {
        this.youtubeKey = youtubeKey;
    }

    public String getTrailerRes() {
        return trailerRes;
    }

    public void setTrailerRes(String trailerRes) {
        this.trailerRes = trailerRes;
    }
}