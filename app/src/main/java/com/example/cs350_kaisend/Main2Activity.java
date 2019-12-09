package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;


public class Main2Activity extends AppCompatActivity {
    private TextView mainTextView;
    private Button ourButton;
    FirebaseDatabase database;
    Button btnLogOut;
    FirebaseUser user;
    String uid;
    private Button requestButton, senderButton;
    private Button makeClaimButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        Log.d("Arggggggg", "users/" + uid + "/deliveries");


        btnLogOut = findViewById(R.id.btnLogOut);
        makeClaimButton = findViewById(R.id.btnClaim);
        btnLogOut = findViewById(R.id.btnLogOut);

        makeClaimButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main2Activity.this, BrowseClaimActivity.class);
                startActivity(intent);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent I = new Intent(Main2Activity.this, MainActivity.class);
                startActivity(I);

            }
        });
    }

    public void createAuction(View view) {
        DatabaseReference userRequestRef = database.getReference("users/" + uid+"/deliveries/request");
        userRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //   Get Post object and use the values to update the UI
                String request = dataSnapshot.getValue(String.class);
                if (request == null) {
                    startActivity(new Intent(getApplicationContext(), AuctionCreation.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), RequesterConfirmation.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("readDeliveriesError", "loadPost:onCancelled", databaseError.toException());
            }
        });

        finish();
    }


    public void goToAuctionList(View view) {
        DatabaseReference userSendRef = database.getReference("users/" + uid+"/deliveries/send");
        userSendRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //   Get Post object and use the values to update the UI
                String send = dataSnapshot.getValue(String.class);
                if (send == null) {
                    startActivity(new Intent(getApplicationContext(), AuctionListView.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), SenderConfirmation.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("readDeliveriesError", "loadPost:onCancelled", databaseError.toException());
            }
        });

        finish();
    }


    public void myRequests(View view) {
        startActivity(new Intent(getApplicationContext(), MyRequests.class));
    }

}