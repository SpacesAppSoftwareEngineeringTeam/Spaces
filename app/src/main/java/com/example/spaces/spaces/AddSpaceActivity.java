package com.example.spaces.spaces;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;


/**
 * Created on 4/1/18.
 */

public class AddSpaceActivity extends BaseActivity {

    private static final String TAG = "Spaces#AddSpaceActivity";
    private static final int GET_FROM_GALLERY = 3;
    private final long MAX_IMAGE_BYTES = 10000000;

    private int pictureCount = 0;
    private ArrayList<Bitmap> pictures = new ArrayList<>();
    private EditText name;
    private ImageButton addImageButton;
    private Button submitButton;

    // [START declare_storage_ref]
    private StorageReference mStorageRef;
    // [END declare_storage_ref]
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_space_screen);
        name = findViewById(R.id.add_space_name);
        addImageButton = findViewById(R.id.add_space_add_photos);
        submitButton = findViewById(R.id.add_space_submit);

        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        addImageButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // open the user's gallery so they can select an image to upload
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        GET_FROM_GALLERY);
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

        // button to get image from gallery was clicked
        if (resultCode == Activity.RESULT_OK && requestCode == GET_FROM_GALLERY) {
            final Uri selectedImage = data.getData();
            // ask for storage permissions to upload an image
            Dexter.withActivity(this)
                    .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .withListener(new PermissionListener() {
                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse response) {
                            Log.d(TAG, "storage permission: denied");
                        }
                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                            token.continuePermissionRequest();
                        }
                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse response) {
                            Log.d(TAG, "storage permission: granted");

                            Bitmap image;
                            try {
                                // copy image from gallery
                                image = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                                int minSize = image.getByteCount();
                                if (minSize > MAX_IMAGE_BYTES) {
                                    //@TODO downsize image (low priority)
                                }
                                pictures.add(image);
                                pictureCount++;
                                // update formatted string to display new number of images uploaded
                                String str = getResources().getString(R.string.add_space_imagecount);
                                String newString = str.replace(str.charAt(str.length()-1), (char)('0'+pictureCount));
                                ((TextView) findViewById(R.id.add_space_imagecount_text)).setText(newString);

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
                    .withErrorListener(new PermissionRequestErrorListener() {
                        @Override
                        public void onError(DexterError error) {
                            Log.d(TAG, "Error getting image from gallery: " + error.toString());
                        }

                    }).check();
        }
    }

    public void submit(final View v) {
        final String locationName = name.getText().toString();

        if (locationName.equals("")) {
            Toast.makeText(AddSpaceActivity.this,
                    "Enter a name", Toast.LENGTH_LONG).show();
        }
        else if (name.length() > 50) {
            Toast.makeText(AddSpaceActivity.this,
                    "The name must be under 50 characters", Toast.LENGTH_LONG).show();
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
                        StudyLocation location = new StudyLocation(locationName);
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
                        locations.child(locationName).setValue(location);

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
