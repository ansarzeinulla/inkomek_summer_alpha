package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcement_list_vol extends AppCompatActivity {
String login;
String list_login,list_timedate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_list_vol);

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(announcement_list_vol.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(announcement_list_vol.this,enter_volunteer.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }


        //ПОЛУЧИТЬ ИНКЛЮЗИВНЫЕ, ПОТОМ ПОЛУЧИТЬ ТАСК ЛИСТ (ТАЙМ ДАТЕ) ЗАТЕМ ТОЛЬКО ТАСКС СМОТРЕТЬ
        ListView listView=findViewById(R.id.anV_list);
        //ArrayList<String> arrayList_logins=new ArrayList<>();
        //ArrayList<String> arrayList_timedate=new ArrayList<>();
        ArrayList<String> result=new ArrayList<>();
        ArrayList<String> result_timedate=new ArrayList<>();
        ArrayList<String> result_inc_login=new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,result);
        DatabaseReference reference_forLogins = FirebaseDatabase.getInstance().getReference("listOfInclusives");
        reference_forLogins.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot1, @Nullable String previousChildName) {
                list_login=snapshot1.getValue(String.class);
                //gotted one login
                DatabaseReference reference_forTimedate=FirebaseDatabase.getInstance().getReference("tasks").child(list_login);
                reference_forTimedate.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot0, @Nullable String previousChildName) {
                        if(snapshot0.exists()){
//                            list_timedate=snapshot0.getValue(String.class);
//                            DatabaseReference reference_result=FirebaseDatabase.getInstance().getReference("tasks").child(list_login).child(list_timedate);
//                            reference_result.addChildEventListener(new ChildEventListener() {
//                                @Override
//                                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
//                                    String kk=snapshot.child("inc_name").getValue(String.class);
//                                    Log.e("R", String.valueOf(snapshot.getChildrenCount()));
//                                    result.add(kk);
//                                    Log.e("ZZZ",kk+"|");
//                                    Log.e("ZZ4",list_login);
//                                    Log.e("ZZ4",list_timedate);
//                                }
//                                @Override
//                                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
//                                @Override
//                                public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
//                                @Override
//                                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {}
//                            })
                            //so each snapshot is a task->login->timedate
                            if(snapshot0.child("isAssigned").getValue(Boolean.class)==false && snapshot0.child("isClosed").getValue(Boolean.class)==false) {
                                result.add(snapshot0.child("task_title").getValue(String.class));
                                result_timedate.add(snapshot0.child("timedate").getValue(String.class));
                                result_inc_login.add(snapshot0.child("inc_login").getValue(String.class));
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
                    @Override
                    public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        listView.setAdapter(adapter);
        //if we click on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(announcement_list_vol.this, announcement_apply_vol.class);
                intent.putExtra("login",login);
                intent.putExtra("inc_login", result_inc_login.get(i));
                intent.putExtra("timedate",result_timedate.get(i));
                startActivity(intent);
            }
        });
    }
}