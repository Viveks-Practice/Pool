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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

public class rideTripLobby extends AppCompatActivity {

    Toolbar toolbarRideLobby;


    private static final String TAG = "DriverLobby";

    private static final String KEY_DRIVER_START_POINT = "startPoint";
    private static final String KEY_DRIVER_START_LAT= "startLat";
    private static final String KEY_DRIVER_START_LNG = "startLng";
    private static final String KEY_DRIVER_DEST = "dest";
    private static final String KEY_DRIVER_DEST_LAT= "destLat";
    private static final String KEY_DRIVER_DEST_LNG = "destLng";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_EMAIL = "email";
    private static final String KEY_DRIVER_DEPART_TIMESTAMP = "departTimeStamp";
    private static final String KEY_DRIVER_ARRIVE_TIMESTAMP = "arriveTimeStamp";
    private static final String KEY_DRIVER_DESTINATION = "dest";

    private static final String KEY_DRIVER_MAXTIP = "maxTip";
    private static final String KEY_DRIVER_MINTIP = "minTip";
    private static final String KEY_DRIVER_RIDER_COUNT = "riderCount";
    private static final String KEY_DRIVER_SEATS_AVAIL = "seatsAvail";
    private static final String KEY_RIDER_REQ_DOC = "riderReqPath";
    private static final String KEY_DRIVER_REQ_DOC = "driverReqPath";
    private static final String KEY_RIDER_EMAIL = "riderEmail";
    private static final String KEY_RIDER_FIRST_NAME = "riderFirstName";
    private static final String KEY_RIDER_LAST_NAME = "riderLastName";

    private static final String EXTRA_DRIVER_DEST_LAT = "ex_driverDestLat";
    private static final String EXTRA_DRIVER_DEST_LNG = "ex_driverDestLng";
    private static final String EXTRA_DRIVER_START_LAT = "ex_driverStartLat";
    private static final String EXTRA_DRIVER_START_LNG = "ex_driverStartLng";

    private static final String EXTRA_DRIVER_EMAIL = "ex_driverEmail";
    private static final String EXTRA_STRAYDEST = "ex_strayDest";
    private static final String EXTRA_STRAYSTART = "ex_strayStart";
    private static final String EXTRA_STARTLAT = "ex_startLat";
    private static final String EXTRA_STARTLNG = "ex_startLng";
    private static final String EXTRA_DESTLAT = "ex_destLat";
    private static final String EXTRA_DESTLNG = "ex_destLng";
    private static final String EXTRA_DATE = "ex_date";
    private static final String EXTRA_WEEKDAY = "ex_weekday";
    private static final String EXTRA_MONTH = "ex_month";
    private static final String EXTRA_STRING_MONTH = "ex_string_month";
    private static final String EXTRA_YEAR = "ex_year";
    private static final String EXTRA_HOUR = "ex_hour";
    private static final String EXTRA_MINUTE = "ex_minute";
    private static final String EXTRA_START = "ex_start";
    private static final String EXTRA_DEST = "ex_dest";
    private static final String EXTRA_RIDER_FIRSTNAME = "ex_riderFirstName";
    private static final String EXTRA_RIDER_LASTNAME = "ex_riderLastName";
    private static final String EXTRA_RIDER_CARMAKE = "ex_riderCarMake";
    private static final String EXTRA_RIDER_CARMODEL = "ex_riderCarModel";
    private static final String EXTRA_RIDER_CARYEAR = "ex_riderCarYear";
    private static final String EXTRA_RIDER_EXPERIENCE = "ex_riderExperience";
    private static final String EXTRA_RIDER_RATING = "ex_riderRating";
    private static final String EXTRA_DRIVER_CARMAKE = "ex_driverCarMake";
    private static final String EXTRA_DRIVER_CARMODEL = "ex_driverCarModel";
    private static final String EXTRA_DRIVER_CARYEAR = "ex_driverCarYear";
    private static final String EXTRA_DRIVER_DEST = "ex_driverDest";
    private static final String EXTRA_DRIVER_EXPERIENCE = "ex_driverExperience";
    private static final String EXTRA_DRIVER_FIRSTNAME = "ex_driverFirstName";
    private static final String EXTRA_DRIVER_LASTNAME = "ex_driverLastName";
    private static final String EXTRA_DRIVER_MAX_TIP = "ex_driverMaxTip";
    private static final String EXTRA_DRIVER_MIN_TIP = "ex_driverMinTip";
    private static final String EXTRA_DRIVER_RATING = "ex_driverRating";
    private static final String EXTRA_DRIVER_START = "ex_driverStart";

    private static final String EXTRA_DRIVER_ARRIVE_DATE = "ex_driverArriveDate";
    private static final String EXTRA_DRIVER_ARRIVE_WEEKDAY = "ex_driverArriveWeekday";
    private static final String EXTRA_DRIVER_ARRIVE_MONTH = "ex_driverArriveMonth";
    private static final String EXTRA_DRIVER_ARRIVE_STRING_MONTH = "ex_driverArriveStringMonth";
    private static final String EXTRA_DRIVER_ARRIVE_YEAR = "ex_driverArriveYear";
    private static final String EXTRA_DRIVER_ARRIVE_HOUR = "ex_driverArriveHour";
    private static final String EXTRA_DRIVER_ARRIVE_MINUTE = "ex_driverArriveMinute";

    private static final String EXTRA_DRIVER_DEPART_DATE = "ex_driverDepartDate";
    private static final String EXTRA_DRIVER_DEPART_WEEKDAY = "ex_driverDepartWeekday";
    private static final String EXTRA_DRIVER_DEPART_MONTH = "ex_driverDepartMonth";
    private static final String EXTRA_DRIVER_DEPART_STRING_MONTH = "ex_driverDepartStringMonth";
    private static final String EXTRA_DRIVER_DEPART_YEAR = "ex_driverDepartYear";
    private static final String EXTRA_DRIVER_DEPART_HOUR = "ex_driverDepartHour";
    private static final String EXTRA_DRIVER_DEPART_MINUTE = "ex_driverDepartMinute";




    /***********These global driver variables are updated when a button is clicked************/

    String globalDriverEmail;
    String globalDriverFirstName;
    String globalDriverLastName;
    Timestamp globalDriverArrivalTS;
    String globalDriverCarMake;
    String globalDriverCarModel;
    double globalDriverCarYear;
    Timestamp globalDriverDepartTS;
    String globalDriverDestination;
    double globalDriverExperience;
    double globalDriverMaxTip;
    double globalDriverMinTip;
    double globalDriverRating;
    String globalDriverStartPoint;

    String cancelDriverEmail;
    String cancelDriverDestination;
    Timestamp cancelDriverArrivalTS;

    TextView requestTextView;
    LinearLayout dataLayout;
    LinearLayout lowerlayout;
    Button variableButton;


    String riderFirstName;
    String riderLastName;
    String riderCarMake;
    String riderCarModel;
    double riderCarYear;
    double riderExperience;
    double riderRating;

    TextView fromViewRider;
    TextView toViewRider;
    TextView startTimeRider;
    TextView destTimeRider;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private GeoFirestore geoFirestore;
    String userEmail = fAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ride_trip_lobby);

        toolbarRideLobby = findViewById(R.id.toolbarRideLobby);
        setSupportActionBar(toolbarRideLobby);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        fromViewRider = findViewById(R.id.fromViewRider);
        toViewRider = findViewById(R.id.toViewRider);
        startTimeRider = findViewById(R.id.startTimeRider);
        destTimeRider = findViewById(R.id.destTimeRider);

        requestTextView = findViewById(R.id.requestTextView);
        dataLayout = findViewById(R.id.listlayout);
        lowerlayout = findViewById(R.id.layoutBelow);
        variableButton = findViewById(R.id.variableButton);
        variableButton.setVisibility(View.INVISIBLE);




        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("GeoFirestore");
        geoFirestore = new GeoFirestore(collectionRef);

        final Intent intent = getIntent();
        final String riderDestination = intent.getStringExtra(EXTRA_DEST);
        final String riderStartPoint = intent.getStringExtra(EXTRA_START);
        double riderStrayDest = intent.getDoubleExtra(EXTRA_STRAYDEST, 0);
        double riderStrayStart = intent.getDoubleExtra(EXTRA_STRAYSTART, 0);
        double riderStartLat = intent.getDoubleExtra(EXTRA_STARTLAT, 0);
        double riderStartLng = intent.getDoubleExtra(EXTRA_STARTLNG, 0);
        double riderDestLat = intent.getDoubleExtra(EXTRA_DESTLAT, 0);
        double riderDestLng = intent.getDoubleExtra(EXTRA_DESTLNG, 0);
        final int riderArriveDate = intent.getIntExtra(EXTRA_DATE, 0);
        final String riderArriveWeekday = intent.getStringExtra(EXTRA_WEEKDAY);
        final int riderArriveMonth = intent.getIntExtra(EXTRA_MONTH, 0);
        final String riderArriveStringMonth = intent.getStringExtra(EXTRA_STRING_MONTH);
        final int riderArriveYear = intent.getIntExtra(EXTRA_YEAR, 0);
        final int riderArriveHour = intent.getIntExtra(EXTRA_HOUR, 0);
        final int riderArriveMinute = intent.getIntExtra(EXTRA_MINUTE, 0);
        riderFirstName = intent.getStringExtra(EXTRA_RIDER_FIRSTNAME);
        riderLastName = intent.getStringExtra(EXTRA_RIDER_LASTNAME);
        riderCarMake = intent.getStringExtra(EXTRA_RIDER_CARMAKE);
        riderCarModel = intent.getStringExtra(EXTRA_RIDER_CARMODEL);
        riderCarYear = intent.getDoubleExtra(EXTRA_RIDER_CARYEAR, 0);
        riderExperience = intent.getDoubleExtra(EXTRA_RIDER_EXPERIENCE, 0);
        riderRating = intent.getDoubleExtra(EXTRA_RIDER_RATING, 0);

        fromViewRider.setText("From: " + riderStartPoint);
        toViewRider.setText("To: " + riderDestination);


        /*******************Rider arrive Mods - Start********************/
        //Determining whether or not to show AM or PM for the driver arrive time details
        String riderArriveAMPM = "AM";
        if(riderArriveHour > 11) riderArriveAMPM = "PM";

        //Change the driver arrive value from 24 hours to 12 hours
        int riderArrive12Hour;
        if(riderArriveHour > 12) riderArrive12Hour = riderArriveHour - 12;
        else riderArrive12Hour = riderArriveHour;

        //Ensure the driver arrive hour doesn't show a zero
        if(riderArrive12Hour == 0) riderArrive12Hour = 12;

        //Ensure the driver arrive minutes are displayed in double digits
        String riderArriveMin;
        if(riderArriveMinute < 10) riderArriveMin = "0" + riderArriveMinute;
        else riderArriveMin = "" + riderArriveMinute;
        /*******************Rider arrive Mods - End********************/

        startTimeRider.setText("");
        destTimeRider.setText(riderArriveStringMonth + " " + riderArriveDate+  ", " +  + riderArrive12Hour + ":" + riderArriveMin + " " + riderArriveAMPM);

        requestTextView.setTextColor(getResources().getColor(android.R.color.black));
        requestTextView.setPadding(5, 0,0 ,0);
        requestTextView.setText("");

        /*****Below: Check for any drivers that have accepted the request, and if there is one, delete all rider requests for this trip******/

        final String selectedRiderPath = "" + riderArriveDate + "_" + (riderArriveMonth+1) + "_" + riderArriveYear +
                "_" + riderArriveHour + ":" + riderArriveMinute + " " + riderDestination +
                " " + userEmail;

         ListenerRegistration acceptedListener = db.collection("Rider").document(userEmail).collection("Rides")
                .document(selectedRiderPath).collection("acceptedDriver")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            Toast.makeText(rideTripLobby.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }


                        if(!queryDocumentSnapshots.isEmpty()){//There is at least one acceptedDriver

                            String driverEmail = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_EMAIL);
                            String driverFirstName = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_FIRST_NAME);
                            String driverLastName = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_LAST_NAME);
                            String driverCarMake = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_CAR_MAKE);
                            String driverCarModel = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_CAR_MODEL);
                            double driverCarYear = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_CAR_YEAR);
                            double driverExperience = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_EXPERIENCE);
                            double driverMaxTip = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_MAXTIP);
                            double driverMinTip = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_MINTIP);
                            double driverRating = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_RATING);
                            String driverDest = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_DEST);
                            String driverStart = queryDocumentSnapshots.getDocuments().get(0).getString(KEY_DRIVER_START_POINT);
                            double driverDestLat = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_DEST_LAT);
                            double driverDestLng = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_DEST_LNG);
                            double driverStartLat = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_START_LAT);
                            double driverStartLng = queryDocumentSnapshots.getDocuments().get(0).getDouble(KEY_DRIVER_START_LNG);
                            Timestamp driverArriveTS = queryDocumentSnapshots.getDocuments().get(0).getTimestamp(KEY_DRIVER_ARRIVE_TIMESTAMP);
                            Timestamp driverDepartTS = queryDocumentSnapshots.getDocuments().get(0).getTimestamp(KEY_DRIVER_DEPART_TIMESTAMP);

                            Calendar calDepart = Calendar.getInstance();
                            calDepart.setTime(driverDepartTS.toDate());

                            int dayDepart = calDepart.get(Calendar.DAY_OF_MONTH);
                            int monthDepart = calDepart.get(Calendar.MONTH) + 1;
                            String driverSMonthDepart = new DateFormatSymbols().getMonths()[monthDepart-1];
                            int yearDepart = calDepart.get(Calendar.YEAR);
                            int hourDepart = calDepart.get(Calendar.HOUR_OF_DAY);
                            int minuteDepart = calDepart.get(Calendar.MINUTE);
                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                            String driverWeekDayDepart = dayFormat.format(calDepart.getTime());

                            Calendar calArrive = Calendar.getInstance();
                            calArrive.setTime(driverArriveTS.toDate());

                            int dayArrive = calArrive.get(Calendar.DAY_OF_MONTH);
                            int monthArrive = calArrive.get(Calendar.MONTH) + 1;
                            String driverSMonthArrive = new DateFormatSymbols().getMonths()[monthArrive-1];
                            int yearArrive = calArrive.get(Calendar.YEAR);
                            int hourArrive = calArrive.get(Calendar.HOUR_OF_DAY);
                            int minuteArrive = calArrive.get(Calendar.MINUTE);
                            String driverWeekDayArrive = dayFormat.format(calArrive.getTime());


                            //Now delete all requests, because a driver has been accepted
                            db.collection("Rider").document(userEmail).collection("Rides")
                                    .document(selectedRiderPath).collection("sentRequests")
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                            if (task.isSuccessful()) {
                                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                                    Log.d(TAG, doc.getId() + " => " + doc.getData());


                                                    //Perform deletion of the ride req in the driver's collection, everywhere a req has been sent out
                                                    db.collection("Drive").document(doc.getString("email"))
                                                            .collection("Drives").document(doc.getId())
                                                            .collection("passengerRequests")
                                                            .document(userEmail).delete();

                                                    //Perform deletion of the ride req in rider collection
                                                    db.collection("Rider").document(userEmail)
                                                            .collection("Rides")
                                                            .document(selectedRiderPath)
                                                            .collection("sentRequests")
                                                            .document(doc.getId()).delete();
                                                }
                                            } else {
                                                Log.d(TAG, "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                            //Need to do it at the very beginning, and during, to see if the rider was added
                            //while the user was on this page
                            //Logic: If the acceptedDriver Collection was changed, and a driver exists
                            //       send the rider to the other page (its "back" button should be to the rides page!)
                            /*****Start: Send the user to the next page if a driver has already been accepted******/
                            Intent intentNext = new Intent(getApplicationContext(), riderMap.class);
                            intentNext.putExtra(EXTRA_DRIVER_CARMAKE, driverCarMake);
                            intentNext.putExtra(EXTRA_DRIVER_CARMODEL, driverCarModel);
                            intentNext.putExtra(EXTRA_DRIVER_CARYEAR, driverCarYear);
                            intentNext.putExtra(EXTRA_DRIVER_DEST, driverDest);
                            intentNext.putExtra(EXTRA_DRIVER_DEST_LAT, driverDestLat);
                            intentNext.putExtra(EXTRA_DRIVER_DEST_LNG, driverDestLng);
                            intentNext.putExtra(EXTRA_DRIVER_EXPERIENCE, driverExperience);
                            intentNext.putExtra(EXTRA_DRIVER_FIRSTNAME, driverFirstName);
                            intentNext.putExtra(EXTRA_DRIVER_LASTNAME, driverLastName);
                            intentNext.putExtra(EXTRA_DRIVER_MAX_TIP, driverMaxTip);
                            intentNext.putExtra(EXTRA_DRIVER_MIN_TIP, driverMinTip);
                            intentNext.putExtra(EXTRA_DRIVER_RATING, driverRating);
                            intentNext.putExtra(EXTRA_DRIVER_START, driverStart);
                            intentNext.putExtra(EXTRA_DRIVER_START_LAT, driverStartLat);
                            intentNext.putExtra(EXTRA_DRIVER_START_LNG, driverStartLng);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_DATE, dayArrive);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_WEEKDAY, driverWeekDayArrive);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_MONTH, monthArrive-1);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_STRING_MONTH, driverSMonthArrive);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_YEAR, yearArrive);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_HOUR, hourArrive);
                            intentNext.putExtra(EXTRA_DRIVER_ARRIVE_MINUTE, minuteArrive);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_DATE, dayDepart);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_WEEKDAY, driverWeekDayDepart);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_MONTH, monthDepart-1);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_STRING_MONTH, driverSMonthDepart);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_YEAR, yearDepart);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_HOUR, hourDepart);
                            intentNext.putExtra(EXTRA_DRIVER_DEPART_MINUTE, minuteDepart);
                            intentNext.putExtra(EXTRA_DATE, riderArriveDate);
                            intentNext.putExtra(EXTRA_WEEKDAY, riderArriveWeekday);
                            intentNext.putExtra(EXTRA_MONTH, riderArriveMonth);
                            intentNext.putExtra(EXTRA_STRING_MONTH, riderArriveStringMonth);
                            intentNext.putExtra(EXTRA_YEAR, riderArriveYear);
                            intentNext.putExtra(EXTRA_HOUR, riderArriveHour);
                            intentNext.putExtra(EXTRA_MINUTE, riderArriveMinute);
                            intentNext.putExtra(EXTRA_DEST, riderDestination);
                            intentNext.putExtra(EXTRA_START, riderStartPoint);
                            intentNext.putExtra(EXTRA_DRIVER_EMAIL, driverEmail);
                            startActivity(intentNext);
                            /*****End: Send the user to the next page if a driver has already been accepted******/
                        }
                    }
                });





        /*****Above: Check for any drivers that have accepted the request, and if there is one, delete all rider requests for this trip******/


        //Make the 5 queries and merge them using the method used in codingInFlow
        //The 5 queries require data from the database, therefore I should make a retrieval
        //asynchronously

                Calendar highBoundArrival = Calendar.getInstance();
                highBoundArrival.set(Calendar.YEAR, riderArriveYear);
                highBoundArrival.set(Calendar.MONTH, riderArriveMonth);//Months go from 0-11. ZERO BASED!
                highBoundArrival.set(Calendar.DAY_OF_MONTH, riderArriveDate);
                highBoundArrival.set(Calendar.HOUR_OF_DAY, riderArriveHour);
                highBoundArrival.set(Calendar.MINUTE, riderArriveMinute);
                highBoundArrival.set(Calendar.SECOND, 0);

                highBoundArrival.add(Calendar.MINUTE, 30);

                Date highBoundArrivalDate = highBoundArrival.getTime();
                Timestamp highBoundArrivalTS = new Timestamp(highBoundArrivalDate);


                Calendar lowBoundArrival = Calendar.getInstance();
                lowBoundArrival.set(Calendar.YEAR, riderArriveYear);
                lowBoundArrival.set(Calendar.MONTH, riderArriveMonth);//Months go from 0-11. ZERO BASED!
                lowBoundArrival.set(Calendar.DAY_OF_MONTH, riderArriveDate);
                lowBoundArrival.set(Calendar.HOUR_OF_DAY, riderArriveHour);
                lowBoundArrival.set(Calendar.MINUTE, riderArriveMinute);
                lowBoundArrival.set(Calendar.SECOND, 0);

                lowBoundArrival.add(Calendar.MINUTE, -30);

                Date lowBoundArrivalDate = lowBoundArrival.getTime();
                Timestamp lowBoundArrivalTS = new Timestamp(lowBoundArrivalDate);


                //I must convert stray from KM to degree for it to be applied to the lat lng variables
                //for the query that will return the proper list of drivers
                double startStrayDeg = riderStrayStart/111;
                double destStrayDeg = riderStrayDest/111;


                Task beginLat = db.collectionGroup("Drives")
                        .whereGreaterThanOrEqualTo("startLat", riderStartLat - startStrayDeg)
                        .whereLessThanOrEqualTo("startLat", riderStartLat + startStrayDeg)
                        .get();

                Task beginLng = db.collectionGroup("Drives")
                        .whereGreaterThanOrEqualTo("startLng", riderStartLng - startStrayDeg)
                        .whereLessThanOrEqualTo("startLng", riderStartLng + startStrayDeg)
                        .get();


                Task endLat = db.collectionGroup("Drives")
                        .whereGreaterThanOrEqualTo("destLat", riderDestLat - destStrayDeg)
                        .whereLessThanOrEqualTo("destLat", riderDestLat + destStrayDeg)
                        .get();

                Task endLng = db.collectionGroup("Drives")
                        .whereGreaterThanOrEqualTo("destLng", riderDestLng - destStrayDeg)
                        .whereLessThanOrEqualTo("destLng", riderDestLng + destStrayDeg)
                        .get();

                Task arriveTime = db.collectionGroup("Drives")
                        .whereGreaterThanOrEqualTo("arriveTimeStamp", lowBoundArrivalTS)
                        .whereLessThanOrEqualTo("arriveTimeStamp", highBoundArrivalTS)
                        .get();

                Task<List<QuerySnapshot>> allTasks = Tasks.whenAllSuccess(
                        beginLat
                        ,beginLng
                        ,endLat
                        ,endLng
                        ,arriveTime
                );
                allTasks.addOnSuccessListener(new OnSuccessListener<List<QuerySnapshot>>() {
                    @Override
                    public void onSuccess(List<QuerySnapshot> querySnapshots) {

                        //This counter will track which query I am working with
                        int queryCounter = 0;

                        Vector<QueryDocumentSnapshot> q1Documents = new Vector<QueryDocumentSnapshot>(10);
                        Vector<QueryDocumentSnapshot> q2Documents = new Vector<QueryDocumentSnapshot>(10);
                        Vector<QueryDocumentSnapshot> q3Documents = new Vector<QueryDocumentSnapshot>(10);
                        Vector<QueryDocumentSnapshot> q4Documents = new Vector<QueryDocumentSnapshot>(10);
                        Vector<QueryDocumentSnapshot> q5Documents = new Vector<QueryDocumentSnapshot>(10);
                        Vector<QueryDocumentSnapshot> finalDocuments = new Vector<QueryDocumentSnapshot>(10);




                        for(int i=0; i<=querySnapshots.size()-1; i++){//for each task, but the last one that is
                                                                    //not meant for the list of drivers, it is
                                                                    //meant for getting rider data for later use
                            QuerySnapshot queryDocumentSnapshots = querySnapshots.get(i);

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots){//for each document retrieved per task

                                if(queryCounter == 0) q1Documents.add(documentSnapshot);
                                else if (queryCounter == 1) q2Documents.add(documentSnapshot);
                                else if (queryCounter == 2) q3Documents.add(documentSnapshot);
                                else if (queryCounter == 3) q4Documents.add(documentSnapshot);
                                else if (queryCounter == 4) q5Documents.add(documentSnapshot);
                                else// do nothing
                                { }
                            }//for each document retrieved per task
                            queryCounter++;
                         }//end of for each task


                        finalDocuments = checkCommonDocuments(q1Documents, q2Documents);
                        finalDocuments = checkCommonDocuments(finalDocuments, q3Documents);
                        finalDocuments = checkCommonDocuments(finalDocuments, q4Documents);
                        finalDocuments = checkCommonDocuments(finalDocuments, q5Documents);
                        finalDocuments = checkCommonDocuments(q1Documents, q5Documents);
                        //Check for seats availability and remove all drivers whose seats are already
                        //full


                        for(int i=0; i<finalDocuments.size(); i++){

                            String driverEmail = finalDocuments.get(i).getString(KEY_DRIVER_EMAIL);
                            String startPoint = finalDocuments.get(i).getString(KEY_DRIVER_START_POINT);
                            String destination = finalDocuments.get(i).getString(KEY_DRIVER_DEST);
                            String firstName = finalDocuments.get(i).getString(KEY_DRIVER_FIRST_NAME);
                            String lastName = finalDocuments.get(i).getString(KEY_DRIVER_LAST_NAME);
                            String carMake = finalDocuments.get(i).getString(KEY_DRIVER_CAR_MAKE);
                            String carModel = finalDocuments.get(i).getString(KEY_DRIVER_CAR_MODEL);
                            double carYear = finalDocuments.get(i).getDouble(KEY_DRIVER_CAR_YEAR);
                            double experience = finalDocuments.get(i).getDouble(KEY_DRIVER_EXPERIENCE);
                            double rating = finalDocuments.get(i).getDouble(KEY_DRIVER_RATING);
                            double minTip = finalDocuments.get(i).getDouble(KEY_DRIVER_MINTIP);
                            double maxTip = finalDocuments.get(i).getDouble(KEY_DRIVER_MAXTIP);

                            Timestamp arrivalTS = finalDocuments.get(i).getTimestamp("arriveTimeStamp");
                            Timestamp departTS = finalDocuments.get(i).getTimestamp("departTimeStamp");


                            Calendar departCal = Calendar.getInstance();
                            departCal.setTime(departTS.toDate());

                            int dayDepart = departCal.get(Calendar.DAY_OF_MONTH);
                            int monthDepart = departCal.get(Calendar.MONTH) + 1;
                            int yearDepart = departCal.get(Calendar.YEAR);
                            int hourDepart = departCal.get(Calendar.HOUR);
                            int ampmDepart = departCal.get(Calendar.AM_PM);
                            int minuteDepart = departCal.get(Calendar.MINUTE);
                            String ampmDepartString = "";
                            if(ampmDepart == 0) ampmDepartString = "AM";
                            else ampmDepartString = "PM";
                            if(hourDepart == 0) hourDepart = 12;

                            String sMonthDepart = new DateFormatSymbols().getMonths()[monthDepart-1];//Jan Feb...
                            SimpleDateFormat dayFormatDepart = new SimpleDateFormat("EEEE", Locale.US);
                            String weekDayDepart = dayFormatDepart.format(departCal.getTime());// Monday Tues...

                            Calendar arriveCal = Calendar.getInstance();
                            arriveCal.setTime(arrivalTS.toDate());

                            int dayArrive = arriveCal.get(Calendar.DAY_OF_MONTH);
                            int monthArrive = arriveCal.get(Calendar.MONTH) + 1;
                            int yearArrive = arriveCal.get(Calendar.YEAR);
                            int hourArrive = arriveCal.get(Calendar.HOUR);
                            int ampmArrive = arriveCal.get(Calendar.AM_PM);
                            int minuteArrive = arriveCal.get(Calendar.MINUTE);
                            String ampmArriveString = "";
                            if(ampmArrive == 0) ampmArriveString = "AM";
                            else ampmArriveString = "PM";
                            if(hourArrive == 0 ) hourArrive = 12;

                            String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive-1];//Jan Feb...
                            SimpleDateFormat dayFormatArrive = new SimpleDateFormat("EEEE", Locale.US);
                            String weekDayArrive = dayFormatArrive.format(arriveCal.getTime());// Monday Tues...

                            createButton(startPoint, destination, hourDepart, minuteDepart,
                                        ampmDepartString, weekDayDepart, dayDepart, sMonthDepart, monthDepart,
                                        yearDepart, hourArrive, minuteArrive, ampmArriveString,
                                        weekDayArrive, dayArrive, sMonthArrive, monthArrive, yearArrive,
                                        firstName, lastName, carMake, carModel, carYear, experience,
                                        rating, riderFirstName, riderLastName, driverEmail,
                                        riderArriveDate, riderArriveMonth+1, riderArriveYear,
                                        riderArriveHour, riderArriveMinute, riderDestination, departTS, arrivalTS,
                                        minTip, maxTip);
                        }

                        String reqDocs = "" + riderArriveDate + "_" + (riderArriveMonth+1) + "_" + riderArriveYear +
                                "_" + riderArriveHour + ":" + riderArriveMinute + " " + riderDestination +
                                " " + userEmail;

                        //Create the button to display which driver has been sent a rideRequest
                        db.collection("Rider").document(userEmail).collection("Rides")
                                .document(reqDocs).collection("sentRequests")
                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                                    @Override
                                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                                        if(e != null) {
                                            Toast.makeText(rideTripLobby.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }

                                        lowerlayout.removeAllViews();//This is here because current implementation is only one request at a time
                                        requestTextView.setText("");


                                        for(DocumentSnapshot doc : queryDocumentSnapshots){


                                            Timestamp departTS = doc.getTimestamp(KEY_DRIVER_DEPART_TIMESTAMP);
                                            Calendar calDepart = Calendar.getInstance();
                                            calDepart.setTime(departTS.toDate());
                                            int dayDepart = calDepart.get(Calendar.DAY_OF_MONTH);
                                            int wDayDepart = calDepart.get(Calendar.DAY_OF_WEEK);
                                            int monthDepart = calDepart.get(Calendar.MONTH) + 1;
                                            int yearDepart = calDepart.get(Calendar.YEAR);
                                            int hourDepart = calDepart.get(Calendar.HOUR);
                                            int minuteDepart = calDepart.get(Calendar.MINUTE);
                                            int ampmDepart = calDepart.get(Calendar.AM_PM);
                                            String ampmDepartString = "";
                                            if(ampmDepart == 0) ampmDepartString = "AM";
                                            else ampmDepartString = "PM";
                                            if(hourDepart == 0) hourDepart = 12;

                                            Timestamp arriveTS = doc.getTimestamp(KEY_DRIVER_ARRIVE_TIMESTAMP);
                                            Calendar calArrive = Calendar.getInstance();
                                            calArrive.setTime(arriveTS.toDate());
                                            int dayArrive = calArrive.get(Calendar.DAY_OF_MONTH);
                                            int wDayArrive = calArrive.get(Calendar.DAY_OF_WEEK);
                                            int monthArrive = calArrive.get(Calendar.MONTH) + 1;
                                            int yearArrive = calArrive.get(Calendar.YEAR);
                                            int hourArrive = calArrive.get(Calendar.HOUR);
                                            int minuteArrive = calArrive.get(Calendar.MINUTE);
                                            int ampmArrive = calArrive.get(Calendar.AM_PM);
                                            String ampmArriveString = "";
                                            if(ampmArrive == 0) ampmArriveString = "AM";
                                            else ampmArriveString = "PM";
                                            if(hourArrive == 0) hourArrive = 12;

                                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                                            String sMonthDepart = new DateFormatSymbols().getMonths()[monthDepart-1];
                                            String weekDayDepart = dayFormat.format(calDepart.getTime());

                                            String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive-1];
                                            String weekDayArrive = dayFormat.format(calArrive.getTime());

                                            requestTextView.setText("Requests sent to: ");

                                            createLowerButton(
                                                    doc.getString(KEY_DRIVER_START_POINT),
                                                    doc.getString(KEY_DRIVER_DEST),
                                                    hourDepart,
                                                    minuteDepart,
                                                    ampmDepartString,
                                                    weekDayDepart,
                                                    dayDepart,
                                                    sMonthDepart,
                                                    yearDepart,
                                                    hourArrive,
                                                    minuteArrive,
                                                    ampmArriveString,
                                                    weekDayArrive,
                                                    dayArrive,
                                                    sMonthArrive,
                                                    yearArrive,
                                                    doc.getString(KEY_DRIVER_FIRST_NAME),
                                                    doc.getString(KEY_DRIVER_LAST_NAME),
                                                    doc.getString(KEY_DRIVER_CAR_MAKE),
                                                    doc.getString(KEY_DRIVER_CAR_MODEL),
                                                    doc.getDouble(KEY_DRIVER_CAR_YEAR),
                                                    doc.getDouble(KEY_DRIVER_EXPERIENCE),
                                                    doc.getDouble(KEY_DRIVER_RATING),
                                                    doc.getDouble(KEY_DRIVER_MINTIP),
                                                    doc.getDouble(KEY_DRIVER_MAXTIP),
                                                    doc.getString(KEY_DRIVER_EMAIL),
                                                    doc.getTimestamp(KEY_DRIVER_ARRIVE_TIMESTAMP)
                                            );//End of createLowerButton function call
                                        }//End of for loop - for all documents in the sentRequests collection

                                    }//End of onEvent (in the snapshot listener)

                                });//End of addSnapshotListener to create buttons for drivers that reqs have been sent to



                        Button variabButton = findViewById(R.id.variableButton);
                        variabButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                String buttonText = "";

                                buttonText = (String) variableButton.getText();



                                if(buttonText.equals("Send a Request")) {//If we are sending a request

                                    Toast.makeText(getApplicationContext(), "Request sending", Toast.LENGTH_SHORT).show();


                                    Calendar calendarArrive = Calendar.getInstance();
                                    calendarArrive.setTime(globalDriverArrivalTS.toDate());

                                    Calendar calendarDepart = Calendar.getInstance();
                                    calendarDepart.setTime(globalDriverDepartTS.toDate());

                                    String driveReqDocName = "" + calendarArrive.get(Calendar.DATE) +
                                            "_" + (calendarArrive.get(Calendar.MONTH) + 1) +
                                            "_" + calendarArrive.get(Calendar.YEAR) +
                                            "_" + calendarArrive.get(Calendar.HOUR_OF_DAY) +
                                            ":" + calendarArrive.get(Calendar.MINUTE) +
                                            " " + globalDriverDestination +
                                            " " + globalDriverEmail;

                                    String riderReqDocName = "" + riderArriveDate + "_" + (riderArriveMonth + 1) + "_" +
                                            riderArriveYear + "_" + riderArriveHour + ":" + riderArriveMinute +
                                            " " + riderDestination + " " + userEmail;

                                    Map<String, Object> passengerReq = new HashMap<>();
                                    passengerReq.put(KEY_RIDER_EMAIL, userEmail);
                                    passengerReq.put(KEY_RIDER_FIRST_NAME, riderFirstName);
                                    passengerReq.put(KEY_RIDER_LAST_NAME, riderLastName);
                                    passengerReq.put(KEY_RIDER_REQ_DOC, riderReqDocName);
                                    passengerReq.put(KEY_DRIVER_REQ_DOC, driveReqDocName);


                                    Map<String, Object> sentReq = new HashMap<>();
                                    sentReq.put(KEY_DRIVER_EMAIL, globalDriverEmail);
                                    sentReq.put(KEY_DRIVER_FIRST_NAME, globalDriverFirstName);
                                    sentReq.put(KEY_DRIVER_LAST_NAME, globalDriverLastName);
                                    sentReq.put(KEY_DRIVER_ARRIVE_TIMESTAMP, globalDriverArrivalTS);
                                    sentReq.put(KEY_DRIVER_CAR_MAKE, globalDriverCarMake);
                                    sentReq.put(KEY_DRIVER_CAR_MODEL, globalDriverCarModel);
                                    sentReq.put(KEY_DRIVER_CAR_YEAR, globalDriverCarYear);
                                    sentReq.put(KEY_DRIVER_DEPART_TIMESTAMP, globalDriverDepartTS);
                                    sentReq.put(KEY_DRIVER_DESTINATION, globalDriverDestination);
                                    sentReq.put(KEY_DRIVER_EXPERIENCE, globalDriverExperience);
                                    sentReq.put(KEY_DRIVER_MAXTIP, globalDriverMaxTip);
                                    sentReq.put(KEY_DRIVER_MINTIP, globalDriverMinTip);
                                    sentReq.put(KEY_DRIVER_RATING, globalDriverRating);
                                    sentReq.put(KEY_DRIVER_START_POINT, globalDriverStartPoint);

                                    db.collection("Drive").document(globalDriverEmail).collection("Drives")
                                            .document(driveReqDocName).collection("passengerRequests")
                                            .document(userEmail).set(passengerReq);
                                    db.collection("Rider").document(userEmail).collection("Rides")//Make this a hardcoded path!!
                                            .document(riderReqDocName).collection("sentRequests")
                                            .document(driveReqDocName).set(sentReq);

                                }//end of if this button is sending a request
                                else if (buttonText.equals("Cancel")){//if the button reads Cancel
                                    Toast.makeText(getApplicationContext(), "Cancelling request!", Toast.LENGTH_SHORT).show();


                                    Calendar cancelArrive = Calendar.getInstance();
                                    cancelArrive.setTime(cancelDriverArrivalTS.toDate());

                                    String driveCancelDocName = "" + cancelArrive.get(Calendar.DATE) +
                                            "_" + (cancelArrive.get(Calendar.MONTH) + 1) +
                                            "_" + cancelArrive.get(Calendar.YEAR) +
                                            "_" + cancelArrive.get(Calendar.HOUR_OF_DAY) +
                                            ":" + cancelArrive.get(Calendar.MINUTE) +
                                            " " + cancelDriverDestination +
                                            " " + cancelDriverEmail;

                                    String riderCancelDocName = "" + riderArriveDate + "_" + (riderArriveMonth + 1) + "_" +
                                            riderArriveYear + "_" + riderArriveHour + ":" + riderArriveMinute +
                                            " " + riderDestination + " " + userEmail;

                                    db.collection("Drive").document(cancelDriverEmail).collection("Drives")
                                            .document(driveCancelDocName).collection("passengerRequests")
                                            .document(userEmail).delete();

                                    db.collection("Rider").document(userEmail).collection("Rides")//Make this a hardcoded path!!
                                            .document(riderCancelDocName).collection("sentRequests")
                                            .document(driveCancelDocName).delete();



                                }//end of "if buttonText is Cancel"
                                else {//do nothing
                                    Toast.makeText(getApplicationContext(), "This case should not occur", Toast.LENGTH_SHORT).show();
                                }

                            }//End of onClick
                        });//End of variabButton on clicklistener
                    }//End of snapshot query listener for retrieving all tasks to make the appropriate driver list

                });//End of snapshot query listener for retrieving all tasks to make the appropriate driver list



    }//end of onCreate() method

    public void createLowerButton(String startPoint, String endPoint, int hourDepart, int minuteDepart,
                                  String ampmDepartString, String weekDayDepart, int dayDepart,
                                  String sMonthDepart, int yearDepart, int hourArrive, int minuteArrive,
                                  String ampmArriveString, String weekDayArrive, int dayArrive,
                                  String sMonthArrive, int yearArrive, String firstName, String lastName,
                                  String carMake, String carModel, double carYear, double experience,
                                  double rating, double minTip, double maxTip, String driverLowerEmail,
                                  Timestamp driverLowerArrivalTimestamp){
        Button myButton = new Button(this);
        DecimalFormat precision = new DecimalFormat("0.00");
        String moneytipMin = precision.format(minTip);
        String moneytipMax = precision.format(maxTip);

        myButton.setText("Start:\t\t\t\t\t\t" + startPoint + "\n" +
                "Destination:\t\t" +
                endPoint + "\n" +
                "Departing at:\t\t" +
                hourDepart + ":" +
                minuteDepart +
                ampmDepartString + " " +
                weekDayDepart + " " +
                dayDepart + " " +
                sMonthDepart + " " +
                yearDepart
                + "\n" +
                "Arriving at:\t\t\t" +
                hourArrive + ":" +
                minuteArrive + "" +
                ampmArriveString + " " +
                weekDayArrive + " " +
                dayArrive + " " +
                sMonthArrive + " " +
                yearArrive + "\n" +
                "User:\t\t\t\t\t\t" + firstName + " " + lastName + " " + carMake + " " + carModel + " " + (int) carYear +
                //" EXP: " + experience + " Rating: " + rating +
                "\n" +
                "Tip Range:\t\t\t$" + moneytipMin + " - $" + moneytipMax

        );

        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        //myButton.setTextColor(getResources().getColor(android.R.color.white));
        myButton.setHeight(200);
        myButton.setGravity(Gravity.LEFT);
        myButton.setPadding(20, 10,20,10);
        myButton.setTextSize(12);
        myButton.setAllCaps(false);
        myButton.setOnClickListener(lowButton(myButton, endPoint, driverLowerEmail, driverLowerArrivalTimestamp));
        lowerlayout.addView(myButton);

    }

    private View.OnClickListener lowButton(Button myButton, final String cancelEndpoint, final String cancelEmail,
                                           final Timestamp cancelArriveTS) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                cancelDriverDestination = cancelEndpoint;
                cancelDriverEmail = cancelEmail;
                cancelDriverArrivalTS = cancelArriveTS;

                variableButton.setText("Cancel");
                variableButton.setVisibility(View.VISIBLE);
            }
        };
    }

    public void createButton(String startPoint, String endPoint, int hourDepart,
                             int minuteDepart, String ampmDepartString, String weekDayDepart,
                             int dayDepart, String sMonthDepart, int monthDepart, int yearDepart, int hourArrive,
                             int minuteArrive, String ampmArriveString, String weekDayArrive,
                             int dayArrive, String sMonthArrive, int monthArrive, int yearArrive, String firstName,
                             String lastName, String carMake, String carModel, double carYear,
                             double experience, double rating, String riderFirstName,
                             String riderLastName, String driverEmail, int riderArriveDate,
                             int riderArriveMonth, int riderArriveYear, int riderArriveHour,
                             int riderArriveMinute, String riderDestination, Timestamp driverDepartTS,
                             Timestamp driverArrivalTS, double minTip, double maxTip) {

        Button myButton = new Button(this);

        DecimalFormat precision = new DecimalFormat("0.00");
        String moneytipMin = precision.format(minTip);
        String moneytipMax = precision.format(maxTip);

        myButton.setText("Start:\t\t\t\t\t\t" + startPoint + "\n" +
                "Destination:\t\t" +
                endPoint + "\n" +
                "Departing at:\t\t" +
                hourDepart + ":" +
                minuteDepart +
                ampmDepartString + " " +
                weekDayDepart + " " +
                dayDepart + " " +
                sMonthDepart + " " +
                yearDepart
                + "\n" +
                "Arriving at:\t\t\t" +
                hourArrive + ":" +
                minuteArrive + "" +
                ampmArriveString + " " +
                weekDayArrive + " " +
                dayArrive + " " +
                sMonthArrive + " " +
                yearArrive + "\n" +
                "User:\t\t\t\t\t\t" + firstName + " " + lastName + " " + carMake + " " + carModel + " " + (int) carYear +
                //" EXP: " + experience + " Rating: " + rating +  "\n" +
                "\n" +
                "Tip Range:\t\t\t$" + moneytipMin + " - $" + moneytipMax

        );
        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        //myButton.setTextColor(getResources().getColor(android.R.color.white));
        myButton.setHeight(200);
        myButton.setGravity(Gravity.LEFT);
        myButton.setPadding(20, 10,20,10);
        myButton.setTextSize(12);        myButton.setAllCaps(false);
        myButton.setOnClickListener(createReq( myButton, driverEmail, firstName, lastName, driverArrivalTS,
                                              carMake, carModel, carYear, driverDepartTS, endPoint,
                                              experience, maxTip, minTip, rating, startPoint));

        dataLayout.addView(myButton);
    }

    View.OnClickListener createReq(final Button button, final String createClickDriverEmail, final String createClickFirstName,
                                   final String createClickLastName, final Timestamp createClickDriverArrivalTS, final String createClickCarMake,
                                   final String createClickCarModel, final double createClickCarYear, final Timestamp createClickDriverDepartTS,
                                   final String createClickEndPoint, final double createClickExperience, final double createClickMaxTip,
                                   final double createClickMinTip, final double createClickRating, final String createClickStartPoint) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                globalDriverEmail = createClickDriverEmail;
                globalDriverFirstName = createClickFirstName;
                globalDriverLastName = createClickLastName;
                globalDriverArrivalTS = createClickDriverArrivalTS;
                globalDriverCarMake = createClickCarMake;
                globalDriverCarModel = createClickCarModel;
                globalDriverCarYear = createClickCarYear;
                globalDriverDepartTS = createClickDriverDepartTS;
                globalDriverDestination = createClickEndPoint;
                globalDriverExperience = createClickExperience;
                globalDriverMaxTip = createClickMaxTip;
                globalDriverMinTip = createClickMinTip;
                globalDriverRating = createClickRating;
                globalDriverStartPoint = createClickStartPoint;


                variableButton.setText("Send a Request");
                variableButton.setVisibility(View.VISIBLE);
            }
        };
    }

    //This function is designed to take two vectors from two queries, and return a vector
    //containing only the common elements between the two inputted vectors
    private Vector<QueryDocumentSnapshot> checkCommonDocuments(Vector<QueryDocumentSnapshot> q1,
                                                               Vector<QueryDocumentSnapshot> q2) {
        Vector<Integer> deleteVec = new Vector<Integer>(0);

        for(int i=0; i<q1.size(); i++){//Looper through q1
            Boolean found = false;

            for(int j=0; j<q2.size(); j++) {//Looper through q2
                if(q1.get(i).getId().equals(q2.get(j).getId())) {
                    found = true;
                    break;
                }
            }//End of looper through q2
            if(!found){//Here we want to collect the index numbers of all the elements that need to be deleted
                deleteVec.add(i);
            }
        }// End of Looper through q1

        Collections.sort(deleteVec);

        String str = "";
        for (int z=0; z<deleteVec.size(); z++){
            str = str + deleteVec.get(z)+ "*";
        }



        if(deleteVec.size() > 0){//if there were any elements to be deleted
            for(int i=(deleteVec.size()-1); i>=0; i--){//Delete all the collected incorrect indexes from q1
                q1.removeElementAt(deleteVec.get(i));
            }
        }


        return q1;
//(DONE) I must make sure the deletion vector is sorted
// and contains no duplicates -> don't need to, there will be no duplicates
//Implement this as a set? then I can just find the intersection OR do an easy duplicate algorithm on deleteVec....-> watev it wokred the way it is
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ride_lobby_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tabbedActivity.class);
        startActivity(intent);
        finish();
    }



}
