package com.example.abdullah.movieproject;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by abdullah on 18/03/19.
 */

public class NetworkUtils {

    private static final String KEY = "a13db04bc8683668b9727735405b164d";
    private static final String ENG_LANG = "en-US";
    private static String pageNumber = "1";
    private static  final String  BASE_URL_P1 = "https://api.themoviedb.org/3/movie/";
    private static  String  BASE_URL_P2 ="" ;
    private static String BASE_URL;
    private static String KEY_PARAM = "api_key";
    private static String LANG_PARAM = "language";
    private static String PAGE_PARAM = "page";

    public static void  setBaseURL(String searchType){
        BASE_URL_P2 = searchType;
        BASE_URL = BASE_URL_P1+BASE_URL_P2;

    }
    public static void  setReviewsBaseURL(String id){
        BASE_URL_P2 = id;
        BASE_URL = BASE_URL_P1+BASE_URL_P2+"/reviews";

    }
    public static void  setTrailersBaseURL(String id){
        BASE_URL_P2 = id;
        BASE_URL = BASE_URL_P1+BASE_URL_P2+"/videos";

    }

        // TODO see the page number issue if needed
        // TODO Make the Lang variable eng arabic ...
    public static URL buildMoviesURL(){
        if(BASE_URL ==null){
            Log.d("NetworkUtils","BASE URL is NULL!!");
        }else{
            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM,KEY)
                    .appendQueryParameter(LANG_PARAM,ENG_LANG)
                    .appendQueryParameter(PAGE_PARAM,pageNumber).build();

            try {
                URL url = new URL(uri.toString());
                return url;

            }
            catch (MalformedURLException mue){
                mue.printStackTrace();

            }
        }
        Log.d("NetworkUtils"," THE CODE SHOULD NOT REACH HERE");
        return null;

    }

    public static URL buildReviewURL() {
        if (BASE_URL == null) {
            Log.d("NetworkUtils", "BASE URL is NULL!!");
        } else {
            Uri uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, KEY)
                    .appendQueryParameter(LANG_PARAM, ENG_LANG)
                    .appendQueryParameter(PAGE_PARAM, pageNumber).build();

            try {
                URL url = new URL(uri.toString());
                return url;

            } catch (MalformedURLException mue) {
                mue.printStackTrace();

            }
        }
        Log.d("NetworkUtils", " THE CODE SHOULD NOT REACH HERE");
        return null;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.d("XXXXXXXX", "getResponseFromHttpUrl: "+url );
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.connect();

        try {

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");


            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.d("NetworkUtils", "getResponseFromHttpUrl:  HAS Input");
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


}
