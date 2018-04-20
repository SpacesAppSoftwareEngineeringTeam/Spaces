package com.example.spaces.spaces;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

public class SpaceListActivity extends BaseActivity {

    private static RecyclerView mainRecyclerView;
    private static RecyclerView.Adapter mainRecyclerAdapter;

    // General constructor
    public SpaceListActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static RecyclerView getRecyclerView() {
        return mainRecyclerView;
    }

    public static RecyclerView.Adapter getRecyclerAdapter() {
        return mainRecyclerAdapter;
    }

    public static void setRecyclerView(RecyclerView mainRecyclerView) {
        SpaceListActivity.mainRecyclerView = mainRecyclerView;
    }

    public static void setRecyclerAdapter(RecyclerView.Adapter mainRecyclerAdapter) {
        SpaceListActivity.mainRecyclerAdapter = mainRecyclerAdapter;
    }

}
