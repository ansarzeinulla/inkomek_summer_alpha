package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class register_volunteer extends AppCompatActivity {
    String login,name,surname,fathername,phone,email,password;
    String detail;
    Boolean isAnonymous;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_volunteer);

        EditText eLogin,eName,eSurname,eFathername,ePhone,eEmail,ePassword;
        //TextInputEditText eDetail=findViewById(R.id.vrDetail);
        Switch eSwitch=findViewById(R.id.vrSwitch);
        eLogin=findViewById(R.id.vrLogin);
        eName=findViewById(R.id.vrName);
        eSurname=findViewById(R.id.vrSurname);
        eFathername=findViewById(R.id.vrFathername);
        ePhone=findViewById(R.id.vrPhone);
        eEmail=findViewById(R.id.vrEmail);
        ePassword=findViewById(R.id.vrPassword);
        TextInputLayout eDetailLayout=findViewById(R.id.vrDetailLayout);


        Button btn=findViewById(R.id.vrButton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login=eLogin.getText().toString();
                name=eName.getText().toString();
                surname=eSurname.getText().toString();
                fathername=eFathername.getText().toString();
                phone=ePhone.getText().toString();
                email=eEmail.getText().toString();
                password=ePassword.getText().toString();
                isAnonymous=eSwitch.isChecked();
                detail=eDetailLayout.getEditText().getText().toString();
                Boolean canregister=true;
//                if(login.length()<5 || login.length()>15){
//                    Toast.makeText(register_volunteer.this, "login length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(name.length()<2 || name.length()>15){
//                    Toast.makeText(register_volunteer.this, "name length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(surname.length()<2 || surname.length()>25){
//                    Toast.makeText(register_volunteer.this, "surname length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(fathername.length()<5 || fathername.length()>25){
//                    Toast.makeText(register_volunteer.this, "fathername length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(phone.length()!=11){
//                    Toast.makeText(register_volunteer.this, "phone length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(email.length()<3 || email.length()>30){
//                    Toast.makeText(register_volunteer.this, "email length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
//                if(password.length()<6 || password.length()>30){
//                    Toast.makeText(register_volunteer.this, "password length", Toast.LENGTH_LONG).show();
//                    canregister=false;
//                }
                if( canregister){
                    DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference loginsRef = rootRef.child("listOfAllLogins").child(login);
                    ValueEventListener valueEventListener=new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()) {//checking is the same login is taken from ALL users
                                //now we can safely register
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("volunteers").child(login);
                                class_volunteer human=new class_volunteer(login,name,surname,fathername,phone,email,password,detail,isAnonymous,0,false,false,0);
                                myRef.setValue(human);//adding volunteer to volunteers
                                DatabaseReference Ref2 = database.getReference("listOfAllLogins").child(login);
                                Ref2.setValue(login);//adding to ALL users logins list
                                DatabaseReference Ref3 = database.getReference("listOfVolunteers").child(login);
                                Ref3.setValue(login);//adding to volunteer logins list
                            }
                            else{//no, the login is taken
                                Toast.makeText(register_volunteer.this, "Same login already taken", Toast.LENGTH_LONG).show();
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