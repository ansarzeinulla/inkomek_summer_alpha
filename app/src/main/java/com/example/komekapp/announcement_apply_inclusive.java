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

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class announcement_apply_inclusive extends AppCompatActivity {
String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_apply_inclusive);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(announcement_apply_inclusive.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(announcement_apply_inclusive.this,enter_inclusive.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }

        TextInputLayout a1=findViewById(R.id.annount_ai1);
        TextInputLayout a2=findViewById(R.id.annount_ai2);
        TextInputLayout a3=findViewById(R.id.annount_ai3);
        Button btn=findViewById(R.id.annount_ai_btn);
        String task_title=a1.getEditText().getText().toString();
        String task_detail=a2.getEditText().getText().toString();
        Integer task_point=Integer.valueOf(a3.getEditText().getText().toString());
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference human= FirebaseDatabase.getInstance().getReference("inclusives").child(login);
                human.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String inc_name = snapshot.child("name").getValue(String.class);
                        String inc_initial = snapshot.child("initial").getValue(String.class);
                        String inc_detail = snapshot.child("detail").getValue(String.class);
                        Integer inc_point = snapshot.child("points").getValue(Integer.class);


                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                        String timedate=currentDate+"_"+currentTime;
                        class_task announcement=new class_task(timedate,login,inc_name,inc_initial,inc_detail,task_title,task_detail,false,false,task_point,0);
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("tasks").child(login).child(timedate);
                        myRef.setValue(announcement);//added task to DB
                        DatabaseReference myRef22 = database.getReference("taskslist").child(login).child(timedate);
                        myRef22.setValue(timedate);//added task into tasklist
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });
    }
}