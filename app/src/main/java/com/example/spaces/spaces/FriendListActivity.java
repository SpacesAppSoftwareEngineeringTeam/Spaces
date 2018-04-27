package com.example.spaces.spaces;

import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.util.Log;

import com.example.spaces.spaces.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FriendListActivity extends BaseActivity {

    private static final String TAG = "FriendListActivity";

    private RecyclerView mainRecyclerView;
    private RecyclerView.Adapter mainRecyclerAdapter;
    private RecyclerView.LayoutManager mainRecyclerLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mainRecyclerView = findViewById(R.id.friendList);

        mainRecyclerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainRecyclerLayoutManager);

        // Setup add friend FAB
        FloatingActionButton fab = findViewById(R.id.friend_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(AddFriendActivity.class);
            }
        });

        setupFriendListDataset();
    }

    void setupFriendListDataset(){
        final DatabaseReference users = mDatabase.child("users");
        final String userUID = mAuth.getCurrentUser().getUid();

        ValueEventListener friendsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> friendsList = getFriendsFromDataSnapShot(dataSnapshot, userUID);
                ArrayList<User> friendRequestList = getFriendRequestsFromDataSnapShot(dataSnapshot, userUID);

                mainRecyclerAdapter = new FriendListAdapter(friendsList, friendRequestList);
                mainRecyclerView.setAdapter(mainRecyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        users.addListenerForSingleValueEvent(friendsListener);

    }

    ArrayList<User> getFriendsFromDataSnapShot(DataSnapshot dataSnapshot, String userUID){
        ArrayList<User> friends = new ArrayList<User>();
        for(DataSnapshot friend : dataSnapshot.child(userUID).child("friendList").getChildren()){
            String friendUID = friend.getValue(String.class);
            Log.d(TAG, "Friend UID:" + friendUID);
            String displayName = dataSnapshot.child(friendUID).child("username").getValue(String.class);
            String email = dataSnapshot.child(friendUID).child("email").getValue(String.class);
            User newUser = new User(displayName, email);
            newUser.setUserID(friendUID);
            friends.add(newUser);

        }
        return friends;
    }

    ArrayList<User> getFriendRequestsFromDataSnapShot(DataSnapshot dataSnapshot, String userUID){
        ArrayList<User> requests = new ArrayList<User>();
        for(DataSnapshot request : dataSnapshot.child(userUID).child("friendRequestList").getChildren()){
            String requestUID = request.getValue(String.class);
            Log.d(TAG, "Friend Request UID:" + requestUID);
            String displayName = dataSnapshot.child(requestUID).child("username").getValue(String.class);
            String email = dataSnapshot.child(requestUID).child("email").getValue(String.class);
            User newUser = new User(displayName, email);
            newUser.setUserID(requestUID);
            requests.add(newUser);
        }
        return requests;
    }

}
