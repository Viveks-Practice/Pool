package com.example.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sentRequestsToDriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sentRequestsToDriverFragment extends Fragment {

    private static final String KEY_DRIVER_DEPART_TIMESTAMP = "departTimeStamp";
    private static final String KEY_DRIVER_ARRIVE_TIMESTAMP = "arriveTimeStamp";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_EMAIL = "email";
    private static final String KEY_DRIVER_MAXTIP = "maxTip";
    private static final String KEY_DRIVER_MINTIP = "minTip";
    private static final String KEY_DRIVER_START_POINT = "startPoint";
    private static final String KEY_DRIVER_DEST = "dest";

    private static final String BUNDLE_RIDER_ARRIVE_DATE = "keyRiderArriveDate";
    private static final String BUNDLE_RIDER_ARRIVE_WEEKDAY = "keyRiderArriveWeekday";
    private static final String BUNDLE_RIDER_ARRIVE_MONTH = "keyRiderArriveMonth";
    private static final String BUNDLE_RIDER_ARRIVE_STRING_MONTH = "keyRiderArriveStringMonth";
    private static final String BUNDLE_RIDER_ARRIVE_YEAR = "keyRiderArriveYear";
    private static final String BUNDLE_RIDER_ARRIVE_HOUR = "keyRiderArriveHour";
    private static final String BUNDLE_RIDER_ARRIVE_MINUTE = "keyRiderArriveMinute";
    private static final String BUNDLE_RIDER_EMAIL = "keyRiderEmail";
    private static final String BUNDLE_RIDER_DESTINATION = "keyRiderDestination";


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

    String riderEmail;
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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LinearLayout sentReqsLL;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();

    public sentRequestsToDriverFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment sentRequestsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static sentRequestsToDriverFragment newInstance(String param1, String param2) {
        sentRequestsToDriverFragment fragment = new sentRequestsToDriverFragment();
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


        riderArriveDate = getArguments().getInt(BUNDLE_RIDER_ARRIVE_DATE);
        riderArriveMonth = getArguments().getInt(BUNDLE_RIDER_ARRIVE_MONTH);
        riderArriveYear = getArguments().getInt(BUNDLE_RIDER_ARRIVE_YEAR);
        riderArriveHour = getArguments().getInt(BUNDLE_RIDER_ARRIVE_HOUR);
        riderArriveMinute = getArguments().getInt(BUNDLE_RIDER_ARRIVE_MINUTE);
        riderDestination = getArguments().getString(BUNDLE_RIDER_DESTINATION);
        riderEmail = getArguments().getString(BUNDLE_RIDER_EMAIL);



        String reqDocs = "" + riderArriveDate + "_" + (riderArriveMonth+1) + "_" + riderArriveYear +
                "_" + riderArriveHour + ":" + riderArriveMinute + " " + riderDestination +
                " " + riderEmail;

        //Create the button to display which driver has been sent a rideRequest
        db.collection("Rider").document(riderEmail).collection("Rides")
                .document(reqDocs).collection("sentRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(getContext(), "Error while loading!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }

                        sentReqsLL.removeAllViews();//This is here because current implementation is only one request at a time

                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
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
                             * sMonthArrive
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
                            if (ampmDepart == 0) ampmDepartString = "AM";
                            else ampmDepartString = "PM";
                            if (hourDepart == 0) hourDepart = 12;

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
                            if (ampmArrive == 0) ampmArriveString = "AM";
                            else ampmArriveString = "PM";
                            if (hourArrive == 0) hourArrive = 12;

                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                            String sMonthDepart = new DateFormatSymbols().getMonths()[monthDepart - 1];
                            String weekDayDepart = dayFormat.format(calDepart.getTime());

                            String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive - 1];
                            String weekDayArrive = dayFormat.format(calArrive.getTime());

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
                            );

                        }
                    }
                });
    }

    public void createLowerButton(String startPoint, String endPoint, int hourDepart, int minuteDepart,
                                  String ampmDepartString, String weekDayDepart, int dayDepart,
                                  String sMonthDepart, int yearDepart, int hourArrive, int minuteArrive,
                                  String ampmArriveString, String weekDayArrive, int dayArrive,
                                  String sMonthArrive, int yearArrive, String firstName, String lastName,
                                  String carMake, String carModel, double carYear, double experience,
                                  double rating, double minTip, double maxTip, String driverLowerEmail,
                                  Timestamp driverLowerArrivalTimestamp) {
        Button myButton = new Button(getContext());
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
        sentReqsLL.addView(myButton);

    }

    private View.OnClickListener lowButton(Button myButton, final String cancelEndpoint, final String cancelEmail,
                                           final Timestamp cancelArriveTS) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View v) {
/*
                cancelDriverDestination = cancelEndpoint;
                cancelDriverEmail = cancelEmail;
                cancelDriverArrivalTS = cancelArriveTS;

                variableButton.setText("Cancel");
                variableButton.setVisibility(View.VISIBLE);*/
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sent_requests, container, false);
        sentReqsLL = view.findViewById(R.id.listlayoutDriverOptions);
        return view;
    }
}