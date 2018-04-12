package com.example.spaces.spaces;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.spaces.spaces.models.StudyLocation;
import com.example.spaces.spaces.models.User;

import java.util.Random;

public class FriendListActivity extends AppCompatActivity {

    private RecyclerView mainRecyclerView;
    private RecyclerView.Adapter mainRecyclerAdapter;
    private RecyclerView.LayoutManager mainRecyclerLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        mainRecyclerView = findViewById(R.id.friendList);

        mainRecyclerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainRecyclerLayoutManager);

        // Test location array
        User[] testFriendData = new User[10];
        Random r = new Random();
        for (int i = 0; i < testFriendData.length; i++) {
            testFriendData[i] = new User("Friend " + i, "Friend" + i + "@case.edu");
        }

        mainRecyclerAdapter = new FriendListAdapter(testFriendData);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);

        // Setup add space FAB
        FloatingActionButton fab = findViewById(R.id.friend_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(AddFriendActivity.class);
            }
        });
    }

    //starts a typical activity
    private void start(Class c) {
        startActivity(new Intent(this, c));
    }
}
