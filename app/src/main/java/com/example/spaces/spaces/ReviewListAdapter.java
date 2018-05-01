package com.example.spaces.spaces;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaces.spaces.R;
import com.example.spaces.spaces.models.Review;
import com.example.spaces.spaces.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaces.spaces.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Steven on 4/30/2018.
 */

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private static final String TAG = "ReviewListAdapter";

    private ArrayList<Review> mReviewDataset;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public class ViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;       // Containing card for friend
        public TextView mOverallRatingView;
        public TextView mReviewCommentView;

        public ViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.reviewCardView);
            mOverallRatingView = v.findViewById(R.id.reviewOverallRatingView);
            mReviewCommentView = v.findViewById(R.id.reviewCommentText);
        }

    }

    public ReviewListAdapter(ArrayList<Review> reviews) {
        mReviewDataset = reviews;
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public ReviewListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_cardview_item, parent, false);
        ReviewListAdapter.ViewHolder vh = new ReviewListAdapter.ViewHolder(v);
        return vh;
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(ReviewListAdapter.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Review review = mReviewDataset.get(position);


        String overall = String.valueOf(review.getOverall());
        Log.d(TAG, overall);
        // Set card characteristics

        holder.mCardView.setCardBackgroundColor(Color.LTGRAY);
        holder.mOverallRatingView.setText(overall);
        holder.mOverallRatingView.setTextSize(24);

        holder.mReviewCommentView.setText(review.getComment());
        holder.mReviewCommentView.setTextSize(24);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return mReviewDataset.size();
    }
}
