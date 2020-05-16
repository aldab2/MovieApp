package com.example.abdullah.movieproject;

import android.app.LoaderManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity /*implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> */{
    private MovieAdapter mAdapter;
    private RecyclerView mNumbersList;
    String TAG = getClass().getSimpleName();
    public  String searchType="";
    public String newSearchType = "";
    Intent settingsIntent;
    public final static String KEY_SEARCH_TYPE="searchType";



/*    private final String SEARCH_POPULAR = "popular";
    private final String SEARCH_TOP_RATED = "top_rated";
    private final String SEARCH_NOW_PLAYING = "now_playing";
    private final String SEARCH_UPCOMING = "upcoming";*/
    /*private final String KEY = "a13db04bc8683latest668b9727735405b164d";
    private final String ENG_LANG = "en-US";*/
    private String pageNumber = "1";
    URL url;
    ProgressBar mProgressBar;
    ArrayList<Movie> moviesSearchResults;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState!=null)
        searchType = savedInstanceState.getString(KEY_SEARCH_TYPE);

        Log.d("LLLLLLLLLLLLLL", "onCreate: searchtype: "+searchType+" new search type"+newSearchType);



        mNumbersList = (RecyclerView) findViewById(R.id.rv_list_movie);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_loading);

        int orientation = getResources().getConfiguration().orientation;
        GridLayoutManager gridLayoutManager;
        int numOfCols = calculateNoOfColumns(this);
       // if(orientation == Configuration.ORIENTATION_PORTRAIT)
         gridLayoutManager = new GridLayoutManager(this,numOfCols);
       // else gridLayoutManager = new GridLayoutManager(this,3);


        mNumbersList.setLayoutManager(gridLayoutManager);

        mNumbersList.setHasFixedSize(true);
        Boolean isNewQuery = setupSharedPreferences(); //Tried using it -- will not work
        Log.d("LLLLLLLLLLLLLLL", "onCreate: IsNewQuery? "+isNewQuery );

            retrieveMovies(isNewQuery);


       // retrieveMovies(isNewQuery);
        setUpViewModel();





    }
    public static int calculateNoOfColumns(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 200;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        if(noOfColumns < 2)
            noOfColumns = 2;
        return noOfColumns;
    }

    private void retrieveMovies(Boolean isNewQuery) {
        try {
            //url = new URL("https://api.themoviedb.org/3/movie/upcoming?api_key=a13db04bc8683668b9727735405b164d&language=en-US&page=1");
            if(!isNetworkConnected()){

                Toast.makeText(this,"No Internet Connection. Showing Favorites",Toast.LENGTH_SHORT).show();
            }

            if(!searchType.equals(getString(R.string.btn_favorite)) && isNetworkConnected()) {
                Log.e("LLLLLLLLLLLL"," Getting New Movies isNewQuery is "+isNewQuery);
                NetworkUtils.setBaseURL(searchType);
                url = NetworkUtils.buildMoviesURL();
                new MovieSearchTask().execute(url);

            }
            else{
                //new FavorateLoadTask().execute();

               // setUpViewModel();
                Log.e("LLLLLLLLLLLL"," Viewing Old Favorites");
                mAdapter = new MovieAdapter((ArrayList<Movie>)FavorriteHolder.getFavorites());
               // TODO Change the prev step to mAdapter.setMovies() if it worked.
                mNumbersList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
            }


        }
        catch (Exception  mue){
            mue.printStackTrace();
        }
    }

    @Override
    protected void onStop() {

       // Log.d("LLLLLLLLLLLLL", "onStop: " );
        super.onStop();

    }

    @Override
    protected void onPause() {
        //Log.d("LLLLLLLLLLLLLLLLL", "onPause: " );

        super.onPause();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("LLLLLLLLLLLLLLLL", "onRestoreInstanceState "+searchType );
        savedInstanceState.putString(KEY_SEARCH_TYPE,searchType);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("LLLLLLLLLLLLLLLL", "onSaveInstanceState "+searchType );
        outState.putString(KEY_SEARCH_TYPE,searchType);

        super.onSaveInstanceState(outState);
    }

    /*
            Return true is the search type has changed to re excecute the asnyc task
             */
    private boolean setupSharedPreferences() {
        // Get all of the values from shared preferences to set it up
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
         newSearchType = sharedPreferences.getString("list_pref_sort_by","popular");
        Log.d("LLLLLLLLLLLLLL", "setupSharedPreferences: "+newSearchType );
        if (newSearchType !=null)
            if(newSearchType != searchType) {
                searchType = newSearchType;
                return true;
            }
        Log.d(TAG, "setupSharedPreferences: SearchType is "+searchType );

        // Register the listener
        //  sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.main_menu,menu);
        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(!isNetworkConnected()){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();

        }

            String prevSearchType = searchType;
           /* if (item.getItemId() == R.id.menu_item_popular) {
                searchType = SEARCH_POPULAR;
                Toast.makeText(this, "Popolar", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.menu_item_rating) {
                searchType = SEARCH_TOP_RATED;
                Toast.makeText(this, "Rating", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.menu_item_now_playing) {
                searchType = SEARCH_NOW_PLAYING;
                Toast.makeText(this, "Now Playing", Toast.LENGTH_SHORT).show();
            }
            if (item.getItemId() == R.id.menu_item_upcoming) {
                searchType = SEARCH_UPCOMING;
                Toast.makeText(this, "Upcoming", Toast.LENGTH_SHORT).show();
            }*/
            if(item.getItemId() == R.id.menu_settings){
                Log.d(TAG, "onOptionsItemSelected: XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
                 settingsIntent = new Intent(this,SettingsActivity.class);
                startActivity(settingsIntent);
            }
            Log.d(TAG, "onOptionsItemSelected: prevSearchType: " + prevSearchType + "  currentSearchTag: " + searchType);
//            if (!prevSearchType.equalsIgnoreCase(searchType)) {
//                if(!searchType.equals(getString(R.string.btn_favorite))){
//                NetworkUtils.setBaseURL(searchType);
//                url = NetworkUtils.buildMoviesURL();
//                new MovieSearchTask().execute(url);}
//                else{
//                    Log.d(TAG, "onOptionsItemSelected: SearchType is "+searchType );
//                    //new FavorateLoadTask().execute();
//                    setUpViewModel();
//                }
//            }




        return super.onOptionsItemSelected(item);
    }

    public void setUpViewModel(){
  //      mProgressBar.setVisibility(ProgressBar.VISIBLE);
        final LiveData<List<Movie>> movies= AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllFavorites();

//        mProgressBar.setVisibility(ProgressBar.VISIBLE);
//        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies==null){
                    Log.d("LLLLLLLLLLLLL", "onChanged movies is NULL" );
                    //movies = new ArrayList<Movie>();
                    FavorriteHolder.setFavorites(movies);
                }

                else{
                    Log.e("LLLLLLLLLLLL","onChanged Setting Favorites");

                    mAdapter = new MovieAdapter((ArrayList<Movie>)movies);
                    if(searchType.equals(getString(R.string.btn_favorite))) {
                        mNumbersList.setAdapter(mAdapter);
                        mAdapter.notifyDataSetChanged();
                    }
                    FavorriteHolder.setFavorites(movies);


                }
            }
        });
      /*  movies.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if(movies==null){
                    Log.d(TAG, "onPostExecute: Search Result Is NULL" );
                    //movies = new ArrayList<Movie>();
                    FavorriteHolder.setFavorites(movies);
                }

                else{
                    Log.d(TAG, "onPostExecute: "+movies );
                    mAdapter = new MovieAdapter((ArrayList<Movie>)movies);
                    mNumbersList.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    FavorriteHolder.setFavorites(movies);


                }
            }
        });*/
        FavorriteHolder.setFavorites(movies.getValue());

    }




    class FavorateLoadTask extends AsyncTask<Void,Void, LiveData<List<Movie>>>{
        @Override
        protected void onPreExecute() {

            mProgressBar.setVisibility(ProgressBar.VISIBLE);
           final LiveData<List<Movie>> movies= AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllFavorites();

            mProgressBar.setVisibility(ProgressBar.VISIBLE);
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            if(movies==null){
                Log.d(TAG, "onPostExecute: Search Result Is NULL" );
                //movies = new ArrayList<Movie>();
                FavorriteHolder.setFavorites(movies.getValue());
            }

            else{
                Log.d(TAG, "onPostExecute: "+movies );
                mAdapter = new MovieAdapter((ArrayList<Movie>)movies.getValue());
                mNumbersList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                FavorriteHolder.setFavorites(movies.getValue());


            }

            super.onPreExecute();
        }

        @Override
        protected  LiveData<List<Movie>> doInBackground(Void... voids) {
            Log.e("LLLLLLLLLLLLLLLLLLL", "Updating Favorites" );
            return AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllFavorites();

        }

        @Override
        protected void onPostExecute( LiveData<List<Movie>> movies) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            if(movies==null){
                Log.d(TAG, "onPostExecute: Search Result Is NULL" );
                //movies = new ArrayList<Movie>();
                FavorriteHolder.setFavorites(movies.getValue());
            }

            else{
                Log.d(TAG, "onPostExecute: "+movies );
                mAdapter = new MovieAdapter((ArrayList<Movie>)movies.getValue());
                mNumbersList.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                FavorriteHolder.setFavorites(movies.getValue());


            }


        }

    }



    @Override
    protected void onStart() {
        super.onStart();
        Boolean isNewQuery = setupSharedPreferences();
        if(isNewQuery) {
            retrieveMovies(isNewQuery);
        }

        Log.d("LLLLLLLLLLLLLLLLLLL", "On Start SearchType "+searchType+"is New:"+isNewQuery );
    }

    class MovieSearchTask extends AsyncTask<URL,Void,ArrayList<Movie>> {
        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(ProgressBar.VISIBLE);

            super.onPreExecute();
        }


        @Override
        protected ArrayList<Movie> doInBackground(URL... urls) {
            URL url = urls[0];
            moviesSearchResults = null;
            try {
                String jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
                moviesSearchResults = MovieJsonUtils.movieJsonParse(jsonResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesSearchResults;

        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            mProgressBar.setVisibility(ProgressBar.INVISIBLE);
            if(movies==null){
                Log.d(TAG, "onPostExecute: Search Result Is NULL" );
            }

            else{
                mAdapter = new MovieAdapter(movies);
                mNumbersList.setAdapter(mAdapter);


            }


        }
    }


    public void resetPageNumber(){
        pageNumber = "1";
    }
    public  void incrementPageNumber(){
        int number = Integer.parseInt(pageNumber);
        number++;
        pageNumber=""+number;
    }

    /*

    This method has been taken from StackOverflow
     */
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


   /* class MovieLoader extends AsyncTaskLoader<ArrayList<Movie>>{
        URL urls[];
        public MovieLoader(Context context){
            super(context);

        }
       public void getURL(){
           NetworkUtils.setBaseURL(searchType);
           url = NetworkUtils.buildMoviesURL();
           this.urls =url;

       }

        @Override
        public ArrayList<Movie> loadInBackground() {
            URL url = urls[0];
            moviesSearchResults = null;
            try {
                String jsonResult = NetworkUtils.getResponseFromHttpUrl(url);
                moviesSearchResults = MovieJsonUtils.movieJsonParse(jsonResult);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesSearchResults;
        }




    }


    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);

        MovieLoader movieLoader = new MovieLoader(this);
        movieLoader.getURL();
        return ;
    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        if(movies==null){
            Log.d(TAG, "onPostExecute: Search Result Is NULL" );
        }

        else{
            mAdapter = new MovieAdapter(movies);
            mNumbersList.setAdapter(mAdapter);


        }

    }*/
}
