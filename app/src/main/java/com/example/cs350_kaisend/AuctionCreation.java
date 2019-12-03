package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuctionCreation extends AppCompatActivity {
    private DatabaseReference rootRef;
    EditText mDeadline, mName, mPrice, mInitDest, mFinalDest, mDelivery;
    Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_creation);
        rootRef = FirebaseDatabase.getInstance().getReference();
        mDeadline = findViewById(R.id.deadline);
        mName = findViewById(R.id.name);
        mPrice = findViewById(R.id.price);
        mInitDest = findViewById(R.id.initDest);
        mFinalDest = findViewById(R.id.finalDest);
        mDelivery = findViewById(R.id.delivery);
        mSubmit = findViewById(R.id.submit);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString();
                String initDest = mInitDest.getText().toString();
                String finalDest = mFinalDest.getText().toString();
                String deadline = mDeadline.getText().toString();
                String price = mPrice.getText().toString();
                String fee = mDelivery.getText().toString();
                String description = "Need to get " + name + " from " + initDest + " to " + finalDest + " until " +
                        deadline + ". " + "Item's price is " + price + "delivery fee is " +  fee + ".";
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(initDest) || TextUtils.isEmpty(finalDest) || TextUtils.isEmpty(deadline) || TextUtils.isEmpty(fee)){
                    Toast.makeText(getApplicationContext(), "Some of the field(s) are empty", Toast.LENGTH_SHORT);
                    return;
                }
                DatabaseReference auctionRef = rootRef.child("auctions");
                Auction auction = new Auction(name, initDest, finalDest, price, fee, deadline, description);
                auctionRef.push().setValue(auction);

                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
    }
}
