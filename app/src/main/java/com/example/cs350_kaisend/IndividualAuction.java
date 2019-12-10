package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IndividualAuction extends AppCompatActivity {
    public static final String EXTRA_ID = "com.individualAuction.EXTRA_ID";
    public static final String EXTRA_NAME = "com.individualAuction.EXTRA_NAME";
    public static final String EXTRA_INITDEST = "com.individualAuction.EXTRA_INITDEST";
    public static final String EXTRA_FINALDEST = "com.individualAuction.EXTRA_FINALDEST";
    public static final String EXTRA_DEAD = "com.individualAuction.EXTRA_DEAD";
    public static final String EXTRA_ACTIVE = "com.individualAuction.EXTRA_ACTIVE";
    public static final String EXTRA_FEE = "com.individualAuction.EXTRA_FEE";
    public static final String EXTRA_USERID = "com.individualAuction.EXTRA_USERID";
    public static final String EXTRA_AUCTIONID = "com.individualAuction.EXTRA_AUCTIONID";
    private static final String TAG = "wassup";
    TextView mAuctionId, mAuctionName, mInitDest, mFinalDest, mDeadline, mActive, mFee;
    EditText mDelivery;
    Button mStop, mEnroll, mCancel;
    DatabaseReference rootRef;
    Integer curr, min;
    String winner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_auction);
        Intent intent = getIntent();
        min = Integer.MAX_VALUE;
        mAuctionId = findViewById(R.id.auctionId);
        mAuctionName = findViewById(R.id.auctionName);
        mInitDest = findViewById(R.id.initDest);
        mFinalDest = findViewById(R.id.finalDest);
        mDeadline = findViewById(R.id.deadline);
        mActive = findViewById(R.id.active);
        mFee = findViewById(R.id.fee);
        mStop = findViewById(R.id.stop);
        mEnroll = findViewById(R.id.enroll);
        mDelivery = findViewById(R.id.delivery);
        mCancel = findViewById(R.id.cancel);
        final String userID = String.valueOf(intent.getStringExtra(EXTRA_USERID));
        Log.d(TAG, userID);
        final String auctionID = String.valueOf(intent.getStringExtra(EXTRA_AUCTIONID));
        Log.d(TAG, auctionID);
        mAuctionId.setText(String.valueOf(intent.getIntExtra(EXTRA_ID,0)));
        mAuctionName.setText(intent.getStringExtra(EXTRA_NAME));
        mInitDest.setText(intent.getStringExtra(EXTRA_INITDEST));
        mFinalDest.setText(intent.getStringExtra(EXTRA_FINALDEST));
        mDeadline.setText(intent.getStringExtra(EXTRA_DEAD));
        mActive.setText(String.valueOf(intent.getBooleanExtra(EXTRA_ACTIVE, true)));
        mFee.setText(String.valueOf(intent.getStringExtra(EXTRA_FEE)));
        rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference userHostedAuctionRef = rootRef.child("users").child(userID).child("hosted");
        userHostedAuctionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, String.valueOf(childSnapshot.getValue()));
                    if (String.valueOf(childSnapshot.getValue()).equals(auctionID)){
                        mStop.setVisibility(View.VISIBLE);
                        return;
                    }
                }mEnroll.setVisibility(View.VISIBLE);
                mDelivery.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDelivery.getText().toString() != null){
                    HashMap<String, Integer> hm = new HashMap<>();
                    hm.put(userID, Integer.parseInt(mDelivery.getText().toString()));
                    rootRef.child("auctions").child(auctionID).child("senders").push().setValue(hm);
                    mEnroll.setVisibility(View.INVISIBLE);
                    mDelivery.setVisibility(View.INVISIBLE);
                    //mCancel.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(getApplicationContext(), "Type in your bet!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootRef.child("auctions").child(auctionID).child("senders").child(userID).removeValue();
                mCancel.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Cancellation is Successful.", Toast.LENGTH_SHORT).show();
            }
        });*/
        mStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "on Click?");
                rootRef.child("auctions").child(auctionID).child("senders").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()){
                            Log.d(TAG, "for loop?");
                            for (DataSnapshot grandchildSnapshot : childSnapshot.getChildren()) {
                                curr = Integer.parseInt(String.valueOf(grandchildSnapshot.getValue()));
                                if (curr < min) {
                                    rootRef.child("auctions").child(auctionID).child("winner").child("id").setValue(String.valueOf(grandchildSnapshot.getKey()));
                                    min = curr;
                                    rootRef.child("auctions").child(auctionID).child("winner").child("fee").setValue(min);
                                }
                            }

                        }
                        rootRef.child("auctions").child(auctionID).child("active").setValue(false);
                        mStop.setVisibility(View.INVISIBLE);
                        DatabaseReference specificAuctionRef = rootRef.child("auctions").child(auctionID);
                        specificAuctionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                try {
                                    JSONObject someAuction = new JSONObject((HashMap) dataSnapshot.getValue());
                                    Boolean active = someAuction.getBoolean("active");
                                    String deadline = someAuction.getString("deadline");
                                    String initDest = someAuction.getString("initDest");
                                    String finalDest = someAuction.getString("finalDest");
                                    int price = Integer.parseInt(someAuction.getString("price"));
                                    String hoster = someAuction.getString("owner");
                                    JSONObject winnerjson = (JSONObject) someAuction.get("winner");
                                    String name = someAuction.getString("name");
                                    int fee = winnerjson.getInt("fee");
                                    String winner = winnerjson.getString("id");
                                    Item item = new Item(deadline, initDest, finalDest, name, "none", price);
                                    Delivery del = new Delivery(hoster, winner, item, fee);
                                    HashMap<String, Delivery> hm = new HashMap<>();
                                    hm.put(auctionID, del);
                                    rootRef.child("deliveries").updateChildren((Map) hm);

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

}
