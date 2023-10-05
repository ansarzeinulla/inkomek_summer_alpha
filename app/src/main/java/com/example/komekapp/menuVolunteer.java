package com.example.komekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class menuVolunteer extends AppCompatActivity {
String login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_volunteer);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras==null) {
                login=null;
                Toast.makeText(menuVolunteer.this, "ERROR PLS GO BACK", Toast.LENGTH_LONG).show();
                Intent intent=new Intent(menuVolunteer.this,enter_volunteer.class);
                startActivity(intent);
            } else {
                login=extras.getString("login");
            }
        } else {
            login=(String)savedInstanceState.getSerializable("login");
        }
        Button b1=findViewById(R.id.menuV1);
        Button b2=findViewById(R.id.menuV2);
        Button b3=findViewById(R.id.menuV3);
        Button b4=findViewById(R.id.menuV4);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuVolunteer.this, cabinet_volunteer.class);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuVolunteer.this, chathelp.class);
                intent.putExtra("iv","v");
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuVolunteer.this, announcement_list_vol.class);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(menuVolunteer.this, shoplist.class);
                intent.putExtra("login",login);
                startActivity(intent);
            }
        });
    }
}