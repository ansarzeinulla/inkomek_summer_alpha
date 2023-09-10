package com.example.komekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menuInclusive extends AppCompatActivity {
    String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inclusive);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(menuInclusive.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(menuInclusive.this,enter_inclusive.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }
        Log.e("MENU INCLUSIVE", login);
        //login is gotted
        Button b1=findViewById(R.id.menuI1);
        Button b2=findViewById(R.id.menuI2);
        Button b3=findViewById(R.id.menuI3);
        Button b4=findViewById(R.id.menuI4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuInclusive.this, cabinet_inclusive.class);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuInclusive.this, chathelp.class);
                intent.putExtra("login",login);
                intent.putExtra("iv","i");
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuInclusive.this, announcement_apply_inclusive.class);
                intent.putExtra("iv","i");
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuInclusive.this, announcement_my_inclusive.class);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
    }
}