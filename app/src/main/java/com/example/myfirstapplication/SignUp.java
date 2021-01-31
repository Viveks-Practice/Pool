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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private Button button4SignUp;
    EditText etEmailSignUp;
    EditText etPasswordSignUp;
    ProgressBar progressBar;


    // Initialize Firebase Auth
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        button4SignUp = ( Button ) findViewById(R.id.buttonSignUp);
        button4SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                registerUser();
            }
        });

        etEmailSignUp = (EditText) findViewById(R.id.emailSignUp);
        etPasswordSignUp = (EditText) findViewById(R.id.passwordSignUp);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

    }

    public void login(View view) {
        Intent intent = new Intent(this, LoginPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    public void registerUser() {
        String email = etEmailSignUp.getText().toString().trim();
        String password = etPasswordSignUp.getText().toString().trim();

        if(email.isEmpty()){
            etEmailSignUp.setError("Email is required");
            etEmailSignUp.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailSignUp.setError("Please enter a valid email address");
            etEmailSignUp.requestFocus();
            return;
        }

        if (password.isEmpty()){
            etPasswordSignUp.setError("Password is required");
            etPasswordSignUp.requestFocus();
            return;
        }

        if(password.length() < 6){
            etPasswordSignUp.setError("Minimum length of password should be 6");
            etPasswordSignUp.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password) .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()) {

                    //Create a user object for this newly signed up user
                    userEmail = mAuth.getCurrentUser().getEmail();

/*                    (String name, String carMake, String carModel, double carYear, float rating,
                    float experience, double lat, double lng, Boolean init, String driverDestination,
                    double driverStray, double driverStrayDest, double driverStrayStart,
                    double driverStrayRouteDest, double driverStrayRouteStart, double driverSeats,
                    double driverMinTip, double driverMaxTip, String riderDestination,
                    double riderStrayDest, double riderStrayStart)*/


                    User user = new User("", "","","",0, 0,
                            0, 0 , 0, false);
                    db.collection("Users").document(userEmail).set(user);

                    toInitProfile();
                    Toast.makeText(getApplicationContext(), "User Registered Successfully", Toast.LENGTH_SHORT).show();
                }
                else {

                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "This email address is already registered", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    public void toInitProfile() {
        Intent intent = new Intent(SignUp.this, profileSetup.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
