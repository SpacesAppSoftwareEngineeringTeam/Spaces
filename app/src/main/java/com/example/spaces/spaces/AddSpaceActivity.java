package com.example.spaces.spaces;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;


/**
 * Created on 4/1/18.
 */

public class AddSpaceActivity extends Activity {

    private final long MAX_IMAGE_BYTES = 10000000;
    private int imageCount = 0;
    EditText name;
    ImageButton addImageButton;
    Button submitButton;

    // request codes
    public static final int GET_FROM_GALLERY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_space_screen);
        name = findViewById(R.id.add_space_name);
        addImageButton = findViewById(R.id.add_space_add_photos);
        submitButton = findViewById(R.id.add_space_submit);

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
               // check if the space info added is valid
               String result = verifyName(name.getText().toString());
               if (result.startsWith("ERROR")) {
                   // display error message
                   Toast.makeText(AddSpaceActivity.this, result.substring("ERROR:".length()), Toast.LENGTH_LONG).show();
               }
               else {
                   //@TODO store the images with Cloud Firestore.
                   //@TODO add image references and space title to database

                   //return user to main screen
                   finish();
               }
           }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Request to get images from the gallery detected
        if (resultCode == Activity.RESULT_OK && requestCode == GET_FROM_GALLERY) {
            Uri selectedImage = data.getData();
            Bitmap image;
            try {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                int minSize = image.getByteCount();
                if (minSize > MAX_IMAGE_BYTES) {
                    //@TODO downsize image (low priority feature)
                }
                // increment number of images uploaded, to be displayed to the user
                imageCount++;
                String str = getResources().getString(R.string.add_space_imagecount);
                // update formatted string to display new number of images uploaded
                String newString = str.replace(str.charAt(str.length()-1), (char)(imageCount+'0'));
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
