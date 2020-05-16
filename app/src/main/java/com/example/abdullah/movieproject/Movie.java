package com.example.abdullah.movieproject;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;



/**
 * Created by abdullah on 18/03/19.
 */

@Entity
public class Movie {

    @PrimaryKey
    int id;
    String originalTitle;

    String overview;
    Double rating;//////////////////////
    String relaseDate;
    Boolean isAdult;/////////////////////////////
    String originalLanguage;
    String imageBase = "http://image.tmdb.org/t/p/w185//";
    String imagePath ;

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String imageURL ;

    public Movie(int id,String originalTitle, String imagePath, String overview, Double rating, String relaseDate, Boolean isAdult, String originalLanguage) {
        this.id=id;
        this.originalTitle = originalTitle;
        this.imagePath = imagePath;
        this.imageURL = imageBase+imagePath;
        this.overview = overview;
        this.rating = rating;
        this.relaseDate = relaseDate;
        this.isAdult = isAdult;
        this.originalLanguage = originalLanguage;


    }
    @Ignore
    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }





    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getRelaseDate() {
        return relaseDate;
    }

    public void setRelaseDate(String relaseDate) {
        this.relaseDate = relaseDate;
    }

    public Boolean getAdult() {
        return isAdult;
    }

    public void setAdult(Boolean adult) {
        isAdult = adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }
}
