package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class shopdetail extends AppCompatActivity {
String login, name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopdetail);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                name=null;
                Toast.makeText(shopdetail.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(shopdetail.this,menuVolunteer.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
                name=extras.getString("name");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
            name=(String)savedInstanceState.getSerializable("name");
        }

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("shopdetail").child(name);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String address=snapshot.child("Address").getValue(String.class);
                String company=snapshot.child("company").getValue(String.class);
                String companylogin=snapshot.child("companylogin").getValue(String.class);
                String name=snapshot.child("name").getValue(String.class);
                Integer cost=snapshot.child("cost").getValue(Integer.class);
                String code=snapshot.child("code").getValue(String.class);
                TextView tv1=findViewById(R.id.shopdetailtv1);
                Button buybtn=findViewById(R.id.shopdetailbtnbuy);
                buybtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference referenceHuman=FirebaseDatabase.getInstance().getReference("volunteers").child(login);
                        referenceHuman.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot2) {
                                Integer havingpoints=snapshot2.child("points").getValue(Integer.class);
                                Log.e("havingPOINTS",havingpoints.toString());
                                Log.e("code",code);
                                Log.e("cost",cost.toString());
                                if(havingpoints>=cost && code==tv1.getText().toString()){
                                    referenceHuman.child("points").setValue(havingpoints-cost);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}