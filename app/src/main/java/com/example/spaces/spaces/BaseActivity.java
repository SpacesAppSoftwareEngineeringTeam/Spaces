package com.example.spaces.spaces;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.FileNotFoundException;
import java.io.IOException;


public abstract class BaseActivity extends AppCompatActivity {

    protected static final int GET_FROM_GALLERY = 3;
    private static final String TAG = "Spaces#BaseActivity";
    private final long MAX_IMAGE_BYTES = 10000000;
    private Bitmap galleryImage;
    private ProgressDialog mProgressDialog;

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage(msg); //e.g. "Loading..."
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * starts a typical activity
     * @param activity  the activity class to start
     */
    protected <T extends Activity> void start(Class<T> activity) {
        startActivity(new Intent(this, activity));
    }

    /**
     * @return unique id of the current user
     */
    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    /**
     *  opens the user's gallery so they can select an image to upload
     */
    protected void openGallery() {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI),
                        GET_FROM_GALLERY);
    }

    /**
     * gets a bitmap of a user-selected image
     * intended for use when an activity results in a request to read from the gallery
     */
    protected Bitmap getFromGallery(int requestCode, int resultCode, Intent data) {

        // button to get image from gallery was clicked
        if (resultCode == Activity.RESULT_OK && requestCode == GET_FROM_GALLERY) {
            final Uri selectedImage = data.getData();
            if (selectedImage == null) {
                galleryImage = null;
            }
            else {
                // ask for storage permissions to upload an image
                Dexter.withActivity(this)
                        .withPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
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
                                    galleryImage = image;


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
        else {
            throw new RuntimeException("Invalid request to get from gallery");
        }
        
        return galleryImage;
    }



}
