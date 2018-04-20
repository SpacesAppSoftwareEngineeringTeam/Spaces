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
 * Created by Steven on 4/8/2018.
 */

public class FriendListAdapter extends RecyclerView.Adapter<FriendListAdapter.ViewHolder> {

    private static final String TAG = "FriendListAdapter";

    private ArrayList<User> mFriendsDataset;
    private ArrayList<User> mFriendRequestsDataset;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public CardView mCardView;       // Containing card for friend
        public TextView mFriendNameView;
        public TextView mFriendLocationView;
        public Button mFriendAcceptButton;
        public Button mFriendRemoveButton;
        private boolean hasBeenLongClicked = false;

        public ViewHolder(View v) {
            super(v);
            mCardView = v.findViewById(R.id.friendCardView);
            mFriendNameView = v.findViewById(R.id.friendNameText);
            mFriendLocationView = v.findViewById(R.id.friendLocationText);
            mFriendAcceptButton = v.findViewById(R.id.friendAcceptButton);
            mFriendRemoveButton = v.findViewById(R.id.friendRemoveButton);

            mCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(getAdapterPosition() >= mFriendRequestsDataset.size()){
                        if(!hasBeenLongClicked){
                            mFriendLocationView.setVisibility(View.GONE);
                            mFriendRemoveButton.setVisibility(View.VISIBLE);
                            hasBeenLongClicked = true;
                        }
                        else{
                            hasBeenLongClicked = false;
                            mFriendLocationView.setVisibility(View.VISIBLE);
                            mFriendRemoveButton.setVisibility(View.GONE);
                        }
                    }
                    return false;
                }
            });
            mFriendAcceptButton.setOnClickListener(this);
            mFriendRemoveButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            if(v.equals(mFriendAcceptButton)) {
                acceptUserAtPosition(getAdapterPosition());
            } else if(v.equals(mFriendRemoveButton)) {
                removeUserAtPosition(getAdapterPosition());
            }

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public FriendListAdapter(ArrayList<User> myFriendsDataset, ArrayList<User> myFriendRequestsDataset) {
        mFriendsDataset = myFriendsDataset;
        mFriendRequestsDataset = myFriendRequestsDataset;

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
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

        if(position < mFriendRequestsDataset.size()){
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
        return mFriendRequestsDataset.size() + mFriendsDataset.size() ;
    }

    public User getUserFromPosition(int position){
        if(position < 0 || position > getItemCount()){
            Log.d(TAG,"Position is out of range");
        }
        if(position < mFriendRequestsDataset.size()){
            return mFriendRequestsDataset.get(position);
        }
        else{
            int adjustedPosition = position - mFriendRequestsDataset.size();
            return mFriendsDataset.get(adjustedPosition);
        }
    }

    public void removeUserAtPosition(int position){
        if(position < 0 || position > getItemCount()){
            Log.d(TAG,"Position is out of range");
        }
        else {
            User removedUser;
            String removedUserUID;
            String listPath;
            if(position < mFriendRequestsDataset.size()) {
                removedUser = mFriendRequestsDataset.remove(position);
                removedUserUID = removedUser.getUserID();
                listPath = "friendRequestList";
            }
            else {
                position = position - mFriendRequestsDataset.size();
                removedUser = mFriendsDataset.remove(position);
                removedUserUID = removedUser.getUserID();
                listPath = "friendList";
            }

            String currentUserUID = mAuth.getCurrentUser().getUid();
            DatabaseReference ref = mDatabase.child("users").child(currentUserUID).child(listPath);

            ref.orderByValue().equalTo(removedUserUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, getItemCount());
        }

    }

    public void acceptUserAtPosition(int position){
        if(position < 0 || position > getItemCount()){
            Log.d(TAG,"Position is out of range");
        }
        if(position < mFriendRequestsDataset.size()){
            User friend = mFriendRequestsDataset.remove(position);
            mFriendsDataset.add(friend);
            final String friendUID = friend.getUserID();
            final String currentUserUID = mAuth.getCurrentUser().getUid();
            DatabaseReference requestRef = mDatabase.child("users").child(currentUserUID).child("friendRequestList");
            requestRef.orderByValue().equalTo(friendUID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    dataSnapshot.getRef().removeValue();
                    DatabaseReference friendListRef = mDatabase.child("users").child(currentUserUID).child("friendList").push();
                    friendListRef.setValue(friendUID);
                    DatabaseReference friendFriendListRef = mDatabase.child("users").child(friendUID).child("friendList").push();
                    friendFriendListRef.setValue(currentUserUID);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
            notifyItemRemoved(position);
            notifyItemInserted(getItemCount() - 1);
            notifyItemRangeChanged(position, getItemCount());
        }
        else{
            Log.d(TAG,"attempting to hit accept on an existing friend!");
        }
    }


}
