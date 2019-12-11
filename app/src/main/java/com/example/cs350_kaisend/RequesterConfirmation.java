package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
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


public class RequesterConfirmation extends AppCompatActivity {

    DatabaseReference senderUidRef, deliveryRef, senderRef;
    FirebaseDatabase database;
    FirebaseUser user;
    String uid;
    Button deliConBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requester_confirmation);
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        final TextView deliCon = findViewById(R.id.deliveryCon);
        final TextView payCon = findViewById(R.id.paymentCon);
        final TextView sendName = findViewById(R.id.senderName);
        final TextView sendEmail = findViewById(R.id.senderEmail);
        final TextView sendPhoneNumber = findViewById(R.id.senderPhoneNumber);
        final TextView sendKakaoId = findViewById(R.id.senderKakaoID);
        final TextView DeadLine = findViewById(R.id.deadLine);
        final TextView FDest = findViewById(R.id.fDest);
        final TextView Idest = findViewById(R.id.iDest);
        final TextView Iprice = findViewById(R.id.iprice);
        final TextView Iname = findViewById(R.id.iname);
        final TextView Itype = findViewById(R.id.itype);
        final TextView payCost = findViewById(R.id.paymentCost);
        deliConBut = findViewById(R.id.deliveryCon_button);
        DatabaseReference userRequestRef = database.getReference("users/" + uid + "/deliveries/request");
        userRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String request = dataSnapshot.getValue(String.class);
                if (request == null) {
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                    finish();
                } else {
                    senderUidRef = database.getReference("deliveries/" + request + "/sender");
                    senderUidRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String senderUid = dataSnapshot.getValue(String.class);
                            senderRef = database.getReference("users/" + senderUid);
                            senderRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String senderName = dataSnapshot.child("userName").getValue(String.class);
                                    String senderEmail = dataSnapshot.child("email").getValue(String.class);
                                    String senderPhoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                                    String senderKakao = dataSnapshot.child("kakaoTalkID").getValue(String.class);
                                    sendName.setText(senderName);
                                    sendEmail.setText(senderEmail);
                                    sendPhoneNumber.setText(senderPhoneNumber);
                                    sendKakaoId.setText(senderKakao);
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Log.w("requestConError", "loadPost:onCancelled", databaseError.toException());
                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.w("requestConError", "loadPost:onCancelled", databaseError.toException());
                        }
                    });

                    deliveryRef = database.getReference("deliveries/" + request);
                    deliveryRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean deliveryConfirm = dataSnapshot.child("deliveryConfirm").getValue(Boolean.class);
                            Boolean paymentConfirm = dataSnapshot.child("paymentConfirm").getValue(Boolean.class);
                            if((deliveryConfirm ==true)&&(paymentConfirm==true)){
                                deliveryRef.child("valid").setValue(true);
                                database.getReference("users/"+uid+"/deliveries/request").removeValue();
                            }
                            String deadline = dataSnapshot.child("item/deadline").getValue(String.class);
                            String finalDest = dataSnapshot.child("item/finalDest").getValue(String.class);
                            String initDest = dataSnapshot.child("item/initDest").getValue(String.class);
                            String name = dataSnapshot.child("item/name").getValue(String.class);
                            Integer price = dataSnapshot.child("item/price").getValue(Integer.class);
                            String type = dataSnapshot.child("item/type").getValue(String.class);
                            Integer payment = dataSnapshot.child("payment").getValue(Integer.class);

                            Log.w("DeadLine", deadline);
                            DeadLine.setText(deadline);
                            FDest.setText(finalDest);
                            Idest.setText(initDest);
                            Iprice.setText(price+" Won");
                            Iname.setText(name);
                            Itype.setText(type);
                            payCost.setText(payment+" Won");

                            if (paymentConfirm == false) {
                                payCon.setText("Not confirmed yet");
                            } else {
                                payCon.setText("Confirmed");
                            }
                            if (deliveryConfirm == false) {
                                deliCon.setText("Not confirmed yet");
                                deliConBut.setText("Delivery Confirm");
                            } else {
                                deliCon.setText("Confirmed");
                                deliConBut.setText("Undo Confirm");
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            Log.w("requestConError", "loadPost:onCancelled", databaseError.toException());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w("requestConError", "loadPost:onCancelled", databaseError.toException());
            }
        });

    }

    public void deliveryConfirm(View view) {
        if(deliConBut.getText()=="Undo Confirm"){
            deliveryRef.child("deliveryConfirm").setValue(false);
        }
        else {
            deliveryRef.child("deliveryConfirm").setValue(true);
        }
    }
    public void backToMain2Activity(View view){
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        finish();
    }
}

