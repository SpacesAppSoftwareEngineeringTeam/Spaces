package com.example.spaces.spaces;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.graphics.Color;
import android.view.View.OnClickListener;

/**
 * Created by Steven on 3/14/2018.
 */

public class SpacesAdapter extends RecyclerView.Adapter<SpacesAdapter.ViewHolder> {

    private StudyLocation[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;       // Containing card for space
        public TextView SpaceName;       // Space name
        public ImageView SpaceImage;     // Space thumbnail
        public TextView SpaceRating;     // Overall space rating

        public ViewHolder(View v) {
            super(v);
            SpaceName = v.findViewById(R.id.spaceName);
            mCardView = v.findViewById(R.id.cardView);
            SpaceRating = v.findViewById(R.id.spaceRating);
            SpaceImage = v.findViewById(R.id.spaceImage);

            // Select an individual space and retrieve the space page
            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // @TODO Open the space page for the appropriate location
                }
            });
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SpacesAdapter(StudyLocation[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SpacesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spaces_cardview_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        // Set card characteristics
        if (position % 2 == 0)           // Alternate light gray and gray for enhanced readability
            holder.mCardView.setCardBackgroundColor(Color.GRAY);
        else
            holder.mCardView.setCardBackgroundColor(Color.LTGRAY);

        // Set space name
        holder.SpaceName.setText(mDataset[position].getLocationName());
        holder.SpaceName.setTextSize(24);

        // Set space thumbnail
        //holder.SpaceImage.setImage????

        // Set space rating
        StringBuilder s = new StringBuilder();        // Display rating to one decimal
        String rating = Double.toString(mDataset[position].getOverallReviewAvg());
        for (int i = 0; i < 3; i++) {
            s.append(rating.charAt(i));
        }
        rating = s.toString();
        holder.SpaceRating.setText(rating);
        int RatingColor;                             // Set text color based on rating value
        if (mDataset[position].getOverallReviewAvg() < 2)
            RatingColor = Color.RED;   // 0-1 rating is red
        else if (mDataset[position].getOverallReviewAvg() < 4)
            RatingColor = Color.YELLOW; // 2-4 rating is yellow
        else
            RatingColor = Color.GREEN;   // 4+ rating is green
        holder.SpaceRating.setTextSize(18);
        holder.SpaceRating.setTextColor(RatingColor);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
