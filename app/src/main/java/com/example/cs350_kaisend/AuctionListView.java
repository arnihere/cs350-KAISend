package com.example.cs350_kaisend;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class AuctionListView extends AppCompatActivity {
    private static final String TAG = AuctionListView.class.getSimpleName();
    private DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
    private LinkedList<Auction> auctions;

    private RecyclerView mRecyclerView;
    private AuctionListAdapter mAdapter;

    private EditText mSearchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_list_view);
        mRecyclerView = findViewById(R.id.recyclerview);
        mSearchBox = findViewById(R.id.search_box);
        DatabaseReference aucRef = rootRef.child("auctions");
        auctions = new LinkedList<>();
        mAdapter = new AuctionListAdapter(this, auctions);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        aucRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if (dataSnapshot.getValue(Auction.class).isActive()){
                    auctions.add(dataSnapshot.getValue(Auction.class));
                    Collections.sort(auctions, new Comparator<Auction>(){
                        @Override
                        public int compare(Auction o1, Auction o2) {
                            return Integer.parseInt(o1.getFee()) < Integer.parseInt(o2.getFee()) ? -1 : (Integer.parseInt(o1.getFee()) > Integer.parseInt(o2.getFee())) ? 0 : 1;
                        }
                    });
                    Log.d(TAG, auctions.toString());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d(TAG, String.valueOf(dataSnapshot.getValue(Auction.class).isActive()));
                if (!dataSnapshot.getValue(Auction.class).isActive()){
                    for (Auction auction : auctions){
                        if (dataSnapshot.getValue(Auction.class).getName() == auction.getName()){
                            auctions.remove(auction);
                            mAdapter.notifyDataSetChanged();
                            break;
                        }
                    }
                }else{
                    auctions.add(dataSnapshot.getValue(Auction.class));
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

    }
}
