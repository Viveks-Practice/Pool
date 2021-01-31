package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginPage extends AppCompatActivity implements  View.OnClickListener{

    private Button button4Login;
    FirebaseAuth mAuth;
    EditText etEmailLogin;
    EditText etPasswordLogin;
    ProgressBar progressBarLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        mAuth = FirebaseAuth.getInstance();

        etEmailLogin = (EditText) findViewById(R.id.emailLogin);
        etPasswordLogin = (EditText) findViewById(R.id.passwordLogin);
        progressBarLogin = (ProgressBar) findViewById(R.id.progressBarLogin);

        button4Login = ( Button )  findViewById(R.id.buttonLogin);
        button4Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                buttonLogin();
            }
        });
    }

    public void userLogin() {
        String emailLogin = etEmailLogin.getText().toString().trim();
        String passwordLogin = etPasswordLogin.getText().toString().trim();

        if(emailLogin.isEmpty()){
            etEmailLogin.setError("Email is required");
            etEmailLogin.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(emailLogin).matches()) {
            etEmailLogin.setError("Please enter a valid email address");
            etEmailLogin.requestFocus();
            return;
        }

        if (passwordLogin.isEmpty()){
            etPasswordLogin.setError("Password is required");
            etPasswordLogin.requestFocus();
            return;
        }

        progressBarLogin.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(emailLogin, passwordLogin) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBarLogin.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    toMain();
                }
                else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    public void buttonLogin() {
        userLogin();

    }

    public void toMain() {
        Intent intent = new Intent(this, tabbedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }


    public void signUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

    }
}

