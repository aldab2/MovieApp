package com.example.abdullah.movieproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by abdullah on 18/03/19.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    int viewHolderCount= 10;
     Movie movieItem;
     ArrayList<Movie> movies= new ArrayList<>();

     // TODO make a constructer that takes a movie ArrayList
    public MovieAdapter(){

    }
    public MovieAdapter(ArrayList<Movie> movies){
    this.movies =movies;
    viewHolderCount = movies.size();
    }



    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup , int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        movieItem = new Movie();
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);

        //viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);



        //viewHolderCount++;
        Log.d("MovieAdapter", "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;


    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
            holder.bind(holder);
    }

    @Override
    public int getItemCount() {
        return viewHolderCount  ;
    }



    // TODO implement View.onClickListner
    class  MovieViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener   {
            ImageView image;
        public MovieViewHolder(View view){
            super(view);

            image = (ImageView) view.findViewById(R.id.imgview_movie_item);

            // TODO Complete This Section
            // TODO (7) Call setOnClickListener on the View passed into the constructor (use 'this' as the OnClickListener)
            view.setOnClickListener(this);
        }


        public void bind(MovieViewHolder holder){
            int i = holder.getAdapterPosition();
            image = (ImageView) holder.itemView.findViewById(R.id.imgview_movie_item);
            //Picasso.get().load("http://image.tmdb.org/t/p/w185//iiZZdoQBEYBv6id8su7ImL0oCbD.jpg").fit().centerCrop().into(movieItem.image);
            Picasso.get().load(movies.get(i).imageURL).fit().centerCrop().into(image);
        }


            @Override
        public void onClick(View view) {
                Toast.makeText(view.getContext(),"xxxxxxxxxx",Toast.LENGTH_SHORT);
            Intent detailsIntent = new Intent(view.getContext(),MovieDetails.class);
                detailsIntent.putExtra("rating",movies.get(getAdapterPosition()).getRating());
                detailsIntent.putExtra("overview",movies.get(getAdapterPosition()).getOverview());
                detailsIntent.putExtra("relaseDate",movies.get(getAdapterPosition()).relaseDate);
                detailsIntent.putExtra("tittle",movies.get(getAdapterPosition()).getOriginalTitle());
                detailsIntent.putExtra("image_full_url",movies.get(getAdapterPosition()).imageURL);
                detailsIntent.putExtra("isAdult",movies.get(getAdapterPosition()).getAdult());
                detailsIntent.putExtra("id",movies.get(getAdapterPosition()).getId());
                Log.d("XXXXXXXx", "onClick: is adult: "+movies.get(getAdapterPosition()).getAdult() );
                view.getContext().startActivity(detailsIntent);


        }
    }
    public int getViewHolderCount() {
        return viewHolderCount;
    }

    public void setViewHolderCount(int viewHolderCount) {
        this.viewHolderCount = viewHolderCount;
    }
}
