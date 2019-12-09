package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ClaimActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claim);

        final ArrayList<claimItem> claimItems = new ArrayList<claimItem>();
        database = FirebaseDatabase.getInstance();
        final DatabaseReference claimsRef = database.getReference().child("claims");
        listView = findViewById(R.id.claimListview);

        claimsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    JSONObject claimsObject = new JSONObject((HashMap)dataSnapshot.getValue());
                    JSONArray claimsArray = claimsObject.toJSONArray(claimsObject.names());
                    for (int i=0; i<claimsArray.length(); i++){
                        JSONObject deliveryItem = (JSONObject)claimsArray.get(i);

                        String item = deliveryItem.getString("itemName");
                        String senderUID = deliveryItem.getString("senderUID");
                        String requesterUID = deliveryItem.getString("requsterUID");
                        String sender = deliveryItem.getString("sender");
                        String requester = deliveryItem.getString("requester");
                        Integer price = deliveryItem.getInt("price");

                        claimItem item1 = new claimItem(requester, sender, item, price, senderUID, requesterUID);
                        claimItems.add(item1);
                    }

                    ClaimAdapter claimAdapter = new ClaimAdapter(ClaimActivity.this, claimItems);
                    listView.setAdapter(claimAdapter);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                claimItem item = claimItems.get(position);
                Intent intent = new Intent(ClaimActivity.this, ManageClaimActivity.class);
                intent.putExtra("sender", item.getSender());
                intent.putExtra("requester", item.getRequester());
                intent.putExtra("item", item.getItemName());
                intent.putExtra("price", item.getPrice());
                intent.putExtra("senderUID", item.getSenderUID());
                intent.putExtra("requesterUID", item.getRequsterUID());

                startActivity(intent);
            }
        });

    }
}
