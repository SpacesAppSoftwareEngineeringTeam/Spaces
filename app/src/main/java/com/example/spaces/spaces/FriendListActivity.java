package com.example.spaces.spaces;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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

        mainRecyclerView = findViewById(R.id.spaceList);

        mainRecyclerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainRecyclerLayoutManager);

        // Test location array
        User[] testFriendData = new User[10];
        Random r = new Random();
        for (int i = 0; i < testFriendData.length; i++) {
            testFriendData[i] = new User("User " + i, "User" + i + "@case.edu");
        }

        mainRecyclerAdapter = new FriendListAdapter(testFriendData);
        mainRecyclerView.setAdapter(mainRecyclerAdapter);
    }
}
