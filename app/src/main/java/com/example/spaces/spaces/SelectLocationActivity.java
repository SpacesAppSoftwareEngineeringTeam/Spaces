package com.example.spaces.spaces;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by Owen on 4/19/18.
 */

public class SelectLocationActivity extends BaseActivity {

    private EditText nameEntry;
    private Button nextButton;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private String purpose; // the reason a location is being selected e.g. "view" or "review"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_location);

        nameEntry = findViewById(R.id.field_select_location);
        nextButton = findViewById(R.id.button_next);
        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // get the location name passed to this activity on creation, if any
        Bundle b = getIntent().getExtras();
        if (b != null) purpose = b.getString("purpose");

        nextButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String name = nameEntry.getText().toString();

                //final DatabaseReference reviews = mDatabase.child("reviews");
                final DatabaseReference locationRef = mDatabase.child("locations").child(name);

                locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        // try to pull the location from the database
                        StudyLocation location = snapshot.getValue(StudyLocation.class);
                        switch (purpose) {
                            case "review":
                                if (location == null)  // open the add space page for the location the user wants to review
                                    start(AddSpaceActivity.class, "name", name);
                                else        // open the add review page for the preexisting location
                                    start(ReviewActivity.class, "name", name);
                                break;
                            case "view":
                                if (location == null)
                                    Toast.makeText(SelectLocationActivity.this,
                                            "That location doesn't exist", Toast.LENGTH_LONG).show();
                                else
                                    start(SpacePageActivity.class, "name", name);
                                break;
                            default: break;
                        }


                    }
                    @Override
                    public void onCancelled(DatabaseError de) {
                        de.toException().printStackTrace();
                    }
                });



            }
        });
    }



}
