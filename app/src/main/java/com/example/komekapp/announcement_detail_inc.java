package com.example.komekapp;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class announcement_detail_inc extends AppCompatActivity {
String login,timedate;
Boolean isAssigned,isClosed;

Integer previosPoints,previosCounterOfHelp,taskpoints;
Boolean isDone=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_detail_inc);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                timedate=null;
                Toast.makeText(announcement_detail_inc.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(announcement_detail_inc.this,enter_inclusive.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
                timedate=extras.getString("key");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
            timedate=(String)savedInstanceState.getSerializable("key");
        }

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.e("ITEM_Z",snapshot.child("inc_login").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        ListView listView=findViewById(R.id.andetinc_listview);
        ArrayList<String> result=new ArrayList<>();
        ArrayList<String> result_login_v=new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,result);
        DatabaseReference referenceCandidates=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate).child("candidates");
        referenceCandidates.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists()){
                    //snapshot = candidate
                    result.add(snapshot.child("name").getValue(String.class));
                    result_login_v.add(snapshot.child("login").getValue(String.class));
                    adapter.notifyDataSetChanged();
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
        listView.setAdapter(adapter);
        //if we click on item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String login_v = result_login_v.get(i);
                DatabaseReference referenceDelete=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate).child("candidates").child(login_v);
                referenceDelete.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //we need to check if assigned or closed
                        DatabaseReference referencecheckforAssignOrClose=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                        referencecheckforAssignOrClose.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot22) {
                                if(snapshot22.child("isAssigned").getValue(Boolean.class)==false&&snapshot22.child("isClosed").getValue(Boolean.class)==false){
                                    snapshot.getRef().removeValue();
                                    adapter.notifyDataSetChanged();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e(TAG, "onCancelled", error.toException());
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });
                DatabaseReference referenceDelete_listV=FirebaseDatabase.getInstance().getReference("taskslistV").child(login_v).child(login).child(timedate);
                referenceDelete_listV.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        snapshot.getRef().removeValue();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });
            }
        });


        Button btn_a=findViewById(R.id.adi_btn_a);
        Button btn_c=findViewById(R.id.adi_btn_c);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking is Assigned, is CLosed
                DatabaseReference reference_isAssignClosed=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                reference_isAssignClosed.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        isAssigned=snapshot.child("isAssigned").getValue(Boolean.class);
                        isClosed=snapshot.child("isClosed").getValue(Boolean.class);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });

                //now we need to check to make it Assigned with only one candidate left
                DatabaseReference reference_lastcandidate=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate).child("candidates");
                reference_lastcandidate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            if (snapshot.getChildrenCount()==1){
                                if(isAssigned==false && isClosed==false){
                                    Log.e("SUCCESS","SUCcESS");
                                    //must assign
                                    DatabaseReference reference_assign_candidate=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                                    reference_assign_candidate.child("isAssigned").setValue(Boolean.TRUE);
                                    reference_assign_candidate.child("isClosed").setValue(Boolean.FALSE);
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, "onCancelled", error.toException());
                    }
                });
            }
        });


        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            isDone=false;
                DatabaseReference referenceCLOSING=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                DatabaseReference referenceGetLoginOgVol=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate).child("candidates");
                referenceGetLoginOgVol.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        String lastVolunteerLogin=snapshot.child("login").getValue(String.class);
                        //got login of volunteer
                        Log.e("CC0",lastVolunteerLogin);
                        DatabaseReference referenceToGetInfoFromV=FirebaseDatabase.getInstance().getReference("volunteers").child(lastVolunteerLogin);
                        referenceToGetInfoFromV.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                previosPoints=snapshot.child("points").getValue(Integer.class);
                                previosCounterOfHelp=snapshot.child("counterOfHelp").getValue(Integer.class);
                                Log.e("CCC",String.valueOf(previosPoints));
                                Log.e("CCC",String.valueOf(previosCounterOfHelp));
                                //now we need to get amount of points needed to give V from task
                                DatabaseReference referenceToGetTaskPoints=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                                referenceToGetTaskPoints.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        taskpoints=snapshot.child("task_points").getValue(Integer.class);
                                        //checking is Assigned, is CLosed
                                        DatabaseReference reference_isAssignClosed=FirebaseDatabase.getInstance().getReference("tasks").child(login).child(timedate);
                                        reference_isAssignClosed.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                isAssigned=snapshot.child("isAssigned").getValue(Boolean.class);
                                                isClosed=snapshot.child("isClosed").getValue(Boolean.class);
                                                if(isAssigned==true && isClosed==false){
                                                    //now we need to assign to V
                                                    referenceCLOSING.child("isClosed").setValue(Boolean.TRUE);//closed
                                                    giving(lastVolunteerLogin,taskpoints,previosPoints,previosCounterOfHelp);
                                                }
                                            }
                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {
                                                Log.e(TAG, "onCancelled", error.toException());
                                            }
                                        });
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Log.e(TAG, "onCancelled", error.toException());
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Log.e(TAG, "onCancelled", error.toException());
                            }
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
            }
        });
    }
    public void giving(String lastlogin,Integer tpoints,Integer ppoints,Integer pcounterOfHelp){
        if(isDone==false){
            DatabaseReference referenceToGiveV=FirebaseDatabase.getInstance().getReference("volunteers").child(lastlogin);
            isDone=true;
            referenceToGiveV.child("points").setValue(tpoints+ppoints);
            referenceToGiveV.child("counterOfHelp").setValue(pcounterOfHelp+1);
        }

    }
}