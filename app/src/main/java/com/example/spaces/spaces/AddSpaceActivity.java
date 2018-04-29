package com.example.spaces.spaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.example.spaces.spaces.models.Review;

import java.util.LinkedList;


/**
 * Created by Owen on 4/1/18.
 */

public class AddSpaceActivity extends BaseActivity {
    private static final String TAG = "Spaces#AddSpaceActivity";

    private int pictureCount = 0;
    private LinkedList<Bitmap> pictures = new LinkedList<>();
    private EditText name;
    private ImageButton addImageButton;
    private Button submitButton;
    private String prefilledName;
    // [START declare_storage_ref]
    private StorageReference mStorageRef;
    // [END declare_storage_ref]
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_space);

        name = findViewById(R.id.add_space_name);
        addImageButton = findViewById(R.id.add_space_add_photos);
        submitButton = findViewById(R.id.add_space_submit);
        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // get the location name passed to this activity on creation, if any
        Bundle b = getIntent().getExtras();
        if (b != null) prefilledName = b.getString("name");
        // prefill the given location name
        ((TextView) findViewById(R.id.add_space_name)).setText(prefilledName);

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
            // update formatted string to display new number of images uploaded
            ((TextView) findViewById(R.id.add_space_imagecount)).setText(newString);
        }
    }

    public void submit(final View v) {
        final String locationName = name.getText().toString();

        if (locationName.equals("")) {
            Toast.makeText(AddSpaceActivity.this,
                    "Enter a name", Toast.LENGTH_LONG).show();
        }
        else if (name.length() > 20) {
            Toast.makeText(AddSpaceActivity.this,
                    "The name must be 20 characters or less", Toast.LENGTH_LONG).show();
        }
        else {
            final DatabaseReference locations = mDatabase.child("locations");

            locations.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    if (snapshot.child(locationName).exists()) {
                        // location already exists
                        Toast.makeText(AddSpaceActivity.this, "A location with that name already exists", Toast.LENGTH_LONG).show();
                    }
                    else {
                        // create new location
                        StudyLocation location = new StudyLocation(locationName);
                        // add location to the database
                        locations.child(locationName).setValue(location);
                        for (Bitmap pic : pictures) {
                            Uri uri = ImageUploader.getImageUri(v.getContext(), pic);
                            if (uri != null) {
                                // upload image to firebase cloud storage
                                ImageUploader.uploadFromUri(uri, TAG, mStorageRef);
                                // get image url
                                String url = (new ImageUploader()).getImageUrl(uri, mStorageRef);
                                Log.d(TAG, "submit: url=" +url+", uri="+uri+", location=\""+locationName+"\"");
                                // add image to the StudyLocation and database
                                location.addPicture(uri, snapshot);
                                Log.d(TAG, "submit: " + uri + "to location \"" + locationName + "\"");
                            }
                        }

                        // if this activity was launched by the SelectLocationActivity
                        if (prefilledName != null) {
                            // proceed to the review page
                            start(ReviewActivity.class, "name", locationName);
                        }

                        // return to main screen
                        finish();
                    }
                }

                @Override
                public void onCancelled(DatabaseError de) {
                    de.toException().printStackTrace();
                }
            });
        }
    }

}
