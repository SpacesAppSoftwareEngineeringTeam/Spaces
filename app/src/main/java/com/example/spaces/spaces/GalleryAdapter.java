package com.example.spaces.spaces;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

import java.util.Map;


/**
 * Created by Matt on 4/29/18.
 */

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ViewHolder> {
    private static final String TAG = "SpacesAdapter";

    private Context context;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;
    private ViewHolder[] viewHolders;
    private StorageReference[] pictureRefs;
    private Uri[] pictureURIs;
    private String locationName;

    /**
     * Provide a reference to the views for each data item
     * Complex data items may need more than one view per item, and
     * you provide access to all the views for a data item in a view holder
     */
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView picture;

        public ViewHolder(View v) {
            super(v);
            picture = v.findViewById(R.id.pictureView);
        }
    }

    /**
     *  Provide a suitable constructor (depends on the kind of dataset)
     */
    public GalleryAdapter(Context context, StorageReference[] pictureRefs, Uri[] pictureURIs, String locationName) {
        // [START get_storage_ref]
        this.mStorageRef = FirebaseStorage.getInstance().getReference();
        // [END get_storage_ref]
        // [START get_database_ref]
        this.mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END get_database_ref]
        this.context = context;
        this.pictureRefs = pictureRefs;
        this.pictureURIs = pictureURIs;
        this.locationName = locationName;
        this.viewHolders = new ViewHolder[pictureRefs.length];
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    @Override
    public GalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallery_single_photo, parent, false);

        return new ViewHolder(v);
    }

    /**
     *  Replace the contents of a view (invoked by the layout manager)
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolders[position] = holder;

        final StorageReference pictureRef = pictureRefs[position];
        final Uri pictureURI = pictureURIs[position];

        loadPicture(pictureRef, pictureURI, position);
    }

    private void loadPicture(StorageReference pictureRef, final Uri pictureURI, final int position) {
        RequestListener<Drawable> requestListener = new RequestListener<Drawable>() {
            Handler handler = new Handler();
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                // try again in case there is a local version that hasn't finished uploading
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getLocalCopy();
                    }
                    private void getLocalCopy() {
                        Glide.with(context)
                                .load(pictureURI)
                                .into(viewHolders[position].picture);
                    }
                });
                // return false so the error placeholder can be placed
                return false;
            }
            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                // everything worked out, so probably nothing to do
                return false;
            }
        };

        Glide.with(context)
                .load(pictureRef)
                .listener(requestListener)
                .into(viewHolders[position].picture);
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    @Override
    public int getItemCount() {
        return pictureRefs.length;
    }
}
