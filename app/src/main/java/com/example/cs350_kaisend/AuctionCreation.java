package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;


public class AuctionCreation extends AppCompatActivity {
    private static final String TAG = "DUDE COME ON";
    private DatabaseReference rootRef;
    EditText mDeadline, mPrice,mName, mInitDest, mFinalDest, mDelivery;
    Button mSubmit,mCancel;
    String userID;
    private Spinner spinner;
    //private Spinner mType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_creation);

        rootRef = FirebaseDatabase.getInstance().getReference();
        mDeadline = findViewById(R.id.auction_Deadline);
        mName = findViewById(R.id.auction_Name);
        mPrice = findViewById(R.id.auction_Price);
        mInitDest = findViewById(R.id.auction_From);
        mFinalDest = findViewById(R.id.auction_To);
        mDelivery = findViewById(R.id.auction_Deliveryfee);
        mSubmit = findViewById(R.id.button_submit);
        mCancel = findViewById(R.id.button_cancel);
        spinner = findViewById(R.id.auction_type);
        userID = FirebaseAuth.getInstance().getUid();
        //mType.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayList<String> list = new ArrayList<String>();
        list.add("Item's Type");
        list.add("Send Assignment");
        list.add("Deliver Food/item");

        ArrayAdapter adapter2 = new ArrayAdapter(this, R.layout.item, R.id.textview,list);
        spinner.setAdapter(adapter2);
        spinner.setOnItemSelectedListener(new auction_typeListener());

        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String initDest = mInitDest.getText().toString();
                String finalDest = mFinalDest.getText().toString();
                String deadline = mDeadline.getText().toString();
                String name = mName.getText().toString();
                String price = mPrice.getText().toString();
                String fee = mDelivery.getText().toString();
                String description = "Need to get " + name + " from " + initDest + " to " + finalDest + " until " +
                        deadline + ". " + "Item's price is " + price + "Delivery fee is " +  fee + ".";
                if (isValid(name, initDest, finalDest,deadline, fee, price)){
                    Toast.makeText(getApplicationContext(), "Some of the field(s) are empty or fee/price is not digits only! ", Toast.LENGTH_SHORT);
                    return;
                }
                final DatabaseReference auctionRef = rootRef.child("auctions");
                final Auction auction = new Auction(name, initDest, finalDest, price, fee, deadline, description);
                auctionRef.push().setValue(auction);
                final DatabaseReference userRef = rootRef.child("users");
                final String currUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                auctionRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String id = "";
                        for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                            Log.d(TAG, "not working?!");
                            id = String.valueOf(childSnapshot.getKey());
                            auctionRef.child(id).child("id").setValue(id);

                        }auctionRef.child(id).child("owner").setValue(userID);
                        auction.setId(id);
                        auction.setOwner(userID);
                        userRef.child(currUser).child("hosted").push().setValue(id);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    class auction_typeListener implements android.widget.AdapterView.OnItemSelectedListener{


        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            String selected = parent.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            System.out.println("nothingSelect");
        }
    }
    public boolean isValid(String name, String initDest, String finalDest, String deadline, String fee, String price){
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(initDest) || TextUtils.isEmpty(finalDest) || TextUtils.isEmpty(deadline) || TextUtils.isEmpty(fee) || TextUtils.isEmpty(price)){
            return false;
        }if (!TextUtils.isDigitsOnly(fee) || !TextUtils.isDigitsOnly(price)) return false;
        return true;
    }
}
