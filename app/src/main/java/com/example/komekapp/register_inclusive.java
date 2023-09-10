package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register_inclusive extends AppCompatActivity {
    String login,name,initial,phone,email,password;
    String detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_inclusive);

        EditText eLogin,eName,eInitial,ePhone,eEmail,ePassword;
        eLogin=findViewById(R.id.irLogin);
        eName=findViewById(R.id.irName);
        eInitial=findViewById(R.id.irInitial);
        ePhone=findViewById(R.id.irPhone);
        eEmail=findViewById(R.id.irEmail);
        ePassword=findViewById(R.id.irPassword);
        TextInputLayout eDetailLayout=findViewById(R.id.irDetailLayout);

        Button btn=findViewById(R.id.irBtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login=eLogin.getText().toString();
                name=eName.getText().toString();
                initial=eInitial.getText().toString();
                phone=ePhone.getText().toString();
                email=eEmail.getText().toString();
                password=ePassword.getText().toString();
                detail=eDetailLayout.getEditText().getText().toString();
                Boolean canregister=true;
                //cheking lenthg of inputted
                ///////////code
                //end of checking
                if( canregister){
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference loginsRef = rootRef.child("listOfAllLogins").child(login);
                    ValueEventListener valueEventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()) {//checking is the same login is taken from ALL users
                                //now we can safely register
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("inclusives").child(login);
                                class_inclusive human=new class_inclusive(login,name,initial,phone,email,password,detail,false,0,0,false,false);
                                myRef.setValue(human);//adding inclusive to inclusive
                                DatabaseReference Ref2 = database.getReference("listOfAllLogins").child(login);
                                Ref2.setValue(login);//adding to ALL users logins list
                                DatabaseReference Ref3 = database.getReference("listOfInclusives").child(login);
                                Ref3.setValue(login);//adding to inclusive logins list
                                //ansar
                            }
                            else{//no, the login is taken
                                Toast.makeText(register_inclusive.this, "Same login already taken", Toast.LENGTH_LONG).show();
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    };
                    loginsRef.addListenerForSingleValueEvent(valueEventListener);
                }
            }
        });
    }
}