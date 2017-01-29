package com.example.hassan.supervisorapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btn , createEmp , msgBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.empListBtn);
        createEmp = (Button)findViewById(R.id.createEmp);
        msgBtn = (Button)findViewById(R.id.msgBtn);

        btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this,EmployeesList.class);
                        startActivity(i);
                    }
                }
        );

        createEmp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this,CreateEmoloyee.class);
                        startActivity(i);
                    }
                }
        );

        msgBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(MainActivity.this,MessageActivity.class);
                        startActivity(i);
                    }
                }
        );
    }
}
