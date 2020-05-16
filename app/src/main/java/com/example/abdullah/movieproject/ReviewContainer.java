package com.example.abdullah.movieproject;

import java.util.ArrayList;

/**
 * Created by Kingdom on 4/12/2019.
 */

public class ReviewContainer {

    public static ArrayList<Review> review = new ArrayList<>();

    public static ArrayList<Review> getReview() {
        return review;
    }

    public static void setReview(ArrayList<Review> review) {
        ReviewContainer.review = review;
    }
}
