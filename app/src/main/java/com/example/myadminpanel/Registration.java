package com.example.myadminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registration extends AppCompatActivity {

    EditText username,Password,Confirmpassword;
    Button register;
    CheckBox showpassword;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if (currentuser != null){
            Intent next = new Intent(Registration.this,MainActivity.class);
            startActivity(next);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth=FirebaseAuth.getInstance();

        username = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        Confirmpassword = findViewById(R.id.confirmpassword);
        showpassword = findViewById(R.id.showpassword);
        register = findViewById(R.id.register);
        progressBar = findViewById(R.id.progressbar);


        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean a) {

                if (a) {
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    Confirmpassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    Confirmpassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password,confirmpassword;
                email=String.valueOf(username.getText());
                password = String.valueOf(Password.getText());
                confirmpassword =String.valueOf(Confirmpassword.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Registration.this, "Please enter email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Registration.this, "Please enter password..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)){
                    Toast.makeText(Registration.this, "Please enter Confirm password..", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email,confirmpassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){
                                    Toast.makeText(Registration.this, "Register successfully...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Registration.this,Loginpage.class);
                                    startActivity(intent);
                                }
                                else {
                                    Toast.makeText(Registration.this, "Register failed...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}