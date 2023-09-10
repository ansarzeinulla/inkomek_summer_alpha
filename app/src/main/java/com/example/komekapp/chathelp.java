package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class chathelp extends AppCompatActivity {
String login, iv;
DatabaseReference reference;
    DatabaseReference reference2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chathelp);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                iv=null;
                Toast.makeText(chathelp.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(chathelp.this, MainActivity.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
                iv=extras.getString("iv");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
            iv=(String)savedInstanceState.getSerializable("iv");
        }
        ListView listView=findViewById(R.id.helplistview);
        ArrayList<String> arrayList=new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,arrayList);


        if(iv=="v"){
            reference=FirebaseDatabase.getInstance().getReference("chathelp").child("volunteers").child(login);
            Log.e("VVVV",iv);
            reference2=FirebaseDatabase.getInstance().getReference("chathelp").child("volunteers").child(login);

            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists()) {
                        Log.e("J",String.valueOf(snapshot.getChildrenCount()));
                        arrayList.add(snapshot.getValue(String.class));
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.e("J","EMPTY");
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    arrayList.remove(snapshot.getValue(String.class));
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            reference=FirebaseDatabase.getInstance().getReference("chathelp").child("inclusives").child(login);
            Log.e("VVVV",iv);
            reference2=FirebaseDatabase.getInstance().getReference("chathelp").child("inclusives").child(login);

            reference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    if(snapshot.exists()) {
                        Log.e("J",String.valueOf(snapshot.getChildrenCount()));
                        arrayList.add(snapshot.getValue(String.class));
                        adapter.notifyDataSetChanged();
                    }else{
                        Log.e("J","EMPTY");
                    }
                }
                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    arrayList.remove(snapshot.getValue(String.class));
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    adapter.notifyDataSetChanged();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        listView.setAdapter(adapter);

        Button btn=findViewById(R.id.chatbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView tv=findViewById(R.id.chattv);
                String tekst=tv.getText().toString();
                String z = login+" : "+tekst;

                String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                String timedate=currentDate+"_"+currentTime;
                reference2.child(timedate).setValue(z);
                Log.e("DDD","DDDD");
                Log.e("vDDD",iv);
                adapter.notifyDataSetChanged();
            }
        });
    }
}