package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

//Create a page that has editable fields and then one that displays the entered in text

public class Profile extends AppCompatActivity {

    private static final String TAG = "Profile";
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


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();

    TextView firstNameView;
    TextView lastNameView;
    TextView carMakeView;
    TextView carModelView;
    TextView carYearView;

    private Toolbar toolbarProfile;

    String firstName;
    String lastName;
    String make;
    String model;
    double year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        toolbarProfile = findViewById(R.id.toolbarProfile);
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        firstNameView = findViewById(R.id.firstNameViewProfile); //Accessing the textviews on this page
        lastNameView = findViewById(R.id.lastNameViewProfile); //Accessing the textviews on this page
        carMakeView = findViewById(R.id.makeView); //Accessing the textviews on this page
        carModelView = findViewById(R.id.modelView); //Accessing the textviews on this page
        carYearView = findViewById(R.id.yearView); //Accessing the textviews on this page


        db.collection("Users").document(userEmail).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    makeText(Profile.this, "Error while loading!", LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }
                else{


                    User user = documentSnapshot.toObject(User.class);

                    firstName = user.getFirstName();
                    lastName = user.getLastName();
                    make = user.getCarMake();
                    model = user.getCarModel();
                    year = user.getCarYear();

                    firstNameView.setText(firstName);
                    lastNameView.setText(lastName);
                    carMakeView.setText(make);
                    carModelView.setText(model);
                    DecimalFormat REAL_FORMATTER = new DecimalFormat("0"); // this makes it such that the year view does not show a decimal point on the app
                    carYearView.setText(REAL_FORMATTER.format(year));
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_profile_details:
                openEditProfile();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        openMain();

    }

    //Function for opening the Main page
    public void openMain() {
        Intent intent = new Intent(this, tabbedActivity.class);
        startActivity(intent);
        finish();
    }

    //Function for opening the Edit Profile page
    public void openEditProfile() {

        Intent intent = new Intent(this, EditProfile.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstName);
        intent.putExtra(EXTRA_LAST_NAME, lastName);
        intent.putExtra(EXTRA_CAR_MAKE, make);
        intent.putExtra(EXTRA_CAR_MODEL, model);
        intent.putExtra(EXTRA_CAR_YEAR, year);
        startActivity(intent);
    }




}


