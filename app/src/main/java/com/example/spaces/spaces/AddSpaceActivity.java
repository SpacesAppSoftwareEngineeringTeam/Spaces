package com.example.spaces.spaces;

import android.app.Activity;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;


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
               String locationName = name.getText().toString();
               // check if the space info added is valid
               String result = verifyName(locationName);
               if (result.startsWith("ERROR")) {
                   // display error message
                   Toast.makeText(AddSpaceActivity.this,
                           result.substring("ERROR:".length()), Toast.LENGTH_LONG).show();
               }
               else {
                   // create new StudyLocation
                   StudyLocation location = new StudyLocation(locationName);

                   for (Bitmap pic : pictures) {
                       Uri uri = ImageUploader.getImageUri(v.getContext(), pic);
                       if (uri != null) {
                           // add image to the StudyLocation
                           location.addPicture(uri);
                           // upload image to firebase cloud storage
                           ImageUploader.uploadFromUri(uri, TAG, mStorageRef);

                           Log.d(TAG, "submit: " + uri.toString() + "to location \"" + locationName + "\"");
                       }
                   }
                   // submit new location to database
                   mDatabase.child("locations").child(locationName).setValue(location);

                   // return to main screen
                   finish();
               }
           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // get image from gallery
        if (resultCode == Activity.RESULT_OK && requestCode == GET_FROM_GALLERY) {
            Uri selectedImage = data.getData();
            Bitmap image;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                int minSize = image.getByteCount();
                if (minSize > MAX_IMAGE_BYTES) {
                    //@TODO downsize image (low priority)
                }
                pictures.add(image);
                pictureCount++;
                // update formatted string to display new number of images uploaded
                String str = getResources().getString(R.string.add_space_imagecount);
                String newString = str.replace(str.charAt(str.length()-1), (char)(pictureCount +'0'));
                ((TextView)findViewById(R.id.add_space_imagecount_text)).setText(newString);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String verifyName(String name) {
        if (name == null || name.equals("")) {
            return "ERROR: Enter a name";
        }
        else if (name.length() > 50) {
            return "ERROR: The name must be under 50 characters";
        }

        else {
            return "Success";
        }
    }
}
