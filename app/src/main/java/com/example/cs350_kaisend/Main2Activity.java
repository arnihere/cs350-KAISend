package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Main2Activity extends AppCompatActivity {
    private TextView mainTextView;
    private Button ourButton;
    Button btnLogOut;
    private Button requestButton, senderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
/**
        requestButton = findViewById(R.id.Requester);
        senderButton = findViewById(R.id.Sender);

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuctionCreation.class));
                finish();
            }
        });

        senderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AuctionListView.class));
                finish();
            }
        });
**/
        btnLogOut = findViewById(R.id.btnLogOut);
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
        startActivity(new Intent(getApplicationContext(), AuctionCreation.class));
        finish();
    }

    public void goToAuctionList(View view) {
        startActivity(new Intent(getApplicationContext(), AuctionListView.class));
        finish();
    }

    public void myRequests(View view) {
        startActivity(new Intent(getApplicationContext(), MyRequests.class));
    }
}