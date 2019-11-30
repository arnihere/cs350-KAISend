package com.example.cs350_kaisend;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityRegister extends AppCompatActivity {
    public EditText emailId, passwd, userNam, phoneNumbe, kakaoTalkI;
    Button btnSignUp;
    TextView signIn;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        emailId = findViewById(R.id.ETemail);
        passwd = findViewById(R.id.ETpassword);
        userNam = findViewById(R.id.userName);
        phoneNumbe = findViewById(R.id.phoneNumber);
        kakaoTalkI = findViewById(R.id.kakaoTalkId);
        btnSignUp = findViewById(R.id.btnSignUp);
        signIn = findViewById(R.id.TVSignIn);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String emailID = emailId.getText().toString();
                final String paswd = passwd.getText().toString();
                final String Name = userNam.getText().toString();
                final String phoneN = phoneNumbe.getText().toString();
                final String kakaoId = kakaoTalkI.getText().toString();
                if (emailID.isEmpty()) {
                    emailId.setError("Provide your Email first!");
                    emailId.requestFocus();
                } else if (paswd.isEmpty()) {
                    passwd.setError("Set your password");
                    passwd.requestFocus();
                } else if (Name.isEmpty()){
                    userNam.setError("Provide user name first!");
                    userNam.requestFocus();
                } else if (phoneN.isEmpty()){
                    phoneNumbe.setError("Provide phone number firse!");
                    phoneNumbe.requestFocus();
                } else if (kakaoId.isEmpty()){
                    kakaoTalkI.setError("provide KakaoTalkID first!");
                    kakaoTalkI.requestFocus();
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(ActivityRegister.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(ActivityRegister.this.getApplicationContext(),
                                        "SignUp unsuccessful: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                String uid = user.getUid();
                                DatabaseReference userRef = database.getReference("users/"+uid);
                                HashMap<String, String> userInfo = new HashMap<>();
                                userInfo.put("userName",Name);
                                userInfo.put("email",emailID);
                                userInfo.put("password",paswd);
                                userInfo.put("phoneNumber",phoneN);
                                userInfo.put("kakaoTalkID",kakaoId);
                                userRef.setValue(userInfo);
                                userRef.child("sends").setValue(0);
                                userRef.child("requests").setValue(0);
                                userRef.child("penaltyPoints").setValue(0);
                                userRef.child("banned").setValue(false);
                                startActivity(new Intent(ActivityRegister.this, UserActivity.class));
                            }
                        }
                    });
                }
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(ActivityRegister.this, MainActivity.class);
                startActivity(I);
            }
        });
    }
}
