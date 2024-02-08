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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Loginpage extends AppCompatActivity {

    TextView SignupBtn;
    EditText username,Password;
    CheckBox showpassword;
    Button loginbtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser = mAuth.getCurrentUser();
        if (currentuser != null){
            Intent next = new Intent(Loginpage.this,MainActivity.class);
            startActivity(next);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);

        mAuth = FirebaseAuth.getInstance();
        Password = findViewById(R.id.password);
        username = findViewById(R.id.email);
        showpassword = findViewById(R.id.showpassword);
        loginbtn = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressbar);
        SignupBtn = findViewById(R.id.Signup);

        SignupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(Loginpage.this,Registration.class);
                startActivity(go);
                finish();
            }
        });


        showpassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean a) {

                if (a) {
                    Password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    Password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email,password;
                email=String.valueOf(username.getText());
                password = String.valueOf(Password.getText());

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(Loginpage.this, "Please enter email..", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(Loginpage.this, "Please enter password..", Toast.LENGTH_SHORT).show();
                }

                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){
                                    Toast.makeText(Loginpage.this, "Login Successfully...", Toast.LENGTH_SHORT).show();
                                    Intent go = new Intent(Loginpage.this,MainActivity.class);
                                    startActivity(go);
                                    finish();
                                }
                                else {
                                    Toast.makeText(Loginpage.this, "Login failed...", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

    }
}