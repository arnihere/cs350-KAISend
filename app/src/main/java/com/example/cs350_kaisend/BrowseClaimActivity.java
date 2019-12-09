package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class BrowseClaimActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_claim);

        database = FirebaseDatabase.getInstance();
        final DatabaseReference userRef = database.getReference().child("users");
        final DatabaseReference deliveryRef = database.getReference().child("deliveries");
        listView = findViewById(R.id.cliamListview);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final JSONObject userArray = new JSONObject((HashMap)dataSnapshot.getValue());
                deliveryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        try{
                            ArrayList<claimItem> claimItems = new ArrayList<claimItem>();
                            JSONObject deliveriesObject = new JSONObject((HashMap)dataSnapshot.getValue());
                            JSONArray deliveriesArray = deliveriesObject.toJSONArray(deliveriesObject.names());
                            for (int i=0; i<deliveriesArray.length(); i++){
                                JSONObject deliveryItem = (JSONObject)deliveriesArray.get(i);

                                String item = deliveryItem.getJSONObject("item").getString("name");
                                String senderUID = deliveryItem.getString("sender");
                                String requesterUID = deliveryItem.getString("requester");
                                String sender = userArray.getJSONObject(senderUID).getString("userName");
                                String requester = userArray.getJSONObject(requesterUID).getString("userName");
                                Integer price = deliveryItem.getJSONObject("item").getInt("price");

                                claimItem item1 = new claimItem(requester, sender, item, price);
                                claimItems.add(item1);
                            }

                            ClaimAdapter claimAdapter = new ClaimAdapter(BrowseClaimActivity.this, claimItems);
                            listView.setAdapter(claimAdapter);
                        }catch(JSONException e){
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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }
}
