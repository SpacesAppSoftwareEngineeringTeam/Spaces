package com.example.spaces.spaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;


/**
 * Created by Owen on 4/8/18.
 */

class ImageUploader {

    private String url;
    String getImageUrl(Uri uri, final StorageReference storageRef) {
        final String imgPath = getImagePath(uri);
        System.out.println("getImageUrl: imgPath = "+imgPath);

        storageRef.child(imgPath).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                url = storageRef.child(imgPath).getDownloadUrl().getResult().toString();
            }
        });

        String urlToReturn = url;
        url = null;
        return urlToReturn;
    }

    static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "image", null);
        return Uri.parse(path);
    }

    static String getImagePath(Uri uri) {
        return "photos/"+uri.toString().substring(uri.toString().lastIndexOf('/'));
    }

    // [START upload_from_uri]
    static void uploadFromUri(final Uri fileUri, final String tag, StorageReference storageRef) {
        Log.d(tag, "uploadFromUri:src:" + fileUri.toString());

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = storageRef.child("photos")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        Log.d(tag, "uploadFromUri:dst:" + photoRef.getPath());
        photoRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the public download URL
                        //Uri downloadUri = taskSnapshot.getMetadata().getDownloadUrl();
                        // Upload succeeded
                        Log.d(tag, "uploadFromUri:onSuccess");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed
                        Log.w(tag, "uploadFromUri:onFailure", exception);
                    }
                });
    }
    // [END upload_from_uri]



}
