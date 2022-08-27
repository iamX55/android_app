package com.example.mysplashscreen;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText text1,text2;
    private TextView text3;
    private Button button0;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1=findViewById(R.id.text_1_id);
        text2=findViewById(R.id.text_2_id);
        text3=findViewById(R.id.text_3_id);
        button0=findViewById(R.id.button_0_id);
        progressBar=findViewById(R.id.progress_2_id);
        button0.setOnClickListener(this);
        text3.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_0_id:
                user_login();
                break;
            case R.id.text_3_id:

               Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
               startActivity(intent);
                break;

        }
    }

    private void user_login() {
        String email=text1.getText().toString().trim();
        String password1=text2.getText().toString().trim();

        if(email.isEmpty()){
            text1.setError("Enter an email");
            text1.requestFocus();
            return;
        }
        if(password1.isEmpty()){
            text2.setError("Enter an password");
            text2.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            text1.setError("Invalid Email");
            text1.requestFocus();
            return;
        }
        if(password1.length()<6 ){
            text2.setError("Password should be minimum 6 character ");
            text2.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {   progressBar.setVisibility(View.GONE);
                    finish();
                    Intent intent=new Intent(getApplicationContext(),SecondActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),"log in unsuccessful",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}