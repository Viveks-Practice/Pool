package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        firstNameView = findViewById(R.id.firstNameViewProfile); //Accessing the textviews on this page
        lastNameView = findViewById(R.id.lastNameViewProfile); //Accessing the textviews on this page
        carMakeView = findViewById(R.id.makeView); //Accessing the textviews on this page
        carModelView = findViewById(R.id.modelView); //Accessing the textviews on this page
        carYearView = findViewById(R.id.yearView); //Accessing the textviews on this page



        // Opening the edit page
        final Button buttonEdit = (Button) findViewById(R.id.buttonEdit);

        db.collection("Users").document(userEmail).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    makeText(Profile.this, "Error while loading!", LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }
                else{


                    User user = documentSnapshot.toObject(User.class);

                    final String firstName = user.getFirstName();
                    final String lastName = user.getLastName();
                    final String make = user.getCarMake();
                    final String model = user.getCarModel();
                    final double year = user.getCarYear();

                    firstNameView.setText(firstName);
                    lastNameView.setText(lastName);
                    carMakeView.setText(make);
                    carModelView.setText(model);
                    DecimalFormat REAL_FORMATTER = new DecimalFormat("0"); // this makes it such that the year view does not show a decimal point on the app
                    carYearView.setText(REAL_FORMATTER.format(year));


                    buttonEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            openEditProfile(firstName, lastName, make, model, year);
                        }
                    });
                }
            }
        });

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
    public void openEditProfile(String firstNameI, String lastNameI, String makeI, String modelI,
                                double yearI) {
        Intent intent = new Intent(this, EditProfile.class);
        intent.putExtra(EXTRA_FIRST_NAME, firstNameI);
        intent.putExtra(EXTRA_LAST_NAME, lastNameI);
        intent.putExtra(EXTRA_CAR_MAKE, makeI);
        intent.putExtra(EXTRA_CAR_MODEL, modelI);
        intent.putExtra(EXTRA_CAR_YEAR, yearI);
        startActivity(intent);
    }




}


