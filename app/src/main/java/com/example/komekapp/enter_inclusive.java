package com.example.komekapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class enter_inclusive extends AppCompatActivity {
    String login,password;

    String existingpassword;
    Boolean isApproved,isBlocked, isBanned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_inclusive);
        Button btn=findViewById(R.id.signIbutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText enterLogin=findViewById(R.id.signI1);
                EditText enterPassword=findViewById(R.id.signI2);
                login=enterLogin.getText().toString();
                password=enterPassword.getText().toString();

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                DatabaseReference loginsRef = rootRef.child("listOfInclusives").child(login);
                ValueEventListener valueEventListener=new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshotR) {
                        if(!snapshotR.exists()) {//there is NO such login
                            Toast.makeText(enter_inclusive.this, "No user login", Toast.LENGTH_LONG).show();
                        }else{
                            DatabaseReference someoneREF=FirebaseDatabase.getInstance().getReference("inclusives").child(login);
                            someoneREF.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    existingpassword = snapshot.child("password").getValue(String.class);
                                    isApproved=snapshot.child("isApproved").getValue(Boolean.class);
                                    isBlocked=snapshot.child("isBlocked").getValue(Boolean.class);
                                    isBanned=snapshot.child("isBanned").getValue(Boolean.class);

                                    if(existingpassword.equals(password)){//cheking the entered and existing passwords
                                        //correct password but need to check approve, banned and blocked
                                        if(isApproved==true){//approved :)
                                            if(isBlocked==true){
                                                Toast.makeText(enter_inclusive.this, "Your are currently blocked for sometime", Toast.LENGTH_LONG).show();
                                            }else{//not blocked, but need to check ban
                                                if(isBanned==true){
                                                    Toast.makeText(enter_inclusive.this, "Your are currently BANNED", Toast.LENGTH_LONG).show();
                                                }else{
                                                    Intent intent=new Intent(enter_inclusive.this,menuInclusive.class);
                                                    intent.putExtra("login",login);
                                                    startActivity(intent);
                                                }
                                            }
                                        }else{//not approved :(
                                            Toast.makeText(enter_inclusive.this, "you are not still approved by admins, plz wait", Toast.LENGTH_LONG).show();
                                        }
                                    }else{// wrong password
                                        Toast.makeText(enter_inclusive.this, "Wrong password", Toast.LENGTH_LONG).show();
                                    }

                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.d(TAG, error.getMessage());
                    }
                };
                loginsRef.addListenerForSingleValueEvent(valueEventListener);
            }
        });
    }
}