package com.example.komekapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_more_info=findViewById(R.id.main_to_moreinfo);
        Button btn_register=findViewById(R.id.main_to_register);
        Button btn_enter=findViewById(R.id.main_to_enter);
        Switch aSwitch=findViewById(R.id.main_switch);

        btn_more_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this, more_info.class);
                startActivity(intent);
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( aSwitch.isChecked()){
                    Intent intent=new Intent(MainActivity.this, register_inclusive.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(MainActivity.this, register_volunteer.class);
                    startActivity(intent);
                }
            }
        });
        btn_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( aSwitch.isChecked()){
                    Intent intent=new Intent(MainActivity.this, enter_inclusive.class);
                    startActivity(intent);
                }else{
                    Intent intent=new Intent(MainActivity.this, enter_volunteer.class);
                    startActivity(intent);
                }
            }
        });
    }
}