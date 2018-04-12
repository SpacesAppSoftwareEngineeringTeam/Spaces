package com.example.spaces.spaces;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AddFriendActivity extends AppCompatActivity {

    private static final String TAG = "Spaces#AddFriendActivity";
    private EditText email;
    private Button submitButton;
    private FirebaseAuth mAuth;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.addFriendEmailText);
        submitButton = findViewById(R.id.submitFriendRequest);

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                submit(v);
            }
        });
    }

    public void submit(final View v) {
        final String finalEmail = email.getText().toString();

        if (finalEmail.equals("")) {
            Toast.makeText(AddFriendActivity.this,
                    "Enter an email", Toast.LENGTH_LONG).show();
        }
        else if (userWithEmailExists(finalEmail)) {
            Toast.makeText(AddFriendActivity.this,
                    "Friend Request Sent", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    public boolean userWithEmailExists(String email){
        final DatabaseReference users = mDatabase.child("users");
        return email.contains("@case.edu");
        //TODO:
        /*users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.child(email).) {
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
        });*/

    }



}
