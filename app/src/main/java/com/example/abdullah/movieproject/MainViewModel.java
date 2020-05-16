package com.example.abdullah.movieproject;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import java.util.List;

/**
 * Created by abdullah on 14/04/19.
 */

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Movie>> movies;
    final String TAG = getClass().getSimpleName();
    public MainViewModel(Application application){
        super(application);
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movies = AppDatabase.getInstance(application).movieDao().loadAllFavorites();
    }

    public LiveData<List<Movie>> getMovies(){
        return movies;
    }

}
