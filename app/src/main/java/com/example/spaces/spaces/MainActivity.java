package com.example.spaces.spaces;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.spaces.spaces.models.StudyLocation;
import com.example.spaces.spaces.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    private RecyclerView mainRecyclerView;
    private RecyclerView.Adapter mainRecyclerAdapter;
    private RecyclerView.LayoutManager mainRecyclerLayoutManager;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_nav_drawer);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mainRecyclerView = findViewById(R.id.spaceList);

        mainRecyclerLayoutManager = new LinearLayoutManager(this);
        mainRecyclerView.setLayoutManager(mainRecyclerLayoutManager);

        // Setup a Floating Action Button to launch add space and review activities
        SpeedDialView fab = findViewById(R.id.fab);
        SpeedDialOverlayLayout fabOverlay = findViewById(R.id.overlay);
        fab.setOverlayLayout(fabOverlay);
        fab.addActionItem(new SpeedDialActionItem.Builder(R.id.new_review, R.drawable.ic_edit)
                .setLabel("Review a space")
                .create());
        fab.addActionItem(new SpeedDialActionItem.Builder(R.id.new_space, R.drawable.ic_space)
                .setLabel("Add a new space")
                .create());

        fab.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem actionItem) {
                switch (actionItem.getId()) {
                    case R.id.new_review:
                        start(SelectLocationActivity.class);
                        // close the speed dial
                        return false;
                    case R.id.new_space:
                        // launch activity to create a new space
                        start(AddSpaceActivity.class);
                        return false;
                    default:
                        return false;
                }
            }
        });

        // Setup building list display
        setupBuildingDataset();
    }

    // (based on setupFriendListDataset from FriendListActivity)
    void setupBuildingDataset() {
        final DatabaseReference locations = mDatabase.child("locations");

        locations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<StudyLocation> locationList = getLocationsFromDataSnapShot(dataSnapshot);
                StudyLocation[] locationArray = new StudyLocation[locationList.size()];
                locationList.toArray(locationArray);
                //@TODO modify SpacesAdapter to receive ArrayList i.o. Array; would clean up the code above
                mainRecyclerAdapter = new SpacesAdapter(locationArray);
                mainRecyclerView.setAdapter(mainRecyclerAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,
                        "Couldn't connect to the database. Are you connected to the internet?",
                        Toast.LENGTH_LONG).show();
            }
        });
        //);
        //locations.addListenerForSingleValueEvent(locationListener);

    }

    ArrayList<StudyLocation> getLocationsFromDataSnapShot(DataSnapshot dataSnapshot) {
        ArrayList<StudyLocation> locations = new ArrayList<StudyLocation>();
        for(DataSnapshot location : dataSnapshot.getChildren()) {
            String locationUID = location.getKey();
            Log.d(TAG, "Location UID:" + locationUID);
            String locationName = dataSnapshot.child(locationUID).child("locationName").getValue(String.class);
            StudyLocation space = new StudyLocation(locationName);
            space.setOverallReviewAvg(dataSnapshot.child(locationUID).child("overallReviewAvg").getValue(Double.class));
            locations.add(space);
        }
        return locations;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Check auth on Activity start
        if (mAuth.getCurrentUser() != null) {
            updateUI(mAuth.getCurrentUser());
        }
    }

    public void updateUI(FirebaseUser currentUser){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navView = drawer.findViewById(R.id.nav_view);
        View headerLayout = navView.getHeaderView(0);
        TextView drawerNameField = headerLayout.findViewById(R.id.drawerNameTextView);
        TextView drawerEmailField = headerLayout.findViewById(R.id.drawerEmailTextView);

        drawerNameField.setText(currentUser.getDisplayName());
        drawerEmailField.setText(currentUser.getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share_spot) {

        } else if (id == R.id.nav_locate_friends) {
            start(FriendListActivity.class);
        } else if (id == R.id.nav_add_friends) {
            start(AddFriendActivity.class);
        } else if (id == R.id.nav_outlets) {

        } else if (id == R.id.nav_whiteboards) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if (id == R.id.search) { /* launch search activity */ }
        if (id == R.id.log_out) {
            FirebaseAuth.getInstance().signOut();
            start(SignInActivity.class);
            finish();
        }



        return super.onOptionsItemSelected(item);
    }


}
