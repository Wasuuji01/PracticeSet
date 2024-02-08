package com.example.myadminpanel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    AppCompatButton categorybtn,productbtn,sliderbtn,orderbtn,logoutBtn;
    FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categorybtn = findViewById(R.id.categorybtn);
        productbtn = findViewById(R.id.productsbtn);
        sliderbtn = findViewById(R.id.sliderimagebtn);
        logoutBtn = findViewById(R.id.LogoutBtn);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (user == null){
            Intent intent = new Intent(MainActivity.this,Loginpage.class);
            startActivity(intent);
            finish();
        }
        else {

        }

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(MainActivity.this, "LogOut.... ", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Loginpage.class);
                startActivity(intent);
                finish();
            }
        });

        categorybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddCategory.class);
                startActivity(i);
            }
        });


        productbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DeleteProduct.class);
                startActivity(i);
            }
        });
//
//        sliderbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this,Upload_Multiple_Products_Images.class);
//                startActivity(i);
//
//            }
//        });

    }
}