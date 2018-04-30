package com.example.spaces.spaces;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.spaces.spaces.models.StudyLocation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Map;

public class GalleryActivity extends BaseActivity {
    private static final String TAG = "Spaces#SpaceGalleryAct";

    // [START declare_storage_ref]
    private StorageReference mStorageRef;
    // [END declare_storage_ref]
    // [START declare_database_ref]
    private DatabaseReference mDatabase;
    // [END declare_database_ref]

    private String locationName;

    private RecyclerView galleryRecyclerView;
    private RecyclerView.Adapter galleryRecyclerAdapter;
    private RecyclerView.LayoutManager galleryRecyclerLayoutManager;

    private ArrayList<StorageReference> pictureRefs = new ArrayList<>();
    private ArrayList<Uri> pictureURIs = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gallery_page);

        galleryRecyclerView = findViewById(R.id.pictureList);
        galleryRecyclerLayoutManager = new LinearLayoutManager(this);
        galleryRecyclerView.setLayoutManager(galleryRecyclerLayoutManager);

        // [START get_storage_ref]
        mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START initialize_database_ref]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END initialize_database_ref]

        // get the location name passed to this activity on creation
        Bundle b = getIntent().getExtras();
        if (b != null) locationName = b.getString("name");

        DatabaseReference locations = mDatabase.child("locations");

        locations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.child(locationName).exists()) {
                    // location doesn't exist
                    Log.d(TAG, "No location with the name "+locationName+" exists");
                }
                else {
                    // pull the location info from the database into a StudyLocation
                    StudyLocation location = new StudyLocation(locationName, snapshot.child(locationName));
                    // populate the space page with info on this location
                    setupPictureDataset(location, snapshot);
                }
            }
            @Override
            public void onCancelled(DatabaseError de) {
                de.toException().printStackTrace();
            }
        });
    }

    void setupPictureDataset(StudyLocation location, DataSnapshot snapshot) {
        fetchPictures(location, snapshot);
        galleryRecyclerAdapter = new GalleryAdapter(GalleryActivity.this,
                pictureRefs.toArray(new StorageReference[pictureRefs.size()]),
                pictureURIs.toArray(new Uri[pictureURIs.size()]),
                locationName);
        Log.d(TAG, "Adapter attached successfully");
        galleryRecyclerView.setAdapter(galleryRecyclerAdapter);
    }

    private void fetchPictures(StudyLocation location, DataSnapshot snapshot) {
        // Refactored from SpacesPageActivity thumbnail process
        Map<String, String> picIds = null;
        if (location.getPictureIds() != null) {
            picIds = location.getPictureIds();
            System.out.println(locationName+": found pictures " +picIds);
        } else System.out.println(locationName + ": had null picture ids");

        if (picIds != null && !picIds.isEmpty()) {
            Object[] ids = picIds.values().toArray();
            // Add all image reference paths/URIs
            for (int i = 0; i < ids.length; i++) {
                String id = ids[ids.length - i - 1].toString();
                String path = ImageUploader.getImagePath(Uri.parse(id));
                System.out.println("got path " + path);
                pictureRefs.add(mStorageRef.child(path));
                pictureURIs.add(Uri.parse(id));
            }
        }
    }

}
