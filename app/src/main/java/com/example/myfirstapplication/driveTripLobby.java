package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class driveTripLobby extends AppCompatActivity {

    private static final String TAG = "driveTripLobby";

    private static final String KEY_RIDER_EMAIL = "riderEmail";
    private static final String KEY_RIDER_FIRST_NAME = "riderFirstName";
    private static final String KEY_RIDER_LAST_NAME = "riderLastName";
    private static final String KEY_RIDER_REQ_DOC = "riderReqPath";
    private static final String KEY_DRIVER_REQ_DOC = "driverReqPath";

    private static final String KEY_ACCEPTED_FIRST_NAME = "riderFirstName";
    private static final String KEY_ACCEPTED_LAST_NAME = "riderLastName";
    private static final String KEY_ACCEPTED_EMAIL = "riderEmail";

    private static final String KEY_RIDER_COUNT = "riderCount";

    private static final String KEY_DRIVER_DEPART_TIMESTAMP = "departTimeStamp";
    private static final String KEY_DRIVER_ARRIVE_TIMESTAMP = "arriveTimeStamp";
    private static final String KEY_DRIVER_DESTINATION = "dest";
    private static final String KEY_DRIVER_DEST_LAT= "destLat";
    private static final String KEY_DRIVER_DEST_LNG = "destLng";
    private static final String KEY_DRIVER_MAXTIP = "maxTip";
    private static final String KEY_DRIVER_MINTIP = "minTip";
    private static final String KEY_DRIVER_RIDER_COUNT = "riderCount";
    private static final String KEY_DRIVER_SEATS_AVAIL = "seatsAvail";
    private static final String KEY_DRIVER_START_POINT = "startPoint";
    private static final String KEY_DRIVER_START_LAT = "startLat";
    private static final String KEY_DRIVER_START_LNG = "startLng";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_EMAIL = "email";
    private static final String KEY_DRIVER_DRIVE_CONFIRMED = "driveConfirmed";


    private static final String EXTRA_FIRSTNAME = "ex_firstName";
    private static final String EXTRA_LASTNAME = "ex_lastName";
    private static final String EXTRA_CARMAKE = "ex_carMake";
    private static final String EXTRA_CARMODEL = "ex_carModel";
    private static final String EXTRA_CARYEAR = "ex_carYear";
    private static final String EXTRA_EXPERIENCE = "ex_experience";
    private static final String EXTRA_RATING = "ex_rating";
    private static final String EXTRA_DATE_ARRIVE = "ex_date_arrive";
    private static final String EXTRA_MONTH_ARRIVE = "ex_month_arrive";
    private static final String EXTRA_YEAR_ARRIVE = "ex_year_arrive";
    private static final String EXTRA_HOUR_ARRIVE = "ex_hour_arrive";
    private static final String EXTRA_MINUTE_ARRIVE = "ex_minute_arrive";
    private static final String EXTRA_DATE_DEPART = "ex_date_depart";
    private static final String EXTRA_MONTH_DEPART= "ex_month_depart";
    private static final String EXTRA_YEAR_DEPART = "ex_year_depart";
    private static final String EXTRA_HOUR_DEPART = "ex_hour_depart";
    private static final String EXTRA_MINUTE_DEPART = "ex_minute_depart";
    private static final String EXTRA_DEST = "ex_dest";

    private static final String EXTRA_DEST_LAT = "ex_dest_lat";
    private static final String EXTRA_DEST_LNG = "ex_dest_lng";
    private static final String EXTRA_START_LAT = "ex_start_lat";
    private static final String EXTRA_START_LNG = "ex_start_lng";

    private static final String EXTRA_START = "ex_start";
    private static final String EXTRA_ARRIVE_TS = "ex_arrive_ts";
    private static final String EXTRA_DEPART_TS = "ex_depart_ts" ;
    private static final String EXTRA_MAX_TIP = "ex_max_tip";
    private static final String EXTRA_MIN_TIP = "ex_min_tip";
    private static final String EXTRA_SEATS_AVAIL = "ex_seats_avail";
    private static final String EXTRA_PAGE_NUM = "ex_page_num";



    String reqClickRiderEmail;
    String reqClickRiderFirstName;
    String reqClickRiderLastName;
    String driverRequestPath;
    String riderRequestPath;
    String driveDoc;


    Button rejectButton;
    Button acceptButton;

    private LinearLayout dataLayout;
    private LinearLayout acceptedLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();
    Boolean driveConfirmed = false;

    String destination;
    String startPoint;
    double destLat;
    double destLng;
    double startLat;
    double startLng;
    double maxtip;
    double mintip;
    String firstName;
    String lastName;
    String carMake;
    String carModel;
    double carYear;
    double experience;
    double rating;
    double seatsAvail;

    int dateArrive;
    int monthArrive;
    int yearArrive;
    int minuteArrive;
    int hourArrive;
    int dateDepart;
    int monthDepart;
    int yearDepart;
    int minuteDepart;
    int hourDepart;

    TextView tripInfo;
    TextView reqsTextView;
    TextView acceptedRidersView;

    Toolbar toolbarDriveLobby;
    TextView fromViewDriver;
    TextView toViewDriver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_trip_lobby);

        toolbarDriveLobby = findViewById(R.id.toolbarDriveLobby);
        setSupportActionBar(toolbarDriveLobby);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        fromViewDriver = findViewById(R.id.fromViewDriver);
        toViewDriver = findViewById(R.id.toViewDriver);

        //Retrieve data from last page
        Intent intent = getIntent();

        dateArrive = intent.getIntExtra(EXTRA_DATE_ARRIVE, 0);
        monthArrive = intent.getIntExtra(EXTRA_MONTH_ARRIVE, 0);
        yearArrive = intent.getIntExtra(EXTRA_YEAR_ARRIVE, 0);
        minuteArrive = intent.getIntExtra(EXTRA_MINUTE_ARRIVE, 0);
        hourArrive = intent.getIntExtra(EXTRA_HOUR_ARRIVE, 0);
        dateDepart = intent.getIntExtra(EXTRA_DATE_DEPART, 0);
        monthDepart = intent.getIntExtra(EXTRA_MONTH_DEPART, 0);
        yearDepart = intent.getIntExtra(EXTRA_YEAR_DEPART, 0);
        minuteDepart = intent.getIntExtra(EXTRA_MINUTE_DEPART, 0);
        hourDepart = intent.getIntExtra(EXTRA_HOUR_DEPART, 0);
        destination = intent.getStringExtra(EXTRA_DEST);
        destLat = intent.getDoubleExtra(EXTRA_DEST_LAT, 0);
        destLng = intent.getDoubleExtra(EXTRA_DEST_LNG, 0);
        startPoint = intent.getStringExtra(EXTRA_START);
        startLat = intent.getDoubleExtra(EXTRA_START_LAT, 0);
        startLng = intent.getDoubleExtra(EXTRA_START_LNG, 0);
        maxtip = intent.getDoubleExtra(EXTRA_MAX_TIP, 0);
        mintip = intent.getDoubleExtra(EXTRA_MIN_TIP, 0);
        firstName = intent.getStringExtra(EXTRA_FIRSTNAME);
        lastName = intent.getStringExtra(EXTRA_LASTNAME);
        carMake = intent.getStringExtra(EXTRA_CARMAKE);
        carModel = intent.getStringExtra(EXTRA_CARMODEL);
        carYear = intent.getDoubleExtra(EXTRA_CARYEAR, 0);
        experience = intent.getDoubleExtra(EXTRA_EXPERIENCE, 0);
        rating = intent.getDoubleExtra(EXTRA_RATING, 0);
        seatsAvail = intent.getDoubleExtra(EXTRA_SEATS_AVAIL, 0);

        fromViewDriver.setText(destination);
        String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive-1];
        toViewDriver.setText(sMonthArrive + " " + dateArrive + " " + yearArrive + " " + hourArrive + ":" + minuteArrive);

        driveDoc = "" + dateArrive + "_" + monthArrive + "_" + yearArrive + "_" + hourArrive +
                ":" + minuteArrive + " " + destination + " " + userEmail;


        /*********************************************START:If drive is confirmed, move to waiting page*******************************************************/
        db.collection("Drive").document(userEmail).collection("Drives")
                .document(driveDoc).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(e != null) {
                    makeText(driveTripLobby.this, "Error while loading!", LENGTH_SHORT).show();
                    Log.d(TAG, e.toString());
                }
                if(documentSnapshot.getBoolean(KEY_DRIVER_DRIVE_CONFIRMED)){
                    toDriveConfirmed();
                }
                if(documentSnapshot.getDouble(KEY_RIDER_COUNT) >= documentSnapshot.getDouble(KEY_DRIVER_SEATS_AVAIL)){
                    db.collection("Drive").document(userEmail).collection("Drives")
                            .document(driveDoc).update(KEY_DRIVER_DRIVE_CONFIRMED, true);
                }
            }
        });
        /*********************************************END:If drive is confirmed, move to waiting page*******************************************************/

        db.collection("Users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

            }
        });


        //Connect variables to activity features
        reqsTextView = findViewById(R.id.reqsTextView);
        acceptedRidersView = findViewById(R.id.accepedRidersView);
        dataLayout = findViewById(R.id.layoutReqToJoin);
        acceptedLayout = findViewById(R.id.acceptedLinearLayout);
        rejectButton = findViewById(R.id.rejectRequest);
        acceptButton = findViewById(R.id.acceptRequest);
        tripInfo = findViewById(R.id.textViewTrip);
        rejectButton.setVisibility(View.INVISIBLE);
        acceptButton.setVisibility(View.INVISIBLE);

        reqsTextView.setTextColor(getResources().getColor(android.R.color.black));
        reqsTextView.setPadding(5, 0, 0, 0);
        reqsTextView.setText("Currently no requests to join trip");

        acceptedRidersView.setTextColor(getResources().getColor(android.R.color.black));
        acceptedRidersView.setPadding(5, 0, 0, 0);
        acceptedRidersView.setText("");

        rejectButton.setOnClickListener(new View.OnClickListener() {//Delete from db the reqs for the driver and the rider
            @Override
            public void onClick(View view) { deleteSelectedReqs();}
        });//end of reject onClickListener

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteSelectedReqs();//Remove the requests associated with this acceptance
                //Create documents in the database for accepted

                Calendar departTime = Calendar.getInstance();
                departTime.set(Calendar.YEAR, yearDepart);
                departTime.set(Calendar.MONTH, monthDepart-1);
                departTime.set(Calendar.DATE, dateDepart);
                departTime.set(Calendar.HOUR_OF_DAY, hourDepart);
                departTime.set(Calendar.MINUTE, minuteDepart);
                departTime.set(Calendar.SECOND, 0);
                departTime.set(Calendar.MILLISECOND, 0);

                Date departD = departTime.getTime();
                Timestamp departTs = new Timestamp(departD);

                Calendar arriveTime = Calendar.getInstance();
                arriveTime.set(Calendar.YEAR, yearArrive);
                arriveTime.set(Calendar.MONTH, monthArrive-1);
                arriveTime.set(Calendar.DATE, dateArrive);
                arriveTime.set(Calendar.HOUR_OF_DAY, hourArrive);
                arriveTime.set(Calendar.MINUTE, minuteArrive);
                arriveTime.set(Calendar.SECOND, 0);
                arriveTime.set(Calendar.MILLISECOND, 0);

                Date arriveD = arriveTime.getTime();
                Timestamp arriveTs = new Timestamp(arriveD);


                final Map<String, Object> confirmedRider = new HashMap<>();
                confirmedRider.put(KEY_RIDER_EMAIL, reqClickRiderEmail);
                confirmedRider.put(KEY_RIDER_FIRST_NAME, reqClickRiderFirstName);
                confirmedRider.put(KEY_RIDER_LAST_NAME, reqClickRiderLastName);

                Map<String, Object> confirmedDriver = new HashMap<>();
                confirmedDriver.put(KEY_DRIVER_CAR_MAKE, carMake);
                confirmedDriver.put(KEY_DRIVER_CAR_MODEL, carModel);
                confirmedDriver.put(KEY_DRIVER_CAR_YEAR, carYear);
                confirmedDriver.put(KEY_DRIVER_DESTINATION, destination);
                confirmedDriver.put(KEY_DRIVER_EXPERIENCE, experience);
                confirmedDriver.put(KEY_DRIVER_FIRST_NAME, firstName);
                confirmedDriver.put(KEY_DRIVER_LAST_NAME, lastName);
                confirmedDriver.put(KEY_DRIVER_MINTIP, mintip);
                confirmedDriver.put(KEY_DRIVER_MAXTIP, maxtip);
                confirmedDriver.put(KEY_DRIVER_RATING, rating);
                confirmedDriver.put(KEY_DRIVER_START_POINT, startPoint);
                confirmedDriver.put(KEY_DRIVER_DEPART_TIMESTAMP, departTs);
                confirmedDriver.put(KEY_DRIVER_ARRIVE_TIMESTAMP, arriveTs);






                db.collection("Rider").document(reqClickRiderEmail)
                        .collection("Rides").document(riderRequestPath)
                        .collection("acceptedDriver")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    if(task.getResult().isEmpty()){//if task is empty - meaning, "if there is no accepted Driver yet

                                        Calendar departTime = Calendar.getInstance();
                                        departTime.set(Calendar.YEAR, yearDepart);
                                        departTime.set(Calendar.MONTH, monthDepart-1);
                                        departTime.set(Calendar.DATE, dateDepart);
                                        departTime.set(Calendar.HOUR_OF_DAY, hourDepart);
                                        departTime.set(Calendar.MINUTE, minuteDepart);
                                        departTime.set(Calendar.SECOND, 0);
                                        departTime.set(Calendar.MILLISECOND, 0);

                                        Date departD = departTime.getTime();
                                        Timestamp departTs = new Timestamp(departD);

                                        Calendar arriveTime = Calendar.getInstance();
                                        arriveTime.set(Calendar.YEAR, yearArrive);
                                        arriveTime.set(Calendar.MONTH, monthArrive-1);
                                        arriveTime.set(Calendar.DATE, dateArrive);
                                        arriveTime.set(Calendar.HOUR_OF_DAY, hourArrive);
                                        arriveTime.set(Calendar.MINUTE, minuteArrive);
                                        arriveTime.set(Calendar.SECOND, 0);
                                        arriveTime.set(Calendar.MILLISECOND, 0);

                                        Date arriveD = arriveTime.getTime();
                                        Timestamp arriveTs = new Timestamp(arriveD);


                                        final Map<String, Object> confirmedRider = new HashMap<>();
                                        confirmedRider.put(KEY_RIDER_EMAIL, reqClickRiderEmail);
                                        confirmedRider.put(KEY_RIDER_FIRST_NAME, reqClickRiderFirstName);
                                        confirmedRider.put(KEY_RIDER_LAST_NAME, reqClickRiderLastName);

                                        Map<String, Object> confirmedDriver = new HashMap<>();
                                        confirmedDriver.put(KEY_DRIVER_EMAIL, userEmail);
                                        confirmedDriver.put(KEY_DRIVER_CAR_MAKE, carMake);
                                        confirmedDriver.put(KEY_DRIVER_CAR_MODEL, carModel);
                                        confirmedDriver.put(KEY_DRIVER_CAR_YEAR, carYear);
                                        confirmedDriver.put(KEY_DRIVER_DESTINATION, destination);
                                        confirmedDriver.put(KEY_DRIVER_DEST_LAT, destLat);
                                        confirmedDriver.put(KEY_DRIVER_DEST_LNG, destLng);
                                        confirmedDriver.put(KEY_DRIVER_EXPERIENCE, experience);
                                        confirmedDriver.put(KEY_DRIVER_FIRST_NAME, firstName);
                                        confirmedDriver.put(KEY_DRIVER_LAST_NAME, lastName);
                                        confirmedDriver.put(KEY_DRIVER_MINTIP, mintip);
                                        confirmedDriver.put(KEY_DRIVER_MAXTIP, maxtip);
                                        confirmedDriver.put(KEY_DRIVER_RATING, rating);
                                        confirmedDriver.put(KEY_DRIVER_START_POINT, startPoint);
                                        confirmedDriver.put(KEY_DRIVER_START_LAT, startLat);
                                        confirmedDriver.put(KEY_DRIVER_START_LNG, startLng);
                                        confirmedDriver.put(KEY_DRIVER_DEPART_TIMESTAMP, departTs);
                                        confirmedDriver.put(KEY_DRIVER_ARRIVE_TIMESTAMP, arriveTs);





                                        /*************Start: Upon clicking on a request and pressing "Accept", add 'accept' documents****************/
                                        db.collection("Rider").document(reqClickRiderEmail).collection("Rides")
                                                .document(riderRequestPath).collection("acceptedDriver")
                                                .document(driveDoc).set(confirmedDriver)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        db.collection("Drive").document(userEmail).collection("Drives")
                                                                .document(driveDoc).collection("acceptedRiders")
                                                                .document(reqClickRiderEmail).set(confirmedRider);
                                                        makeText(driveTripLobby.this, "Rider accepted", LENGTH_SHORT).show();
                                                        db.collection("Drive").document(userEmail).collection("Drives")
                                                                .document(driveDoc).update(KEY_RIDER_COUNT, FieldValue.increment(1));
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        makeText(driveTripLobby.this, "Failed: Rider revoked request", LENGTH_SHORT).show();
                                                    }
                                                });
                                        /************End: Upon clicking on a request and pressing "Accept", add 'accept' documents****************/

                                    }//end of if task is empty - meaning, "if there is no accepted Driver yet
                                    else{
                                        makeText(driveTripLobby.this, "This request has expired", LENGTH_SHORT).show();
                                    }

                                } else {
                                    Log.d(TAG, "Error getting documents: ", task.getException());
                                }
                            }
                        });





            }//end of 'accept' onClick
        });//End of 'accept' onClick method

        /*************Start: Display the all the request riders have sent to this driver for this trip*****************/
        db.collection("Drive").document(userEmail).collection("Drives")
                .document(driveDoc).collection("passengerRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            makeText(driveTripLobby.this, "Error while loading!", LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }

                        dataLayout.removeAllViews();


                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            reqsTextView.setText("Requests to join trip:");
                            final String reqEmail = doc.getString(KEY_RIDER_EMAIL);
                            String reqFirstName = doc.getString(KEY_RIDER_FIRST_NAME);
                            String reqLastName = doc.getString(KEY_RIDER_LAST_NAME);
                            String riderReqPath = doc.getString(KEY_RIDER_REQ_DOC);
                            final String driverReqPath = doc.getString(KEY_DRIVER_REQ_DOC);



                            createButton(reqEmail, reqFirstName, reqLastName, riderReqPath, driverReqPath);
                        }
                    }
                });
        /*************End: Display the all the request riders have sent to this driver for this trip*****************/

        /*************Start: Display all the riders that have been accepted for this trip*****************/
        db.collection("Drive").document(userEmail).collection("Drives")
                .document(driveDoc).collection("acceptedRiders")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            makeText(driveTripLobby.this, "Error while loading!", LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }

                        acceptedLayout.removeAllViews();
                        acceptedRidersView.setText("");


                        for(DocumentSnapshot doc : queryDocumentSnapshots) {
                            String acceptedEmail = doc.getString(KEY_ACCEPTED_EMAIL);
                            String acceptedFirstName = doc.getString(KEY_ACCEPTED_FIRST_NAME);
                            String acceptedLastName = doc.getString(KEY_ACCEPTED_LAST_NAME);

                            acceptedRidersView.setText("Accepted riders: \t\t\t(" + (int) seatsAvail + " seats are available)");

                            addAcceptedButton(acceptedEmail, acceptedFirstName, acceptedLastName);
                        }
                    }
                });
        /*************End: Display all the riders that have been accepted for this trip*****************/


    }//End of onCreate() method

    private void deleteSelectedReqs() {

        db.collection("Drive").document(userEmail).collection("Drives")
                .document(driveDoc).collection("passengerRequests").document(reqClickRiderEmail).delete();

        db.collection("Rider").document(reqClickRiderEmail).collection("Rides")
                .document(riderRequestPath).collection("sentRequests")
                .document(driverRequestPath).delete();
    }


    public void createButton(String reqEmail, String reqFirstName, String reqLastName,
                             String riderReqP, String driverReqP){
        final Button myButton = new Button(this);

        myButton.setText(
                //"Email: " + reqEmail + "\n" + "Name: " +
                        reqFirstName + " " + reqLastName);
        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        //myButton.setTextColor(getResources().getColor(android.R.color.white));
        myButton.setHeight(16);
        myButton.setTextSize(14);
        myButton.setGravity(Gravity.CENTER);
        myButton.setAllCaps(false);
        myButton.setOnClickListener(handleReq(reqEmail, reqFirstName, reqLastName, riderReqP, driverReqP));

        dataLayout.addView(myButton);
    }

    public void addAcceptedButton(String email, String firstName, String lastName){
/*        TextView view = new TextView(this);

        view.setText(firstName + " " + lastName);*/


        final Button myButton = new Button(this);
        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        myButton.setHeight(16);
        myButton.setTextSize(14);
        myButton.setGravity(Gravity.CENTER);
        myButton.setAllCaps(false);
        myButton.setText(firstName + " " + lastName);
        acceptedLayout.addView(myButton);
    }

    private View.OnClickListener handleReq(final String rideReqEmail, final String rideReqFirstName,
                                           final String rideReqLastName, final String riderRP,
                                           final String driverRP) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectButton.setVisibility(View.VISIBLE);
                acceptButton.setVisibility(View.VISIBLE);
                //Set global variables here if needed
                reqClickRiderEmail = rideReqEmail;
                reqClickRiderFirstName = rideReqFirstName;
                reqClickRiderLastName = rideReqLastName;
                riderRequestPath = riderRP;
                driverRequestPath = driverRP;
            }
        };
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tabbedActivity.class);
        intent.putExtra(EXTRA_PAGE_NUM, 1);
        startActivity(intent);
        finish();
    }

    public void toDriveConfirmed() {

        /********************************Start: Create intent to send driver to the ride confirmed waiting page**************************************/
        Intent intent = new Intent(getApplicationContext(), driveConfirmedWaiting.class);
        intent.putExtra(EXTRA_START, startPoint);
        intent.putExtra(EXTRA_DEST, destination);
        intent.putExtra(EXTRA_FIRSTNAME, firstName);
        intent.putExtra(EXTRA_LASTNAME, lastName);
        intent.putExtra(EXTRA_CARMAKE, carMake);
        intent.putExtra(EXTRA_CARMODEL, carModel);
        intent.putExtra(EXTRA_CARYEAR, carYear);
        intent.putExtra(EXTRA_DATE_ARRIVE, dateArrive);
        intent.putExtra(EXTRA_MONTH_ARRIVE, monthArrive);
        intent.putExtra(EXTRA_YEAR_ARRIVE, yearArrive);
        intent.putExtra(EXTRA_MINUTE_ARRIVE, minuteArrive);
        intent.putExtra(EXTRA_HOUR_ARRIVE, hourArrive);
        intent.putExtra(EXTRA_DATE_DEPART, dateDepart);
        intent.putExtra(EXTRA_MONTH_DEPART, monthDepart);
        intent.putExtra(EXTRA_YEAR_DEPART, yearDepart);
        intent.putExtra(EXTRA_MINUTE_DEPART, minuteDepart);
        intent.putExtra(EXTRA_HOUR_DEPART, hourDepart);
        startActivity(intent);
        /********************************End: Create intent to send driver to the ride confirmed waiting page**************************************/

        /********************************Start: Also delete all the requests that this driver has received in their driver/passengerRequests collection and delete all the requests in each of those rider's rides/sentRequests collections**************************************/
        final CollectionReference driverPassengerReqs = db.collection("Drive").document(userEmail).collection("Drives").document(driveDoc)
                .collection("passengerRequests");

        driverPassengerReqs.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {//queryDocumentSnapshots here is the list of the driver's requests
                        for(DocumentSnapshot doc : queryDocumentSnapshots){
                            //delete the request in the rider's collection
                            db.collection("Rider").document(doc.getString("riderEmail"))
                                    .collection("Rides").document(doc.getString("riderReqPath"))
                                    .collection("sentRequests").document(doc.getString("driverReqPath"))
                                    .delete();
                            //delete the request document in this driver's collection
                            driverPassengerReqs.document(doc.getId()).delete();
                        }//end of for loop to delete the rider's request to this driver

                    }//end of OnSuccess method
                });
        /********************************End: Also delete all the requests that this driver has received in their driver/passengerRequests collection and delete all the requests in each of those rider's rides/sentRequests collections**************************************/

    }

    public void confirmRide(View view){
        db.collection("Drive").document(userEmail).collection("Drives")
                .document(driveDoc).update(KEY_DRIVER_DRIVE_CONFIRMED, true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.drive_lobby_menu, menu);
        return true;
    }
}