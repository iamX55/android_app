package com.example.mysplashscreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText text4,text5,text6;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        text4=findViewById(R.id.text_4_id);
        text5=findViewById(R.id.text_5_id);
        text6=findViewById(R.id.text_6_id);
        Button button1 = findViewById(R.id.button_1_id);
        progressBar=findViewById(R.id.progress_1_id);
        button1.setOnClickListener(this);
        mAuth=FirebaseAuth.getInstance();


    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.button_1_id){
            userregister();
        }
    }

    private void userregister() {
        String email=text4.getText().toString().trim();
        String password1=text5.getText().toString().trim();
        String password2=text6.getText().toString().trim();
        if(email.isEmpty()){
            text4.setError("Enter an email");
            text4.requestFocus();
            return;
        }
        if(password1.isEmpty()){
            text5.setError("Enter an password");
            text5.requestFocus();
            return;
        }
        if(password2.isEmpty()){
            text6.setError("Enter an email");
            text6.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            text4.setError("Invalid Email");
            text4.requestFocus();
            return;
        }
        if(password1.length()<6 || password2.length()<6){
            text5.setError("Password should be minimum 6 character ");
            text5.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(),"register is successful",Toast.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    progressBar.setVisibility(View.INVISIBLE);
                   if(task.getException() instanceof FirebaseAuthUserCollisionException){
                       Toast.makeText(getApplicationContext(),"User already registered",Toast.LENGTH_SHORT).show();
                   }else{
                       Toast.makeText(getApplicationContext(),"error :"+ Objects.requireNonNull(task.getException()).getMessage(),Toast.LENGTH_SHORT).show();
                   }
                }
            }
        });


    }
}