package com.example.cs350_kaisend;

import androidx.appcompat.app.AppCompatActivity;

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

        mainTextView = findViewById(R.id.helloWorldText);
        ourButton = findViewById(R.id.ourButton);

        ourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainTextView.setText("Android is horrible");
            }
        });


    }
}
