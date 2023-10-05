package com.example.komekapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class shoplist extends AppCompatActivity {
    String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoplist);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(shoplist.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(shoplist.this,menuVolunteer.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }

        ListView listView=findViewById(R.id.shoplistview);
        ArrayList<String> shoplist=new ArrayList<>();
        ArrayList<String> shoplist22=new ArrayList<>();
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,shoplist);


        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("shoplist");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String product=snapshot.getValue(String.class);
                DatabaseReference reference22= FirebaseDatabase.getInstance().getReference("shopdetail").child(product);
                reference22.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String address=snapshot.child("Address").getValue(String.class);
                        String company=snapshot.child("company").getValue(String.class);
                        String companylogin=snapshot.child("companylogin").getValue(String.class);
                        String name=snapshot.child("name").getValue(String.class);
                        Integer cost=snapshot.child("cost").getValue(Integer.class);
                        shoplist.add(name+" | "+company+" : "+cost.toString());
                        shoplist22.add(name);
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(shoplist.this, shopdetail.class);
                intent.putExtra("name",shoplist22.get(i));
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
    }
}