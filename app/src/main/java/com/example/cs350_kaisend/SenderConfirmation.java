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

public class SenderConfirmation extends AppCompatActivity {
    DatabaseReference requesterUidRef, deliveryRef, requesterRef;
    FirebaseDatabase database;
    FirebaseUser user;
    String uid;
    Button payConBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sender_confirmation);

        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        final TextView deliCon = findViewById(R.id.deliveryCon);
        final TextView payCon = findViewById(R.id.paymentCon);
        final TextView requestName = findViewById(R.id.requesterName);
        final TextView requestEmail = findViewById(R.id.requesterEmail);
        final TextView requestPhoneNumber = findViewById(R.id.requesterPhoneNumber);
        final TextView requestKakaoId = findViewById(R.id.requesterKakaoID);
        final TextView DeadLine = findViewById(R.id.deadLine);
        final TextView FDest = findViewById(R.id.fDest);
        final TextView Idest = findViewById(R.id.iDest);
        final TextView Iprice = findViewById(R.id.iprice);
        final TextView Iname = findViewById(R.id.iname);
        final TextView Itype = findViewById(R.id.itype);
        final TextView payCost = findViewById(R.id.paymentCost);
        payConBut = findViewById(R.id.paymentCon_button);
        DatabaseReference userRequestRef = database.getReference("users/" + uid + "/deliveries/send");
        userRequestRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String send = dataSnapshot.getValue(String.class);
                if (send == null) {
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                    finish();
                } else {
                    requesterUidRef = database.getReference("deliveries/" + send + "/requester");
                    requesterUidRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String requesterUid = dataSnapshot.getValue(String.class);
                            requesterRef = database.getReference("users/" + requesterUid);
                            requesterRef.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    String senderName = dataSnapshot.child("userName").getValue(String.class);
                                    String senderEmail = dataSnapshot.child("email").getValue(String.class);
                                    String senderPhoneNumber = dataSnapshot.child("phoneNumber").getValue(String.class);
                                    String senderKakao = dataSnapshot.child("kakaoTalkID").getValue(String.class);
                                    requestName.setText(senderName);
                                    requestEmail.setText(senderEmail);
                                    requestPhoneNumber.setText(senderPhoneNumber);
                                    requestKakaoId.setText(senderKakao);
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

                    deliveryRef = database.getReference("deliveries/" + send);
                    deliveryRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Boolean deliveryConfirm = dataSnapshot.child("deliveryConfirm").getValue(Boolean.class);
                            Boolean paymentConfirm = dataSnapshot.child("paymentConfirm").getValue(Boolean.class);
                            if((deliveryConfirm ==true)&&(paymentConfirm==true)){
                                deliveryRef.child("valid").setValue(true);
                                database.getReference("users/"+uid+"/deliveries/send").removeValue();
                            }
                            String deadline = dataSnapshot.child("item/deadline").getValue(String.class);
                            String finalDest = dataSnapshot.child("item/finalDest").getValue(String.class);
                            String initDest = dataSnapshot.child("item/initDest").getValue(String.class);
                            String name = dataSnapshot.child("item/name").getValue(String.class);
                            Integer price = dataSnapshot.child("item/price").getValue(Integer.class);
                            String type = dataSnapshot.child("item/type").getValue(String.class);
                            Integer payment = dataSnapshot.child("payment").getValue(Integer.class);
                            String priceS = Integer.toString(price);
                            String paymentS = Integer.toString(payment);
                            Log.w("DeadLine", deadline);
                            DeadLine.setText(deadline);
                            FDest.setText(finalDest);
                            Idest.setText(initDest);
                            Iprice.setText(priceS+ " Won");
                            Iname.setText(name);
                            Itype.setText(type);
                            payCost.setText(paymentS+" Won");


                            if (paymentConfirm == false) {
                                payCon.setText("Not confirmed yet");
                                payConBut.setText("Payment Confirm");
                            } else {
                                payCon.setText("Confirmed");
                                payConBut.setText("Undo Confirm");
                            }
                            if (deliveryConfirm == false) {
                                deliCon.setText("Not confirmed yet");
                            } else {
                                deliCon.setText("Confirmed");
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
    public void paymentConfirm(View view) {
        if (payConBut.getText()=="Undo Confirm"){
            deliveryRef.child("paymentConfirm").setValue(false);
        }
        else {
            deliveryRef.child("paymentConfirm").setValue(true);
        }
    }
    public void backToMain2Activity(View view){
        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        finish();
    }
}
