package com.example.abdullah.movieproject;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class TestActivity extends AppCompatActivity {
        TextView mTestView;
        String TAG = getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);


        //NetworkUtils.getBaseURL("top_rated");
        URL url = NetworkUtils.buildMoviesURL();
        mTestView = (TextView) findViewById(R.id.tv_test);
        Log.d(TAG, "onCreate: mTestView is "+mTestView );

        new MovieSearchTask().execute(url);
    }



    class MovieSearchTask extends AsyncTask<URL,Void,String>{
        @Override
        protected String doInBackground(URL... urls) {
            URL url = urls[0];
            String moviesSearchResults = null;
            try {
                moviesSearchResults = NetworkUtils.getResponseFromHttpUrl(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return moviesSearchResults;

        }

        @Override
        protected void onPostExecute(String s) {
            if(s==null){
                Log.d("TestActivity", "onPostExecute: Search Result Is NULL" );
            }
            else if(mTestView == null){
                Log.d("TestActivity", "onPostExecute: TextView is Is NULL" );


            }
            else
            mTestView.setText(s);

        }
    }

}
