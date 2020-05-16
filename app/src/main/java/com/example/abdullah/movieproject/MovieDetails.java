package com.example.abdullah.movieproject;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MovieDetails extends AppCompatActivity {

    TextView mAdultTextVew;
    TextView mRatingTv;
    TextView mOverviewTv;
    TextView mRelaseDateTv;
    ImageView mImageIv;
    ImageView mbutButton;
    Button mReviewButton;
    TrailerAdapter mAdapter;
    ReviewAdapter mReviewAdaptper;
    RecyclerView mRecyclerView;
    List<Movie> favorites;
    ArrayList<String> trailers = new ArrayList<>();
     Movie movieEntry;
    boolean isFavorate;
    boolean action = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        action = false;
        mRecyclerView = findViewById(R.id.rv_trailer);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrailerAdapter(trailers);
        mRecyclerView.setAdapter(mAdapter);
        mAdultTextVew = findViewById(R.id.tv_adult);
        mRatingTv = findViewById(R.id.tv_rating_val);
        mOverviewTv = findViewById(R.id.tv_overview);
        mRelaseDateTv = findViewById(R.id.tv_relase_date);
        mImageIv = findViewById(R.id.image_iv);
        mbutButton = findViewById(R.id.btn_favorite);
        mReviewButton = findViewById(R.id.btn_reviews);

        mReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReviewActivity();
            }
        });






        Intent callingIntent = getIntent();
        String extra;
        if(callingIntent.hasExtra("isAdult")){
            Boolean booleanExtra = callingIntent.getBooleanExtra("isAdult",true);
            if (booleanExtra==true)
            mAdultTextVew.setText("For Adults");
            else
                mAdultTextVew.setText("All Ages");

        }else{
            mAdultTextVew.setText("No Information Available");

        }
        if(callingIntent.hasExtra("rating")){
            double doubleExtra = callingIntent.getDoubleExtra("rating",0);
            mRatingTv.setText(""+doubleExtra);

        }else{
            mRatingTv.setText("---");

        }
        if(callingIntent.hasExtra("overview")){
            extra = callingIntent.getStringExtra("overview");
            mOverviewTv.setText(extra);
        }else{
            mOverviewTv.setText("No Information Available");

        }
        if(callingIntent.hasExtra("relaseDate")){
            extra = callingIntent.getStringExtra("relaseDate");
            mRelaseDateTv.setText(extra);
        }else{
            mRelaseDateTv.setText("No Information Available");

        }
        if(callingIntent.hasExtra("tittle")){
            extra = callingIntent.getStringExtra("tittle");
            setTitle(extra);
        }else{
            setTitle("No Tittle Available");

        }
        if(callingIntent.hasExtra("image_full_url")){
            extra = callingIntent.getStringExtra("image_full_url");
            extra = extra.replace("w185","w342");
            Log.d("XXXXXXXXXXx", "onCreate: "+extra );
            Picasso.get().load(extra).fit().centerInside().into(mImageIv);


        }else{


        }

        int id;
        double rating;
        String overview,relaseDate,tittle,imageFullURL;
                Boolean isAdult;
        rating =callingIntent.getDoubleExtra("rating",0);
        overview= callingIntent.getStringExtra("overview");
        relaseDate=  callingIntent.getStringExtra("relaseDate");
        tittle= callingIntent.getStringExtra("tittle");
        imageFullURL= callingIntent.getStringExtra("image_full_url");
        isAdult=  callingIntent.getBooleanExtra("isAdult",false);
       id= callingIntent.getIntExtra("id",-1);

        movieEntry = new Movie(id,tittle,imageFullURL,overview,rating,relaseDate,isAdult,"English");
         movieEntry.imageURL = imageFullURL;
         if(isNetworkConnected()) {
             new TrailerMoviesTask().execute(movieEntry);
             new ReviewMoviesTask().execute(movieEntry);
         }
         else {
             Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
         }
        Log.e("FFFFFFFFFF", "onCreate: Favorite is Beeing Set" );
        setFavButtonText(movieEntry.getId());
        mbutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    action = true;
                   new FavoriteMovieTask().execute(movieEntry);
                Log.d("YYYYYY", "onClick: DOING FAVORITE MOVIE TASK" );
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public void setFavButtonText(int id){
        favorites = FavorriteHolder.getFavorites();
        isFavorate = false;
        if(favorites == null)
            isFavorate= false;
                    else
        for(Movie movie : favorites){
            if(movie.id == id){
                isFavorate =true;
            }
        }
        if (isFavorate){

            mbutButton.setImageResource(android.R.drawable.btn_star_big_on);
            mbutButton.setTag(getString(R.string.btn_unfavorite));
        }
        else {

            mbutButton.setImageResource(android.R.drawable.btn_star_big_off);
            mbutButton.setTag(getString(R.string.btn_favorite));      }
    }

    public void setFavButtonText(boolean isFavorate){

        if (isFavorate){

            mbutButton.setImageResource(android.R.drawable.btn_star_big_on);
            mbutButton.setTag(getString(R.string.btn_unfavorite));
        }
        else {

            mbutButton.setImageResource(android.R.drawable.btn_star_big_off);
            mbutButton.setTag(getString(R.string.btn_favorite));      }
    }


    @Override
    protected void onResume() {
        super.onResume();
        new ReviewMoviesTask().execute(movieEntry);
        new TrailerMoviesTask().execute(movieEntry);
        action = false;
    }
    public void openReviewActivity(){
        Intent intent = new Intent(this,ReviewsActivity.class);
        startActivity(intent);
    }
    class ReviewMoviesTask extends  AsyncTask<Movie,Void,ArrayList<Review>>{


        @Override
        protected ArrayList<Review> doInBackground(Movie... movies) {
            int id = movies[0].getId();
            NetworkUtils.setReviewsBaseURL(""+id);
           URL url =  NetworkUtils.buildReviewURL();
           String jsonReviewRespons="";
           ArrayList<Review> reviews = new ArrayList<>();

            try {
                jsonReviewRespons= NetworkUtils.getResponseFromHttpUrl(url);
                reviews = MovieJsonUtils.reviewJsonParse(jsonReviewRespons);
                Log.d("XXXXXXXX", "doInBackground: "+jsonReviewRespons );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return reviews;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> reviews) {
            super.onPostExecute(reviews);
            ReviewContainer.setReview(reviews);
            mAdapter.notifyDataSetChanged();

        }
    }
    class TrailerMoviesTask extends  AsyncTask<Movie,Void,ArrayList<String>>{


        @Override
        protected ArrayList<String> doInBackground(Movie... movies) {
            int id = movies[0].getId();
            NetworkUtils.setTrailersBaseURL(""+id);
            URL url =  NetworkUtils.buildReviewURL();
            String jsonReviewRespons="";
            ArrayList<String> trailers = new ArrayList<>();

            try {
                jsonReviewRespons= NetworkUtils.getResponseFromHttpUrl(url);
                trailers = MovieJsonUtils.trailerJsonParse(jsonReviewRespons);
                Log.d("XXXXXXXX", "doInBackground: "+jsonReviewRespons );
            } catch (IOException e) {
                e.printStackTrace();
            }

            return trailers;
        }

        @Override
        protected void onPostExecute(ArrayList<String> trailers) {
            super.onPostExecute(trailers);
            //TODO Set on Post
            mAdapter.setmReviews(trailers);
            
            mAdapter.notifyDataSetChanged();


        }
    }


    class FavoriteMovieTask extends AsyncTask<Movie,Void,Boolean>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mbutButton.setVisibility(View.INVISIBLE);
        }

        @Override
        protected Boolean doInBackground(Movie... movies) {
            Log.d("YYYYYYYY", "doInBackground:  what is the star?" );
            if(mbutButton.getTag().toString().equals(getString(R.string.btn_favorite))){

                Log.d("YYYYYYYY", "doInBackground:  yellow star is on" );


                AppDatabase.getInstance(getApplicationContext()).movieDao().addToFavorites(movies[0]);
                FavorriteHolder.getFavorites().add(movies[0]);
                return true;
            }
            else {
                Log.d("YYYYYYYY", "doInBackground:  normal star is on" );
                AppDatabase.getInstance(getApplicationContext()).movieDao().removeFavorite(movies[0]);
                FavorriteHolder.getFavorites().remove(movies[0]);
                return false;
            }


        }

        @Override
        protected void onPostExecute(Boolean isFavorite) {
          /*  if(isFavorite){
                mbutButton.setText(getString(R.string.btn_unfavorite));


            }
            else {
                mbutButton.setText(getString(R.string.btn_favorite));
            }*/
            Log.d("YYYYYYYY", "onPostExecute: is favorite is "+isFavorite );

         /*   if (isFavorate){

                mbutButton.setImageResource(android.R.drawable.btn_star_big_off);
                mbutButton.setTag(getString(R.string.btn_favorite));
            }
            else {

                mbutButton.setImageResource(android.R.drawable.btn_star_big_on);
                mbutButton.setTag(getString(R.string.btn_unfavorite));       }*/
            setFavButtonText( isFavorite);

            mbutButton.setVisibility(View.VISIBLE);
        }
    }
 /*   public List<Movie> getFavorateMovies(){

        Runnable r = new Runnable() {
            public List<Movie> favMovies;
            @Override
            public void run() {
                favMovies = AppDatabase.getInstance(getApplicationContext()).movieDao().loadAllFavorites();
            }
            public void setFavMovies(List<Movie> favMovies2){
                favMovies2 = favMovies2;
            }
        };

        Thread thread = new Thread(r);
        thread.run();


    }*/
}
