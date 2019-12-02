package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mainTextView;
    private Button ourButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createAuction(View view) {
        startActivity(new Intent(getApplicationContext(), AuctionCreation.class));
        finish();
    }

    public void goToAuctionList(View view) {
        startActivity(new Intent(getApplicationContext(), AuctionListView.class));
        finish();
    }
}
