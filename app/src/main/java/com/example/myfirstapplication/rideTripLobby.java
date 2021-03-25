package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

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

    private static final String BUNDLE_RIDER_EMAIL = "keyRiderEmail";
    private static final String BUNDLE_RIDER_FIRST_NAME = "keyRiderFirstName";
    private static final String BUNDLE_RIDER_LAST_NAME = "keyRiderLastName";
    private static final String BUNDLE_RIDER_CAR_MAKE = "keyRiderCarMake";
    private static final String BUNDLE_RIDER_CAR_MODEL = "keyRiderCarModel";
    private static final String BUNDLE_RIDER_CAR_YEAR = "keyRiderCarYear";
    private static final String BUNDLE_RIDER_EXPERIENCE = "keyRiderExperience";
    private static final String BUNDLE_RIDER_RATING = "keyRiderRating";
    private static final String BUNDLE_RIDER_DESTINATION = "keyRiderDestination";
    private static final String BUNDLE_RIDER_START_POINT = "keyRiderStartPoint";
    private static final String BUNDLE_RIDER_STRAY_DEST = "keyRiderStrayDest";
    private static final String BUNDLE_RIDER_STRAY_START = "keyRiderStrayStart";
    private static final String BUNDLE_RIDER_START_LAT = "keyRiderStartLat";
    private static final String BUNDLE_RIDER_START_LNG = "keyRiderStartLng";
    private static final String BUNDLE_RIDER_DEST_LAT = "keyRiderDestLat";
    private static final String BUNDLE_RIDER_DEST_LNG = "keyRiderDestLng";
    private static final String BUNDLE_RIDER_ARRIVE_DATE = "keyRiderArriveDate";
    private static final String BUNDLE_RIDER_ARRIVE_WEEKDAY = "keyRiderArriveWeekday";
    private static final String BUNDLE_RIDER_ARRIVE_MONTH = "keyRiderArriveMonth";
    private static final String BUNDLE_RIDER_ARRIVE_STRING_MONTH = "keyRiderArriveStringMonth";
    private static final String BUNDLE_RIDER_ARRIVE_YEAR = "keyRiderArriveYear";
    private static final String BUNDLE_RIDER_ARRIVE_HOUR = "keyRiderArriveHour";
    private static final String BUNDLE_RIDER_ARRIVE_MINUTE = "keyRiderArriveMinute";

    private static final String BUNDLE_DRIVER_START_POINT = "keyDriverStartPoint";
    private static final String BUNDLE_DRIVER_DESTINATION = "keyDriverDestination";
    private static final String BUNDLE_DRIVER_HOUR_DEPART = "keyDriverHourDepart";
    private static final String BUNDLE_DRIVER_MINUTE_DEPART = "keyDriverMinuteDepart";
    private static final String BUNDLE_DRIVER_AMPM_DEPART_STRING = "keyDriverAMPMDepartString";
    private static final String BUNDLE_DRIVER_WEEKDAY_DEPART = "keyDriverWeekdayDepart";
    private static final String BUNDLE_DRIVER_DAY_DEPART = "keyDriverDayDepart";
    private static final String BUNDLE_DRIVER_SMONTH_DEPART = "keyDriverSMonthDepart";
    private static final String BUNDLE_DRIVER_YEAR_DEPART = "keyDriverYearDepart";
    private static final String BUNDLE_DRIVER_HOUR_ARRIVE = "keyDriverHourArrive";
    private static final String BUNDLE_DRIVER_MINUTE_ARRIVE = "keyDriverMinute_Arrive";
    private static final String BUNDLE_DRIVER_AMPM_ARRIVE_STRING = "keyDriverAMPMArriveString";
    private static final String BUNDLE_DRIVER_WEEKDAY_ARRIVE = "keyDriverWeekdayArrive";
    private static final String BUNDLE_DRIVER_DAY_ARRIVE = "keyDriverDayArrive";
    private static final String BUNDLE_DRIVER_SMONTH_ARRIVE = "keyDriverSMonthArrive";
    private static final String BUNDLE_DRIVER_YEAR_ARRIVE = "keyDriverYearArrive";
    private static final String BUNDLE_DRIVER_FIRST_NAME = "keyDriverFirstName";
    private static final String BUNDLE_DRIVER_LAST_NAME = "keyDriverLastName";
    private static final String BUNDLE_DRIVER_CAR_MAKE = "keyDriverCarMake";
    private static final String BUNDLE_DRIVER_CAR_MODEL = "keyDriverCarModel";
    private static final String BUNDLE_DRIVER_CAR_YEAR = "keyDriverCarYear";
    private static final String BUNDLE_DRIVER_EXPERIENCE = "keyDriverExperience";
    private static final String BUNDLE_DRIVER_RATING = "keyDriverRating";
    private static final String BUNDLE_DRIVER_MINTIP = "keyDriverMinTip";
    private static final String BUNDLE_DRIVER_MAXTIP = "keyDriverMaxTip";


    /* rider email
     *
     * riderArriveDate
     * riderArriveMonth
     * riderArriveYear
     * riderARriveHour
     * riderARriveMinute
     * riderDestination
     * driver start point
     * driver dest
     * hour depart
     * min depart
     * ampmdepartstring
     * weekdaydepart
     * daydepart
     * sMonthdepart
     * yearDepart
     * Hourarrive
     * minuteArrive
     * ampmArriveString
     * weekDayArrive
     * dayArrive
     * sMonthArrive---
     * yearArrive
     * firstName
     * lastName
     * carMake
     * carModel
     * carYear
     * Experience
     * rating
     * mintip
     * maxtip
     * driver email
     * driverArriveTimestamp
     * driverDepartTimestamp
     * */


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

    String riderFirstName;
    String riderLastName;
    String riderCarMake;
    String riderCarModel;
    double riderCarYear;
    double riderExperience;
    double riderRating;

    String riderDestination;
    String riderStartPoint;
    double riderStrayDest;
    double riderStrayStart;
    double riderStartLat;
    double riderStartLng;
    double riderDestLat;
    double riderDestLng;
    int riderArriveDate;
    String riderArriveWeekday;
    int riderArriveMonth;
    String riderArriveStringMonth;
    int riderArriveYear;
    int riderArriveHour;
    int riderArriveMinute;

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

        BottomNavigationView navigationViewRideTripLobby = findViewById(R.id.bottomNavigationView);

        final driverOptionsFragment driverOptionsFrag = new driverOptionsFragment();
        final sentRequestsToDriverFragment sentRequestsToDriverFrag = new sentRequestsToDriverFragment();
        final carpoolMapFragment carpoolMapFrag = new carpoolMapFragment();

        fromViewRider = findViewById(R.id.fromViewRider);
        toViewRider = findViewById(R.id.toViewRider);
        startTimeRider = findViewById(R.id.startTimeRider);
        destTimeRider = findViewById(R.id.destTimeRider);

        CollectionReference collectionRef = FirebaseFirestore.getInstance().collection("GeoFirestore");
        geoFirestore = new GeoFirestore(collectionRef);

        final Intent intent = getIntent();
        riderDestination = intent.getStringExtra(EXTRA_DEST);
        riderStartPoint = intent.getStringExtra(EXTRA_START);
        riderStrayDest = intent.getDoubleExtra(EXTRA_STRAYDEST, 0);
        riderStrayStart = intent.getDoubleExtra(EXTRA_STRAYSTART, 0);
        riderStartLat = intent.getDoubleExtra(EXTRA_STARTLAT, 0);
        riderStartLng = intent.getDoubleExtra(EXTRA_STARTLNG, 0);
        riderDestLat = intent.getDoubleExtra(EXTRA_DESTLAT, 0);
        riderDestLng = intent.getDoubleExtra(EXTRA_DESTLNG, 0);
        riderArriveDate = intent.getIntExtra(EXTRA_DATE, 0);
        riderArriveWeekday = intent.getStringExtra(EXTRA_WEEKDAY);
        riderArriveMonth = intent.getIntExtra(EXTRA_MONTH, 0);
        riderArriveStringMonth = intent.getStringExtra(EXTRA_STRING_MONTH);
        riderArriveYear = intent.getIntExtra(EXTRA_YEAR, 0);
        riderArriveHour = intent.getIntExtra(EXTRA_HOUR, 0);
        riderArriveMinute = intent.getIntExtra(EXTRA_MINUTE, 0);
        riderFirstName = intent.getStringExtra(EXTRA_RIDER_FIRSTNAME);
        riderLastName = intent.getStringExtra(EXTRA_RIDER_LASTNAME);
        riderCarMake = intent.getStringExtra(EXTRA_RIDER_CARMAKE);
        riderCarModel = intent.getStringExtra(EXTRA_RIDER_CARMODEL);
        riderCarYear = intent.getDoubleExtra(EXTRA_RIDER_CARYEAR, 0);
        riderExperience = intent.getDoubleExtra(EXTRA_RIDER_EXPERIENCE, 0);
        riderRating = intent.getDoubleExtra(EXTRA_RIDER_RATING, 0);

        fromViewRider.setText("From: " + riderStartPoint);
        toViewRider.setText("To: " + riderDestination);


        navigationViewRideTripLobby.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.selectDriver){
                    setFragment(driverOptionsFrag, 1);
                    return true;
                }
                else if (id == R.id.sentRequests){
                    setFragment(sentRequestsToDriverFrag, 2);
                    return true;
                }
                else if(id == R.id.carpoolMap){
                    setFragment(carpoolMapFrag, 3);
                    return true;
                }
                return false;
            }
        });

        navigationViewRideTripLobby.setSelectedItemId(R.id.selectDriver);


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
                            Intent intentNext = new Intent(getApplicationContext(), rideAcceptedWaiting.class);
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


    }//end of onCreate() method

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

    private void setFragment(Fragment fragment, int pageNum){


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        if(pageNum == 1){
            bundle.putString(BUNDLE_RIDER_FIRST_NAME, riderFirstName);
            bundle.putString(BUNDLE_RIDER_LAST_NAME, riderLastName);
            bundle.putString(BUNDLE_RIDER_CAR_MAKE, riderCarMake);
            bundle.putString(BUNDLE_RIDER_CAR_MODEL, riderCarModel);
            bundle.putDouble(BUNDLE_RIDER_CAR_YEAR, riderCarYear);
            bundle.putDouble(BUNDLE_RIDER_EXPERIENCE, riderExperience);
            bundle.putDouble(BUNDLE_RIDER_RATING, riderRating);
            bundle.putString(BUNDLE_RIDER_DESTINATION,riderDestination);
            bundle.putString(BUNDLE_RIDER_START_POINT, riderStartPoint);
            bundle.putDouble(BUNDLE_RIDER_STRAY_DEST, riderStrayDest);
            bundle.putDouble(BUNDLE_RIDER_STRAY_START, riderStrayStart);
            bundle.putDouble(BUNDLE_RIDER_START_LAT, riderStartLat);
            bundle.putDouble(BUNDLE_RIDER_START_LNG, riderStartLng);
            bundle.putDouble(BUNDLE_RIDER_DEST_LAT, riderDestLat);
            bundle.putDouble(BUNDLE_RIDER_DEST_LNG, riderDestLng);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_DATE, riderArriveDate);
            bundle.putString(BUNDLE_RIDER_ARRIVE_WEEKDAY, riderArriveWeekday);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_MONTH, riderArriveMonth);
            bundle.putString(BUNDLE_RIDER_ARRIVE_STRING_MONTH, riderArriveStringMonth);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_YEAR, riderArriveYear);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_HOUR, riderArriveHour);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_MINUTE, riderArriveMinute);
        }

        else if (pageNum == 2){
            bundle.putString(BUNDLE_RIDER_EMAIL, userEmail);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_DATE, riderArriveDate);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_MONTH, riderArriveMonth);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_YEAR, riderArriveYear);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_HOUR, riderArriveHour);
            bundle.putInt(BUNDLE_RIDER_ARRIVE_MINUTE, riderArriveMinute);
            bundle.putString(BUNDLE_RIDER_DESTINATION, riderDestination);
        }
        else if (pageNum == 3){
            //Implement the map view of the riders own trip
        }
        fragment.setArguments(bundle);
        fragmentTransaction.replace(R.id.frameCarpoolLobby, fragment);
        fragmentTransaction.commit();
    }
}
