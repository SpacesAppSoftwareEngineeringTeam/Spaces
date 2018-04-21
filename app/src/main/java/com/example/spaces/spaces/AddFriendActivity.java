package com.example.spaces.spaces;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.spaces.spaces.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class AddFriendActivity extends BaseActivity {

    private static final String TAG = "AddFriendActivity";
    private EditText email;
    private Button submitButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_friend);



        email = findViewById(R.id.addFriendEmailText);
        submitButton = findViewById(R.id.submitFriendRequest);

        mAuth = FirebaseAuth.getInstance();
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
        sendFriendRequestFromEmail(finalEmail);
    }

    public void sendFriendRequestFromEmail(String email){
        final DatabaseReference users = mDatabase.child("users");
        Query query = users.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot user: dataSnapshot.getChildren()){
                    String friendUID = user.getKey();
                    String thisUserUID = mAuth.getCurrentUser().getUid();
                    DatabaseReference friendRequestRef = users.child(friendUID).child("friendRequestList").push();
                    friendRequestRef.setValue(thisUserUID);

                    Toast.makeText(AddFriendActivity.this,
                            "Friend Request Sent", Toast.LENGTH_LONG).show();
                    finish();
                }
                if (dataSnapshot.getChildrenCount() == 0) {
                    //nothing in the dataSnapshot
                    Toast.makeText(AddFriendActivity.this,
                            "Email isn't registered!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
