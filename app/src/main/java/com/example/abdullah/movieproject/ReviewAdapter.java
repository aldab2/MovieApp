package com.example.abdullah.movieproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kingdom on 4/12/2019.
 */

public class ReviewAdapter extends  android.support.v7.widget.RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> mReviews;
     public  ReviewAdapter(ArrayList<Review> mReviews){
         this.mReviews = mReviews;
     }

    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.reveiw_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ReviewAdapter.ReviewViewHolder viewHolder = new ReviewAdapter.ReviewViewHolder(view);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        String author = review.getAuthor();
        String reviewContent = review.getContent();

        holder.mTvAuthor.setText(author);
        holder.mTvReviewContent.setText(reviewContent);
        Log.d("YYYYYYYYY", "onBindViewHolder: size = "+mReviews.size() );
        if(holder.mTvNoReview != null) {
            Log.d("YYYYYYYYY", "onBindViewHolder: inside if = "+mReviews.size() );
            if (mReviews.size()==1 & mReviews.get(0).author.equals("") && mReviews.get(0).content.equals(""))
                holder.mTvNoReview.setVisibility(View.VISIBLE);
            else
                holder.mTvNoReview.setVisibility(View.INVISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        if(mReviews == null)
        return 0;
        if(mReviews.size() ==0) {
            mReviews.add(new Review("",""));
            return 1;

        }
        else return mReviews.size();
    }

    public ArrayList<Review> getmReviews() {
        return mReviews;
    }

    public void setmReviews(ArrayList<Review> mReviews) {
        this.mReviews = mReviews;
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder {

        TextView mTvAuthor;
        TextView mTvReviewContent;
        TextView mTvNoReview;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            mTvAuthor = (TextView) itemView.findViewById(R.id.tv_author_name);
            mTvReviewContent = itemView.findViewById(R.id.tv_review_content);
            mTvNoReview = itemView.findViewById(R.id.tv_no_review);
            Boolean bool =mTvNoReview==null;
            Boolean boolOther= mTvAuthor==null;
            Log.d("YYYYYYY", "ReviewViewHolder: Setted ids "+bool+" Others "+boolOther );
        }
    }
}
