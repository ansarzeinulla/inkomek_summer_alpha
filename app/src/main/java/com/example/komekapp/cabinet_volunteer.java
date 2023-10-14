package com.example.komekapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class cabinet_volunteer extends AppCompatActivity {
    String login;
    String oldname,oldsurname,oldpassword,oldemail,oldphone,olddetail;
    Integer oldpoint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cabinet_volunteer);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(cabinet_volunteer.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(cabinet_volunteer.this,enter_inclusive.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }

        TextView c1=findViewById(R.id.cabV1);
        TextView c2=findViewById(R.id.cabV2);
        TextView c3=findViewById(R.id.cabV3);
//        TextView c3=findViewById(R.id.cabI3);
//        TextView c4=findViewById(R.id.cabI4);
//        TextView c5=findViewById(R.id.cabI5);
//        TextView c6=findViewById(R.id.cabI6);
//        EditText e0=findViewById(R.id.cabI20);
//        EditText e1=findViewById(R.id.cabI21);

        TextInputLayout e0=findViewById(R.id.cabV20);
        TextInputLayout e1=findViewById(R.id.cabV21);
        TextInputLayout e2=findViewById(R.id.cabV22);
        TextInputLayout e3=findViewById(R.id.cabV23);
//        Button btn1=findViewById(R.id.cabIbutton1);
        Button btn2=findViewById(R.id.cabVbutton2);

        DatabaseReference human= FirebaseDatabase.getInstance().getReference("volunteers").child(login);
        //getting values and assigning to TextViews and EditTexts
        human.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                oldname = snapshot.child("name").getValue(String.class);
                oldsurname= snapshot.child("surname").getValue(String.class);
                oldpassword = snapshot.child("password").getValue(String.class);
                oldemail = snapshot.child("email").getValue(String.class);
                oldphone = snapshot.child("phone").getValue(String.class);
                olddetail = snapshot.child("detail").getValue(String.class);
                oldpoint = snapshot.child("points").getValue(Integer.class);
                c1.setText("Login : "+login);
                c2.setText("Points : "+oldpoint.toString());
                c3.setText("Helped : "+snapshot.child("counterOfHelp").getValue(Integer.class).toString());
//                c3.setText("Name");
//                c4.setText("Initial");
//                c5.setText("Email");
//                c6.setText("Phone");
                e0.getEditText().setText(oldname);
                e1.getEditText().setText(oldsurname);
                e2.getEditText().setText(oldemail);
                e3.getEditText().setText(oldphone);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

//        btn1.setOnClickListener(new View.OnClickListener() {//go back
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(cabinet_inclusive.this,menuInclusive.class);
//                intent.putExtra("login",login);
//                startActivity(intent);
//            }
//        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change value
                DatabaseReference newhuman=FirebaseDatabase.getInstance().getReference("volunteers").child(login);
                newhuman.child("name").setValue(e0.getEditText().getText().toString());
                newhuman.child("surname").setValue(e1.getEditText().getText().toString());
                newhuman.child("email").setValue(e2.getEditText().getText().toString());
                newhuman.child("phone").setValue(e3.getEditText().getText().toString());
            }
        });
    }
}