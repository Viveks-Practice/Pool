package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driveTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driveTripsFragment extends Fragment {

    private static final String TAG = "PoolLobby";
    private static final String KEY_DRIVER_DEPART_TIMESTAMP = "departTimeStamp";
    private static final String KEY_DRIVER_ARRIVE_TIMESTAMP = "arriveTimeStamp";

    private static final String KEY_DEST = "dest";
    private static final String KEY_DEST_LAT = "destLat";
    private static final String KEY_DEST_LNG = "destLng";
    private static final String KEY_MAXTIP = "maxTip";
    private static final String KEY_MINTIP = "minTip";
    private static final String KEY_RIDER_COUNT = "riderCount";
    private static final String KEY_SEATS_AVAILABLE = "seatsAvail";
    private static final String KEY_START_POINT = "startPoint";
    private static final String KEY_START_POINT_LAT = "startLat";
    private static final String KEY_START_POINT_LNG = "startLng";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_SEATS_AVAIL= "seatsAvail";


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
    private static final String EXTRA_START = "ex_start";
    private static final String EXTRA_START_LAT = "ex_start_lat";
    private static final String EXTRA_START_LNG = "ex_start_lng";
    private static final String EXTRA_ARRIVE_TS = "ex_arrive_ts";
    private static final String EXTRA_DEPART_TS = "ex_depart_ts" ;
    private static final String EXTRA_MAX_TIP = "ex_max_tip";
    private static final String EXTRA_MIN_TIP = "ex_min_tip";
    private static final String EXTRA_SEATS_AVAIL = "ex_seats_avail";

    private FloatingActionButton fabAddDrive;
    private LinearLayout dataLayout;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();

    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public driveTripsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment2.
     */
    // Rename and change types and number of parameters
    public static driveTripsFragment newInstance(String param1, String param2) {
        driveTripsFragment fragment = new driveTripsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        //Once I get how to add them, don't forget to remove/edit them if they get removed/changed
        //kinda already doing the above with "removeButtons()" although not the most efficient method
        db.collection("Drive").document(userEmail).collection("Drives")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            makeText(getContext(), "Error while loading!", LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }
                        removeButtons();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {


                            Timestamp tsDepart = doc.getTimestamp(KEY_DRIVER_DEPART_TIMESTAMP);
                            Timestamp tsArrive = doc.getTimestamp(KEY_DRIVER_ARRIVE_TIMESTAMP);
                            String dest = doc.getString(KEY_DEST);
                            double destLat = doc.getDouble(KEY_DEST_LAT);
                            double destLng = doc.getDouble(KEY_DEST_LNG);
                            double riderCount = doc.getDouble(KEY_RIDER_COUNT);
                            double seatsAvailable = doc.getDouble(KEY_SEATS_AVAILABLE);
                            String startPoint = doc.getString(KEY_START_POINT);
                            double startLat = doc.getDouble(KEY_START_POINT_LAT);
                            double startLng = doc.getDouble(KEY_START_POINT_LNG);
                            double tipMin = doc.getDouble(KEY_MINTIP);
                            double tipMax = doc.getDouble(KEY_MAXTIP);
                            String firstName = doc.getString(KEY_DRIVER_FIRST_NAME);
                            String lastName = doc.getString(KEY_DRIVER_LAST_NAME);
                            String carMake = doc.getString(KEY_DRIVER_CAR_MAKE);
                            String carModel = doc.getString(KEY_DRIVER_CAR_MODEL);
                            double carYear = doc.getDouble(KEY_DRIVER_CAR_YEAR);
                            double experience = doc.getDouble(KEY_DRIVER_EXPERIENCE);
                            double rating = doc.getDouble(KEY_DRIVER_RATING);



                            Calendar calDepart = Calendar.getInstance();
                            calDepart.setTime(tsDepart.toDate());

                            int dayDepart = calDepart.get(Calendar.DAY_OF_MONTH);
                            int wDayDepart = calDepart.get(Calendar.DAY_OF_WEEK);
                            int monthDepart = calDepart.get(Calendar.MONTH) + 1;
                            int yearDepart = calDepart.get(Calendar.YEAR);
                            int hourDepart = calDepart.get(Calendar.HOUR_OF_DAY);
                            int minuteDepart = calDepart.get(Calendar.MINUTE);

                            Calendar calArrive = Calendar.getInstance();
                            calArrive.setTime(tsArrive.toDate());

                            int dayArrive = calArrive.get(Calendar.DAY_OF_MONTH);
                            int wDayArrive = calArrive.get(Calendar.DAY_OF_WEEK);
                            int monthArrive = calArrive.get(Calendar.MONTH) + 1;
                            int yearArrive = calArrive.get(Calendar.YEAR);
                            int hourArrive = calArrive.get(Calendar.HOUR_OF_DAY);
                            int minuteArrive = calArrive.get(Calendar.MINUTE);

                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

                            String sMonthDepart = new DateFormatSymbols().getMonths()[monthDepart-1];
                            String weekDayDepart = dayFormat.format(calDepart.getTime());

                            String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive-1];
                            String weekDayArrive = dayFormat.format(calArrive.getTime());



                            createButton(weekDayDepart, dayDepart, sMonthDepart, monthDepart, yearDepart,hourDepart,
                                    minuteDepart, dest, destLat, destLng, riderCount, seatsAvailable, startPoint,
                                    startLat, startLng, tipMax,
                                    tipMin, firstName, lastName, carMake, carModel, carYear, experience,
                                    rating, weekDayArrive, dayArrive,sMonthArrive, yearArrive,
                                    hourArrive, minuteArrive, monthArrive
                            );
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.drive_trips_fragment, container, false);
        dataLayout = view.findViewById(R.id.listlayoutPool);
        fabAddDrive= view.findViewById(R.id.fabAddDrive);
        fabAddDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDrive();
            }
        });
        return view;
    }


    public void createButton(String weekDay, int day, String sMonth, int month, int year, int hour, int minute,
                             String dest, double destLat, double destLng, double riderCount, double seatsAvailable, String startPoint,
                             double startLat, double startLng, double tipMax, double tipMin, String firstName, String lastName,
                             String carMake, String carModel, double carYear, double experience,
                             double rating, String weekDayArrive, int dayArrive, String sMonthArrive,
                             int yearArrive, int hourArrive, int minuteArrive, int monthArrive
    ) {

        final Button myButton = new Button(getContext());
        int carYearint = (int) carYear;

        /*******************Trip arrive Mods - Start********************/
        String tripDepartAMPM = "AM";
        if(hour > 11) tripDepartAMPM  = "PM";

        int tripDepart12Hour;
        if(hour > 12) tripDepart12Hour = hour - 12;
        else tripDepart12Hour = hour;

        if(tripDepart12Hour == 0) tripDepart12Hour = 12;

        String tripDepartMin;
        if(minute < 10) tripDepartMin = "0" + minute;
        else tripDepartMin = "" + minute;
        /*******************Trip arrive Mods - Start********************/

        DecimalFormat precision = new DecimalFormat("0.00");
        String moneytipMin = precision.format(tipMin);
        String moneytipMax = precision.format(tipMax);

        myButton.setText("From:\t\t" + startPoint + "\n" +
                        "To:\t\t\t\t" + dest + "\n" +
                        "Depart:\t\t" + weekDay + ", " + sMonth
                        +" " +day + ", " + year+ " @ " + tripDepart12Hour + ":" + tripDepartMin + " " + tripDepartAMPM +"\n\n"
                        + "\t\t\t\t\tRiders: " + (int) riderCount + "\t\tOpen Seats: " + (int) seatsAvailable + "\t\t" + " Tip Range: $"
                        + moneytipMin + "-$" + moneytipMax
/*                + "\n" + firstName + " " + lastName +" " + carMake + " " + carModel + " " + carYearint +
                " EXP: "+ experience
                + " Rating: " + rating*/
        );
        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        myButton.setHeight(170);
        myButton.setGravity(Gravity.LEFT);
        myButton.setPadding(20, 10,20,10);
        myButton.setTextSize(12);
        myButton.setAllCaps(false);
        myButton.setTag(dest+hour+":"+minute); //@@@@@@@!!!!!#~~~~~~ This is bound to go wrong with users with identical names, therefore, make this function use the users UID instead
        myButton.setOnClickListener(driveTripInfo(dayArrive, yearArrive,monthArrive,
                hourArrive, minuteArrive, dest, destLat, destLng, startPoint,
                startLat, startLng, firstName, lastName, carMake, carModel, carYear,
                experience, rating, tipMax, tipMin, day, year, month, hour, minute, seatsAvailable));
        dataLayout.addView(myButton);

    }

    private View.OnClickListener driveTripInfo(final int dateArrive, final int yearArrive,
                                               final int monthArrive, final int hourArrive, final int minuteArrive,
                                               final String destination, final double destinationLat, final double destinationLng,
                                               final String startPoint, final double startLat, final double startLng,

                                               final String firstName, final String lastName, final String carMake,
                                               final String carModel, final double carYear, final double experience,
                                               final double rating, final double maxTip, final double minTip,

                                               final int dateDepart, final int yearDepart, final int monthDepart,
                                               final int hourDepart, final int minuteDepart, final double seatsAvailable
    ) {



        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), driveTripLobby.class);
                intent.putExtra(EXTRA_DEST, destination);
                intent.putExtra(EXTRA_DEST_LAT, destinationLat);
                intent.putExtra(EXTRA_DEST_LNG, destinationLng);
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
                intent.putExtra(EXTRA_START, startPoint);
                intent.putExtra(EXTRA_START_LAT, startLat);
                intent.putExtra(EXTRA_START_LNG, startLng);
                intent.putExtra(EXTRA_MAX_TIP, maxTip);
                intent.putExtra(EXTRA_MIN_TIP, minTip);
                intent.putExtra(EXTRA_FIRSTNAME, firstName);
                intent.putExtra(EXTRA_LASTNAME, lastName);
                intent.putExtra(EXTRA_CARMAKE, carMake);
                intent.putExtra(EXTRA_CARMODEL, carModel);
                intent.putExtra(EXTRA_CARYEAR, carYear);
                intent.putExtra(EXTRA_EXPERIENCE, experience);
                intent.putExtra(EXTRA_RATING, rating);
                intent.putExtra(EXTRA_SEATS_AVAIL, seatsAvailable);

                startActivity(intent);
            }
        };
    }

    public void removeButtons(){
        dataLayout.removeAllViews();
    }

    public void addDrive() {
        db.collection("Users").document(userEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String firstName = documentSnapshot.getString("firstName");
                String lastName = documentSnapshot.getString("lastName");
                String carMake = documentSnapshot.getString("carMake");
                String carModel = documentSnapshot.getString("carModel");
                double carYear = documentSnapshot.getDouble("carYear");
                double experience = documentSnapshot.getDouble("experience");
                double rating = documentSnapshot.getDouble("rating");

                Intent intent = new Intent(getContext(), addDrive.class);
                intent.putExtra(EXTRA_FIRSTNAME, firstName);
                intent.putExtra(EXTRA_LASTNAME, lastName);
                intent.putExtra(EXTRA_CARMAKE, carMake);
                intent.putExtra(EXTRA_CARMODEL, carModel);
                intent.putExtra(EXTRA_CARYEAR, carYear);
                intent.putExtra(EXTRA_EXPERIENCE, experience);
                intent.putExtra(EXTRA_RATING, rating);
                startActivity(intent);
            }
        });

    }
}