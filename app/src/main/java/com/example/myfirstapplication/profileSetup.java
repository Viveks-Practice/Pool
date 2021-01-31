package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class profileSetup extends AppCompatActivity {


    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_CARMAKE = "carMake";
    private static final String KEY_CARMODEL = "carModel";
    private static final String KEY_YEAR = "carYear";
    private static final String KEY_INIT = "init";


    private EditText setupEditTextFirstName;
    private EditText setupEditTextLastName;
    private EditText setupEditTextCarMake;
    private EditText setupEditTextCarModel;
    private EditText setupEditTextCarYear;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setup);

        setupEditTextFirstName = findViewById(R.id.setupFirstNameText);
        setupEditTextLastName = findViewById(R.id.setupLastNameText);
        setupEditTextCarMake =  findViewById(R.id.setupMakeText);
        setupEditTextCarModel =  findViewById(R.id.setupModelText);
        setupEditTextCarYear =  findViewById(R.id.setupYearText);
    }

    public void openMain() {
        Intent intent = new Intent(this, tabbedActivity.class);
        startActivity(intent);
    }

    public void initializeProfile(View view){
        String firstName = setupEditTextFirstName.getText().toString();
        String lastName = setupEditTextLastName.getText().toString();
        String make = setupEditTextCarMake.getText().toString();
        String model = setupEditTextCarModel.getText().toString();
        String year = setupEditTextCarYear.getText().toString();

        if(firstName.equals("") || lastName.equals("") || (make.equals("")) || (model.equals("")) || (year.equals(""))) {
            makeText(getApplicationContext(),"Please complete the form", LENGTH_SHORT).show();
        }
        else {

            double doubleYear = Double.parseDouble(setupEditTextCarYear.getText().toString());

            db.collection("Users").document(userEmail).update(
                    KEY_FIRST_NAME, firstName,
                    KEY_LAST_NAME, lastName,
                    KEY_CARMAKE, make,
                    KEY_CARMODEL, model,
                    KEY_YEAR, doubleYear,
                    KEY_INIT, true);
            openMain();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tabbedActivity.class);
        startActivity(intent);
    }
}
