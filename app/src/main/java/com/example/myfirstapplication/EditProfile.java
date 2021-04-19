package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class EditProfile extends AppCompatActivity {

    private static final String TAG = "EditProfile";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_CARMAKE = "carMake";
    private static final String KEY_CARMODEL = "carModel";
    private static final String KEY_YEAR = "carYear";

    private static final String EXTRA_FIRST_NAME = "ex_firstName";
    private static final String EXTRA_LAST_NAME = "ex_lastName";
    private static final String EXTRA_CAR_MAKE = "ex_carMake";
    private static final String EXTRA_CAR_MODEL = "ex_carModel";
    private static final String EXTRA_CAR_YEAR = "ex_carYear";

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextCarMake;
    private EditText editTextCarModel;
    private EditText editTextCarYear;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();

    private Toolbar toolbarEditProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_edit_profile);

        toolbarEditProfile = findViewById(R.id.toolbarEditProfile);
        setSupportActionBar(toolbarEditProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        Intent intent = getIntent();
        String userFirstName = intent.getStringExtra(EXTRA_FIRST_NAME);
        String userLastName = intent.getStringExtra(EXTRA_LAST_NAME);
        String userCarMake = intent.getStringExtra(EXTRA_CAR_MAKE);
        String userCarModel = intent.getStringExtra(EXTRA_CAR_MODEL);
        double userCarYear = intent.getDoubleExtra(EXTRA_CAR_YEAR, 0);

        editTextFirstName = findViewById(R.id.firstNameText);
        editTextLastName = findViewById(R.id.lastNameText);
        editTextCarMake =  findViewById(R.id.makeText);
        editTextCarModel =  findViewById(R.id.modelText);
        editTextCarYear =  findViewById(R.id.yearText);

        editTextFirstName.setText(userFirstName);
        editTextLastName.setText(userLastName);
        editTextCarMake.setText(userCarMake);
        editTextCarModel.setText(userCarModel);
        DecimalFormat REAL_FORMATTER = new DecimalFormat("0"); // this makes it such that the year view does not show a decimal point on the app
        editTextCarYear.setText(REAL_FORMATTER.format(userCarYear));
    }

    @Override
    public void onBackPressed() {
        openProfile();
    }

    public void openProfile() {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }


    public void saveProfile(View view) {
        String firstName = editTextFirstName.getText().toString();
        String lastName = editTextLastName.getText().toString();
        String make = editTextCarMake.getText().toString();
        String model = editTextCarModel.getText().toString();
        String year = editTextCarYear.getText().toString();

        if(     firstName.equals("") ||
                lastName.equals("") ||
                make.equals("") ||
                model.equals("") ||
                year.equals("")
        ) {
            makeText(getApplicationContext(),"Please complete the form", LENGTH_SHORT).show();
        }
        else {
            double doubleYear = Double.parseDouble(editTextCarYear.getText().toString());

            db.collection("Users").document(userEmail).update(
                    KEY_FIRST_NAME, firstName,
                    KEY_LAST_NAME, lastName,
                    KEY_CARMAKE, make,
                    KEY_CARMODEL, model,
                    KEY_YEAR, doubleYear);
            openProfile();
        }
    }

}

