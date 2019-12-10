package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;

public class AddClaimActivity extends AppCompatActivity {

    private EditText title, content;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_claim);

        title = findViewById(R.id.claim_title);
        content = findViewById(R.id.claim_content);
        submitButton = findViewById(R.id.button_submit);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sender = getIntent().getStringExtra("sender");
                String requester = getIntent().getStringExtra("requester");
                String senderUID = getIntent().getStringExtra("senderUID");
                String requesterUID = getIntent().getStringExtra("requesterUID");
                Integer price = getIntent().getIntExtra("price", 1);
                String item = getIntent().getStringExtra("item");

                claimItem sendClaimItem = new claimItem(requester, sender, item, price, senderUID, requesterUID);
                sendClaimItem.setTitle(title.getText().toString());
                sendClaimItem.setContent(content.getText().toString());
                FirebaseDatabase.getInstance().getReference().child("claims").push().setValue(sendClaimItem);

                Toast.makeText(AddClaimActivity.this, "Claim successfully added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddClaimActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
