package com.example.abdullah.movieproject;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kingdom on 4/12/2019.
 */

@Dao
public interface MovieDao {

   @Query("Select * From Movie")
   LiveData<List<Movie>> loadAllFavorites();

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addToFavorites(Movie movieEntry);

   @Delete
    void removeFavorite(Movie movieEntry);


}
