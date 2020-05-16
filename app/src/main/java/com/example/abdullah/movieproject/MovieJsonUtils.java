package com.example.abdullah.movieproject;

import android.util.Log;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by abdullah on 18/03/19.
 */

public class MovieJsonUtils {
    static String TAG = "MovieJsonUtils";

    public static ArrayList<Movie> movieJsonParse(String json){

        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");
            ArrayList<Movie> movies = new ArrayList<>();
            for(int i=0; i<results.length();i++){
                JSONObject movie = results.getJSONObject(i);
                int id = movie.getInt("id");
                String originalTitle = movie.getString("original_title");
                //ImageView image
                String imagePath = movie.getString("poster_path");
                String overview = movie.getString("overview");
                Double rating =  movie.getDouble("vote_average");
                String relaseDate = movie.getString("release_date");
                Boolean isAdult = movie.getBoolean("adult");
                String originalLanguage = movie.getString("original_language");
              /*  Log.d(TAG, "movieJsonParse: "+originalTitle+"\n"+imagePath+"\n"+
                        overview+"\n"+rating+"\n"+relaseDate+"\n"+isAdult+"\n"+originalLanguage);*/

                Movie parsedMovie = new Movie ( id,originalTitle,  imagePath,  overview,  rating,  relaseDate,  isAdult,  originalLanguage);
                movies.add(parsedMovie);
            }
            return movies;
        }
        catch(JSONException jse){
            jse.printStackTrace();

        }


    return null;

    }
    public static ArrayList<String> trailerJsonParse(String json) {
        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");
            ArrayList<String> trailers = new ArrayList<>();
            for(int i=0;i<results.length();i++){
                String key;
                JSONObject result = results.getJSONObject(i);
                key = result.getString("key");


                trailers.add(key);

            }
            return trailers;
        }
        catch (JSONException jsoe){
            jsoe.printStackTrace();
        }

        return null;
    }
    public static ArrayList<Review> reviewJsonParse(String json) {
        try {
            JSONObject data = new JSONObject(json);
            JSONArray results = data.getJSONArray("results");
            ArrayList<Review> reviews = new ArrayList<>();
            for(int i=0;i<results.length();i++){
                String author,reviewContent;
                JSONObject review = results.getJSONObject(i);
                author = review.getString("author");
                reviewContent = review.getString("content");
                Review parsedReview = new Review(author,reviewContent);
                reviews.add(parsedReview);

            }
            return reviews;
        }
        catch (JSONException jsoe){
            jsoe.printStackTrace();
        }

        return null;
    }



}
