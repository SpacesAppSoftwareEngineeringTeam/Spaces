package com.example.spaces.spaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.spaces.spaces.models.Review;
import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.LinkedList;

/**
 * Created by Owen on 4/16/18.
 */

public class ReviewActivity extends BaseActivity {

    private static final String TAG = "Spaces#ReviewActivity";
    private String locationName;
    private EditText reviewText;
    private RatingBar quietnessBar;
    private RatingBar busynessBar;
    private RatingBar comfortBar;
    private CheckBox whiteboardsBox;
    private CheckBox outletsBox;
    private CheckBox computersBox;
    private ImageButton addImageButton;
    private int pictureCount = 0;
    private LinkedList<Bitmap> pictures = new LinkedList<>();
    private Button submitButton;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);

        reviewText = findViewById(R.id.review_textbox);
        quietnessBar = findViewById(R.id.review_quietness_bar);
        busynessBar = findViewById(R.id.review_busyness_bar);
        comfortBar = findViewById(R.id.review_comfort_bar);
        whiteboardsBox = findViewById(R.id.review_whiteboard_checkbox);
        outletsBox = findViewById(R.id.review_outlet_checkbox);
        computersBox = findViewById(R.id.review_computers_checkbox);
        addImageButton = findViewById(R.id.review_add_photos_button);
        submitButton = findViewById(R.id.review_submit);

        //mAuth = FirebaseAuth.getInstance();
        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // get the location name passed to this activity on creation
        Bundle b = getIntent().getExtras();
        if (b != null) locationName = b.getString("name");

        // attach the action listeners
        addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                openGallery();
            }
        });
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit(v);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // attempt to retrieve a user-selected image from the gallery
        Bitmap image = getFromGallery(requestCode, resultCode, data);
        if (image != null) {
            pictures.add(image);
            pictureCount++;
            String newString = String.valueOf(pictureCount);
            ((TextView) findViewById(R.id.review_imagecount_number)).setText(newString);
        }

    }

    private void submit(final View v) {
        //final DatabaseReference reviews = mDatabase.child("reviews");
        final DatabaseReference locationRef = mDatabase.child("locations").child(locationName);

        locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                // pull the location from the database
                StudyLocation location = snapshot.getValue(StudyLocation.class);
                if (location != null) {
                    Log.d(TAG, "Submitting review: \n"
                            +"   location="+locationName+", reviewText="+reviewText.getText().toString()
                            +"\n   quietness="+quietnessBar.getRating()+", busyness="+busynessBar.getRating()+", comfort="+comfortBar.getRating()
                            +"\n   whiteboards="+whiteboardsBox.isChecked()+", outlets="+outletsBox.isChecked()+", computers="+computersBox.isChecked()
                            +"\n   date="+Calendar.getInstance().getTime().toString());

                    // create a review from the current entries
                    Review review = new Review(
                            locationName, reviewText.getText().toString(),
                            quietnessBar.getRating(), busynessBar.getRating(), comfortBar.getRating(),
                            whiteboardsBox.isChecked(), outletsBox.isChecked(), computersBox.isChecked(),
                            Calendar.getInstance().getTime().toString());

                    for (Bitmap pic : pictures) {
                        Uri uri = ImageUploader.getImageUri(v.getContext(), pic);
                        if (uri != null) {
                            // add image to the StudyLocation
                            location.addPicture(uri.toString());
                            // upload image to firebase cloud storage
                            ImageUploader.uploadFromUri(uri, TAG, mStorageRef);
                            Log.d(TAG, "submit: " + uri.toString() + "to location \"" + locationName + "\"");
                        }
                    }
                    // add new location to database
                    DatabaseReference newReviewRef = locationRef.child("reviews").push();
                    newReviewRef.setValue(review);

                    // return to main screen
                    finish();
                }
                else {
                    throw new RuntimeException(
                            "attempted to submit a review of a location that doesn't exist: "+locationName
                    );
                }

            }

            @Override
            public void onCancelled(DatabaseError de) {
                de.toException().printStackTrace();
            }
        });
    }

}
