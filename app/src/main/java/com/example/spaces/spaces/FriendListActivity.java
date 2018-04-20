package com.example.spaces.spaces;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.spaces.spaces.models.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class FriendListActivity extends BaseActivity {

    private RecyclerView mainRecyclerView;
    private RecyclerView.Adapter mainRecyclerAdapter;
    private RecyclerView.LayoutManager mainRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list);

        mainRecyclerView = findViewById(R.id.friendList);

        mainRecyclerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainRecyclerLayoutManager);

        // Test location array
        User[] testFriendData = new User[10];
        Random r = new Random();
        for (int i = 0; i < testFriendData.length; i++) {
            testFriendData[i] = new User("Friend " + i, "Friend" + i + "@case.edu");
            testFriendData[i].setLocationID("Testlocation");
        }

        User[] testFriendRequestData = new User[10];
        for (int i = 0; i < testFriendRequestData.length; i++) {
            testFriendRequestData[i] = new User("Request " + i, "Friend" + i + "@case.edu");
            testFriendRequestData[i].setLocationID("Testlocation");
        }

        mainRecyclerAdapter = new FriendListAdapter(new ArrayList<User>(Arrays.asList(testFriendData)),new ArrayList<User>(Arrays.asList(testFriendRequestData)));
        mainRecyclerView.setAdapter(mainRecyclerAdapter);

        // Setup add friend FAB
        FloatingActionButton fab = findViewById(R.id.friend_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(AddFriendActivity.class);
            }
        });
    }

}
