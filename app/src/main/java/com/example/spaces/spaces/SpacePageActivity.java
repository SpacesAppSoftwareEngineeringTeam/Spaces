package com.example.spaces.spaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialOverlayLayout;
import com.leinardi.android.speeddial.SpeedDialView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.support.v7.widget.CardView;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.graphics.Color;
import android.widget.Toast;
import android.support.design.widget.FloatingActionButton;

/**
 * Created by Matt on 4/11/2018.
 */

public class SpacePageActivity extends BaseActivity {
    private static final String TAG = "Spaces#SpacePageActivit";

    // Location structure for the view
    //private static StudyLocation location;

    // General space information
    private String locationName;
    private TextView nameTextView;

    // Space photos layout information
    private CardView photosCard;
    private TextView photosTitle;
    private ImageView thumb1;
    private ImageView thumb2;
    private ImageView thumb3;
    private ImageButton seeMorePhotos;

    // Space reviews layout information
    private CardView reviewsCard;
    private TextView reviewsTitle;

    // Space ratings layout information
    private CardView ratingsCard;
    private TextView ratingsTitle;
    private TextView overallTitle;
    private TextView quietnessTitle;
    private TextView busynessTitle;
    private TextView comfortTitle;
    private TextView overallValue;
    private TextView quietnessValue;
    private TextView busynessValue;
    private TextView comfortValue;
    // [START declare_storage_ref]
    private StorageReference mStorageRef;
    // [END declare_storage_ref]
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    // General constructor
    public SpacePageActivity() {
        super();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.space_page);
        setViews();
        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // get the location name passed to this activity on creation
        Bundle b = getIntent().getExtras();
        if (b != null) locationName = b.getString("name");

        final DatabaseReference locations = mDatabase.child("locations");
        locations.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.child(locationName).exists()) {
                    // location doesn't exist
                    Log.d(TAG, "No location with the name "+locationName+" exists");
                }
                else {
                    // pull the location info from the database
                    StudyLocation location = (StudyLocation)snapshot.child(locationName).getValue();
                    // populate the space page with info on this location
                    specifyViewInfo(location);
                }
            }
            @Override
            public void onCancelled(DatabaseError de) {
                de.toException().printStackTrace();
            }
        });

        // Setup a Floating Action Button to launch add review activity (based on MainActivity implementation)
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ReviewActivity.class);
                // open the add review page for the location specified
                view.getContext().startActivity(
                        i.putExtra("name", locationName)
                );
            }
        });

        // Setup "see more photos" button
        seeMorePhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(GalleryActivity.class);
            }
        });

    }

    private void specifyViewInfo(StudyLocation location) {
        nameTextView.setText(location.getLocationName());
        overallValue.setText(Double.toString(location.getOverallReviewAvg()));
        quietnessValue.setText(Double.toString(location.getQuietnessAvg()));
        busynessValue.setText(Double.toString(location.getBusynessAvg()));
        comfortValue.setText(Double.toString(location.getComfortAvg()));
    }

    private void setViews() {
        nameTextView = findViewById(R.id.spaceName);
        photosCard = findViewById(R.id.photosCard);
        photosTitle = findViewById(R.id.photosTitle);
        thumb1 = findViewById(R.id.thumb1);
        thumb2 = findViewById(R.id.thumb2);
        thumb3 = findViewById(R.id.thumb3);
        seeMorePhotos = findViewById(R.id.seeMorePhotos);
        reviewsCard = findViewById(R.id.reviewsCard);
        reviewsTitle = findViewById(R.id.reviewsTitle);
        ratingsCard = findViewById(R.id.ratingsCard);
        ratingsTitle = findViewById(R.id.ratingsTitle);
        overallTitle = findViewById(R.id.overallTitle);
        quietnessTitle = findViewById(R.id.quietnessTitle);
        busynessTitle = findViewById(R.id.busynessTitle);
        comfortTitle = findViewById(R.id.comfortTitle);
        overallValue = findViewById(R.id.overallValue);
        quietnessValue = findViewById(R.id.quietnessValue);
        busynessValue = findViewById(R.id.busynessValue);
        comfortValue = findViewById(R.id.comfortValue);
    }

}
