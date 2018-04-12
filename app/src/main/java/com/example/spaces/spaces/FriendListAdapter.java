package com.example.spaces.spaces;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.spaces.spaces.models.User;

import org.w3c.dom.Text;

/**
 * Created by Steven on 4/8/2018.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private User[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;       // Containing card for friend
        public TextView mFriendNameView;
        public TextView mFriendLocationView;

        public ViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.friendCardView);
            mFriendNameView = v.findViewById(R.id.friendNameText);
            mFriendLocationView = v.findViewById(R.id.friendLocationText);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListAdapter(User[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public FriendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_cardview_item, parent, false);
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

        // Set friend name
        holder.mFriendNameView.setText(mDataset[position].getUsername());
        holder.mFriendNameView.setTextSize(24);

        // Set friend location
        holder.mFriendLocationView.setText(mDataset[position].getLocationID());
        holder.mFriendLocationView.setTextSize(24);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}
