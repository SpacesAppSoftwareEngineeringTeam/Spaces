package com.example.spaces.spaces;

import android.graphics.Color;
import android.opengl.Visibility;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.spaces.spaces.models.User;

import org.w3c.dom.Text;

/**
 * Created by Steven on 4/8/2018.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private static final String TAG = "FriendListAdapter";

    private User[] mFriendsDataset;
    private User[] mFriendRequestsDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;       // Containing card for friend
        public TextView mFriendNameView;
        public TextView mFriendLocationView;
        public Button mFriendAcceptButton;
        public Button mFriendRemoveButton;

        public ViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.friendCardView);
            mFriendNameView = v.findViewById(R.id.friendNameText);
            mFriendLocationView = v.findViewById(R.id.friendLocationText);
            mFriendAcceptButton = v.findViewById(R.id.friendAcceptButton);
            mFriendRemoveButton = v.findViewById(R.id.friendRemoveButton);
        }

    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListAdapter(User[] myFriendsDataset, User[] myFriendRequestsDataset) {
        mFriendsDataset = myFriendsDataset;
        mFriendRequestsDataset = myFriendRequestsDataset;

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
        User user = getUserFromPosition(position);

        // Set card characteristics
        holder.mFriendNameView.setText(user.getUsername());
        holder.mFriendNameView.setTextSize(24);

        if(position < mFriendRequestsDataset.length){
            holder.mCardView.setCardBackgroundColor(Color.LTGRAY);

            holder.mFriendLocationView.setVisibility(View.GONE);
            holder.mFriendAcceptButton.setVisibility(View.VISIBLE);
            holder.mFriendRemoveButton.setVisibility(View.VISIBLE);
        }
        else{
            holder.mCardView.setCardBackgroundColor(Color.GRAY);

            holder.mFriendLocationView.setVisibility(View.VISIBLE);
            holder.mFriendLocationView.setText(user.getLocationID());
            holder.mFriendLocationView.setTextSize(24);

            holder.mFriendAcceptButton.setVisibility(View.GONE);
            holder.mFriendRemoveButton.setVisibility(View.GONE);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mFriendRequestsDataset.length + mFriendsDataset.length ;
    }

    public User getUserFromPosition(int position){
        if(position < 0 || position > getItemCount()){
            Log.d(TAG,"Position is out of range");
        }
        if(position < mFriendRequestsDataset.length){
            return mFriendRequestsDataset[position];
        }
        else{
            int adjustedPosition = position - mFriendRequestsDataset.length;
            return mFriendsDataset[adjustedPosition];
        }
    }
}
