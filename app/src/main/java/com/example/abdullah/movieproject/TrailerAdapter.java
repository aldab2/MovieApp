package com.example.abdullah.movieproject;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Barnawi on 4/12/2019.
 */

public class TrailerAdapter extends  android.support.v7.widget.RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {
    private ArrayList<String> mTrailers;
    ActionMode actionMode;

    public TrailerAdapter(ArrayList<String> mTrailers) {
        this.mTrailers = mTrailers;
    }

    @Override
    public TrailerAdapter.TrailerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.trailer_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        TrailerAdapter.TrailerViewHolder viewHolder = new TrailerAdapter.TrailerViewHolder(view);


        return viewHolder;
    }
    public void setActionMode(ActionMode actionMode) {
        this.actionMode = actionMode;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return (actionMode == null ? 0 : 1);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.TrailerViewHolder holder, int position) {
        String trailer = mTrailers.get(position);

        Log.e("TTTTTTTTTTTTT",  "We are Here"+ mTrailers.size()+" "+holder.mTvNoTraielr.getText()+" " +mTrailers.get(0) );

        if(mTrailers.size()==1 && holder.mTvNoTraielr!= null && mTrailers.get(0).equals("NoTrailer")) {
            holder.mTvNoTraielr.setVisibility(View.VISIBLE);
            holder.mTvTrailer.setVisibility(View.INVISIBLE);
            holder.mImageView.setVisibility(View.INVISIBLE);

        }
        else
        if(holder.mTvTrailer!= null  && !mTrailers.get(position).equals("NoTrailer")) {
            holder.mTvTrailer.setText("Trailer " + (position + 1));
            holder.mTvNoTraielr.setVisibility(View.INVISIBLE);
            holder.mTvTrailer.setVisibility(View.VISIBLE);
            holder.mImageView.setVisibility(View.VISIBLE);
        }





    }

    @Override
    public int getItemCount() {
        if (mTrailers == null)
            return 0;
        if(mTrailers.size()==0){
            mTrailers.add(new String("NoTrailer"));
            return 1;
        }
        else return mTrailers.size();
    }

    public ArrayList<String> getmTrailers() {
        return mTrailers;
    }

    public void setmReviews(ArrayList<String> mTrailers) {
        this.mTrailers = mTrailers;
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView mTvTrailer;
        ImageView mImageView;
        TextView mTvNoTraielr;



        public TrailerViewHolder(View itemView) {
            super(itemView);
            mTvTrailer = (TextView) itemView.findViewById(R.id.textView);
            mTvNoTraielr = itemView.findViewById(R.id.tv_no_trailer);
            mImageView =  (ImageView) itemView.findViewById(R.id.img_view);
            mTvNoTraielr.setVisibility(View.INVISIBLE);
            mTvTrailer.setVisibility(View.VISIBLE);
            mImageView.setVisibility(View.VISIBLE);
            //mTvTrailer.setText("Trailer"+itemView.getId()+1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = getAdapterPosition();
            Log.d("YYYYYYYYYYYYYYYY", "onClick: " );
            String key = mTrailers.get(elementId);
            watchYoutubeVideo(view.getContext(),key);
        }
        public  void watchYoutubeVideo(Context context, String id){
            if(mTvNoTraielr.getVisibility()== View.VISIBLE)
                return;
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + id));
            try {
                context.startActivity(appIntent);
            } catch (ActivityNotFoundException ex) {
                if (webIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(webIntent);
                }

            }
        }
    }
}
