package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ManageClaimActivity extends AppCompatActivity {
    private TextView itemText, senderText, requesterText, priceText, titleText, contentText;
    private Button penaltyButton, ignoreButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_claim);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference claimsRef = database.getReference().child("claims");
        final DatabaseReference userRef = database.getReference().child("users");

        itemText = findViewById(R.id.item_text);
        senderText = findViewById(R.id.sender_text);
        requesterText = findViewById(R.id.requester_text);
        priceText = findViewById(R.id.price_text);
        titleText = findViewById(R.id.title_text);
        contentText = findViewById(R.id.content_text);

        penaltyButton = findViewById(R.id.button_penalty);
        ignoreButton = findViewById(R.id.button_ignore);

        itemText.setText(getIntent().getStringExtra("item"));
        senderText.setText(getIntent().getStringExtra("sender"));
        requesterText.setText(getIntent().getStringExtra("requester"));
        priceText.setText(Integer.toString(getIntent().getIntExtra("price", 1)));
        titleText.setText(getIntent().getStringExtra("title"));
        contentText.setText(getIntent().getStringExtra("content"));

        penaltyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageClaimActivity.this, "Penalty Given", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageClaimActivity.this, ClaimActivity.class);
                startActivity(intent);
            }
        });

        ignoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ManageClaimActivity.this, "Claim Ignored", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ManageClaimActivity.this, ClaimActivity.class);
                startActivity(intent);
            }
        });

    }
}
