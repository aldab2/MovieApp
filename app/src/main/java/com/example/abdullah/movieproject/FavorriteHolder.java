package com.example.abdullah.movieproject;

import android.os.AsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingdom on 4/12/2019.
 */

public class FavorriteHolder implements Serializable {
  public static String searchtype;

    public static List<Movie> favorites = new ArrayList<>();

    public static List<Movie> getFavorites() {
        return favorites;
    }

    public static void setFavorites(List<Movie> favorites) {
        FavorriteHolder.favorites = favorites;
    }


}
