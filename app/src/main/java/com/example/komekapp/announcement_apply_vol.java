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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class announcement_apply_vol extends AppCompatActivity {
String login,inc_login, timedate;
Integer numOfCandidates;
    String name;
    String surname;
    String fathername;
    String phone;
    String detail;
    Boolean isAnonymous;
    Integer counterOfHelp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_apply_vol);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(announcement_apply_vol.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(announcement_apply_vol.this,enter_volunteer.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
                inc_login=extras.getString("inc_login");
                timedate=extras.getString("timedate");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
            inc_login=(String)savedInstanceState.getSerializable(inc_login);
            timedate=(String)savedInstanceState.getSerializable("timedate");
        }
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("tasks").child(inc_login).child(timedate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("VJ",snapshot.child("timedate").getValue(String.class));
                numOfCandidates=snapshot.child("numOfVolunteers").getValue(Integer.class);

                TextView annav_tv=findViewById(R.id.annav_tv);
                annav_tv.setText(snapshot.child("tasl_title").getValue(String.class)+snapshot.child("task_detail").getValue(String.class)+snapshot.child("task_points").getValue(Integer.class).toString());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        DatabaseReference referenceVOL=FirebaseDatabase.getInstance().getReference("volunteers").child(login);
        referenceVOL.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name=snapshot.child("name").getValue(String.class);
                surname=snapshot.child("surname").getValue(String.class);
                fathername=snapshot.child("fathername").getValue(String.class);
                phone=snapshot.child("phone").getValue(String.class);
                detail=snapshot.child("detail").getValue(String.class);
                isAnonymous=snapshot.child("isAnonymous").getValue(Boolean.class);
                counterOfHelp=snapshot.child("counterOfHelp").getValue(Integer.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        Button btn=findViewById(R.id.annav_btn);//applying as candidate
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                class_candidate candidate=new class_candidate(login,name,surname,fathername,phone,detail,isAnonymous,counterOfHelp);
                reference.child("candidates").child(login).setValue(candidate);
                reference.child("numOfVolunteers").setValue(numOfCandidates+1); //one more candidate
                DatabaseReference referenceforHistoryOfVolunteer=FirebaseDatabase.getInstance().getReference("taskslistV").child(login).child(inc_login).child(timedate);
                referenceforHistoryOfVolunteer.setValue(timedate);

            }
        });
    }
}