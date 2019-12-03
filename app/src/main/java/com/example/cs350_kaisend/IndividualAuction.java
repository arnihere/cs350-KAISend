package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class IndividualAuction extends AppCompatActivity {
    public static final String EXTRA_ID = "com.individualAuction.EXTRA_ID";
    public static final String EXTRA_NAME = "com.individualAuction.EXTRA_NAME";
    public static final String EXTRA_INITDEST = "com.individualAuction.EXTRA_INITDEST";
    public static final String EXTRA_FINALDEST = "com.individualAuction.EXTRA_FINALDEST";
    public static final String EXTRA_DEAD = "com.individualAuction.EXTRA_DEAD";
    public static final String EXTRA_ACTIVE = "com.individualAuction.EXTRA_ACTIVE";
    public static final String EXTRA_FEE = "com.individualAuction.EXTRA_FEE";
    TextView mAuctionId, mAuctionName, mInitDest, mFinalDest, mDeadline, mActive, mFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_auction);
        Intent intent = getIntent();
        mAuctionId = findViewById(R.id.auctionId);
        mAuctionName = findViewById(R.id.auctionName);
        mInitDest = findViewById(R.id.initDest);
        mFinalDest = findViewById(R.id.finalDest);
        mDeadline = findViewById(R.id.deadline);
        mActive = findViewById(R.id.active);
        mFee = findViewById(R.id.fee);

        mAuctionId.setText(String.valueOf(intent.getIntExtra(EXTRA_ID,0)));
        mAuctionName.setText(intent.getStringExtra(EXTRA_NAME));
        mInitDest.setText(intent.getStringExtra(EXTRA_INITDEST));
        mFinalDest.setText(intent.getStringExtra(EXTRA_FINALDEST));
        mDeadline.setText(intent.getStringExtra(EXTRA_DEAD));
        mActive.setText(String.valueOf(intent.getBooleanExtra(EXTRA_ACTIVE, true)));
        mFee.setText(String.valueOf(intent.getStringExtra(EXTRA_FEE)));


    }
}
