package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link rideTripsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class rideTripsFragment extends Fragment {




    private static final String TAG = "PoolLobby";
    private static final String KEY_RIDER_ARRIVE_TIMESTAMP = "riderArriveTimeStamp";
    private static final String KEY_RIDER_DEST_LAT = "riderDestLat";
    private static final String KEY_RIDER_DEST_LNG = "riderDestLng";
    private static final String KEY_RIDER_DESTINATION = "riderDestination";
    private static final String KEY_RIDER_START_LAT = "riderStartLat";
    private static final String KEY_RIDER_START_LNG = "riderStartLng";
    private static final String KEY_RIDER_START_POINT = "riderStartPoint";
    private static final String KEY_RIDER_STRAY_DEST = "riderStrayDest";
    private static final String KEY_RIDER_STRAY_START = "riderStrayStart";
    private static final String KEY_RIDER_FIRST_NAME = "riderFirstName";
    private static final String KEY_RIDER_LAST_NAME = "riderLastName";
    private static final String KEY_RIDER_CAR_MAKE = "riderCarMake";
    private static final String KEY_RIDER_CAR_MODEL = "riderCarModel";
    private static final String KEY_RIDER_CAR_YEAR = "riderCarYear";
    private static final String KEY_RIDER_EXPERIENCE = "riderExperience";
    private static final String KEY_RIDER_RATING = "riderRating";
    private static final String KEY_USER_FIRST_NAME = "firstName";
    private static final String KEY_USER_LAST_NAME = "lastName";
    private static final String KEY_USER_CAR_MAKE = "carMake";
    private static final String KEY_USER_CAR_MODEL = "carModel";
    private static final String KEY_USER_CAR_YEAR = "carYear";
    private static final String KEY_USER_EXPERIENCE = "experience";
    private static final String KEY_USER_RATING = "rating";


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
    private static final String EXTRA_FIRSTNAME = "ex_firstName";
    private static final String EXTRA_LASTNAME = "ex_lastName";
    private static final String EXTRA_CARMAKE = "ex_carMake";
    private static final String EXTRA_CARMODEL = "ex_carModel";
    private static final String EXTRA_CARYEAR = "ex_carYear";
    private static final String EXTRA_EXPERIENCE = "ex_experience";
    private static final String EXTRA_RATING = "ex_rating";
    private static final String EXTRA_RIDER_FIRSTNAME = "ex_riderFirstName";
    private static final String EXTRA_RIDER_LASTNAME = "ex_riderLastName";
    private static final String EXTRA_RIDER_CARMAKE = "ex_riderCarMake";
    private static final String EXTRA_RIDER_CARMODEL = "ex_riderCarModel";
    private static final String EXTRA_RIDER_CARYEAR = "ex_riderCarYear";
    private static final String EXTRA_RIDER_EXPERIENCE = "ex_riderExperience";
    private static final String EXTRA_RIDER_RATING = "ex_riderRating";



/*    double strayDest, double strayStart,
    double startLat, double startLng, double destLat,
    double destLng, Calendar arriveCal*/

    private LinearLayout dataLayout;
    private FloatingActionButton fabAddRide;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();



    // Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public rideTripsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment1.
     */
    // Rename and change types and number of parameters
    public static rideTripsFragment newInstance(String param1, String param2) {
        rideTripsFragment fragment = new rideTripsFragment();
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
        db.collection("Rider").document(userEmail).collection("Rides")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if(e != null) {
                            Toast.makeText(getContext(), "Error While Loading", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }

                        removeButtons();
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {

                            Timestamp tsArrive = doc.getTimestamp(KEY_RIDER_ARRIVE_TIMESTAMP);
                            String dest = doc.getString(KEY_RIDER_DESTINATION);
                            String startPoint = doc.getString(KEY_RIDER_START_POINT);
                            double strayDest = doc.getDouble(KEY_RIDER_STRAY_DEST);
                            double strayStart = doc.getDouble(KEY_RIDER_STRAY_START);
                            double startLat = doc.getDouble(KEY_RIDER_START_LAT);
                            double startLng = doc.getDouble(KEY_RIDER_START_LNG);
                            double destLat = doc.getDouble(KEY_RIDER_DEST_LAT);
                            double destLng = doc.getDouble(KEY_RIDER_DEST_LNG);
                            String riderFirstName = doc.getString(KEY_RIDER_FIRST_NAME);
                            String riderLastName = doc.getString(KEY_RIDER_LAST_NAME);
                            String riderCarMake = doc.getString(KEY_RIDER_CAR_MAKE);
                            String riderCarModel = doc.getString(KEY_RIDER_CAR_MODEL);
                            double riderCarYear = doc.getDouble(KEY_RIDER_CAR_YEAR);
                            double riderExperience = doc.getDouble(KEY_RIDER_EXPERIENCE);
                            double riderRating = doc.getDouble(KEY_RIDER_RATING);

                            Calendar arriveCal = Calendar.getInstance();
                            arriveCal.setTime(tsArrive.toDate());

                            int day = arriveCal.get(Calendar.DAY_OF_MONTH);
                            int wDay = arriveCal.get(Calendar.DAY_OF_WEEK);
                            int month = arriveCal.get(Calendar.MONTH) + 1;
                            int year = arriveCal.get(Calendar.YEAR);
                            int hour = arriveCal.get(Calendar.HOUR_OF_DAY);
                            int minute = arriveCal.get(Calendar.MINUTE);

                            String sMonth = new DateFormatSymbols().getMonths()[month-1];

                            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
                            String weekDay = dayFormat.format(arriveCal.getTime());

                            createButton(dest, startPoint, strayDest, strayStart, startLat, startLng,
                                    destLat, destLng, weekDay, day, sMonth, month, year, hour, minute,
                                    arriveCal, riderFirstName, riderLastName, riderCarMake, riderCarModel,
                                    riderCarYear, riderExperience, riderRating);


                        }
                    }
                });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.ride_trips_fragment, container, false);
        dataLayout = view.findViewById(R.id.listlayoutCarPool);
        fabAddRide = view.findViewById(R.id.fabAddRide);
        fabAddRide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addRide();
            }
        });
        return view;
    }

    public void createButton(String dest, String startPoint, double strayDest, double strayStart,
                             double startLat, double startLng, double destLat, double destLng,
                             String weekDay, int day, String sMonth, int month, int year, int hour, int minute,
                             Calendar arrivalCal, String rFirstName, String rLastName, String rCarMake,
                             String rCarModel, double rCarYear, double rExperience, double rRating) {

        final Button myButton = new Button(getContext());

        /*******************Trip arrive Mods - Start********************/
        String tripArriveAMPM = "AM";
        if(hour > 11) tripArriveAMPM  = "PM";

        int tripArrive12Hour;
        if(hour > 12) tripArrive12Hour = hour - 12;
        else tripArrive12Hour = hour;

        if(tripArrive12Hour == 0) tripArrive12Hour = 12;

        String tripArriveMin;
        if(minute < 10) tripArriveMin = "0" + minute;
        else tripArriveMin = "" + minute;
        /*******************Trip arrive Mods - Start********************/

        myButton.setText("From:\t\t\t\t" + startPoint + "\n"
                + "To:\t\t\t\t\t\t" + dest + "\n" +
                "Arrival:\t\t\t\t" + weekDay + ", " + sMonth
                +" " + day + ", " + year+ " @ " + tripArrive12Hour + ":" + tripArriveMin + tripArriveAMPM + "\n"
                + "Start stray:\t\t" + strayStart + " km" + "\t End stray: " + strayDest + " km");
        myButton.setBackground(this.getResources().getDrawable(R.drawable.button_background_stroke));
        myButton.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        myButton.setGravity(Gravity.LEFT);
        myButton.setPadding(20, 10,20,10);
        myButton.setTextSize(12);
        myButton.setAllCaps(false);
        myButton.setTag(dest); //@@@@@@@!!!!!#~~~~~~ This is bound to go wrong with users with identical names, therefore, make this function use the users UID instead
        myButton.setOnClickListener(rideTripInfo(myButton, strayDest, strayStart, startLat, startLng,
                destLat, destLng, arrivalCal, day, month-1, year, hour,
                minute, dest, startPoint, rFirstName, rLastName, rCarMake, rCarModel,
                rCarYear, rExperience, rRating, sMonth, weekDay));


        dataLayout.addView(myButton);

    }

    private View.OnClickListener rideTripInfo(Button myButton, final double strayDest,
                                              final double strayStart, final double startLat,
                                              final double startLng, final double destLat,
                                              final double destLng, final Calendar arriveCal,
                                              final int day, final int month, final int year,
                                              final int hour, final int minute, final String destination, final String startPoint,
                                              final String rideFirstName, final String rideLastName, final String rideCarMake,
                                              final String rideCarModel, final double rideCarYear, final double rideExperience,
                                              final double rideRating, final String sMonth, final String weekday) {
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), rideTripLobby.class);
                intent.putExtra(EXTRA_STRAYDEST, strayDest);
                intent.putExtra(EXTRA_STRAYSTART, strayStart);
                intent.putExtra(EXTRA_STARTLAT, startLat);
                intent.putExtra(EXTRA_STARTLNG, startLng);
                intent.putExtra(EXTRA_DESTLAT, destLat);
                intent.putExtra(EXTRA_DESTLNG, destLng);
                intent.putExtra(EXTRA_DATE, day);
                intent.putExtra(EXTRA_WEEKDAY, weekday);
                intent.putExtra(EXTRA_MONTH, month);
                intent.putExtra(EXTRA_STRING_MONTH, sMonth);
                intent.putExtra(EXTRA_YEAR, year);
                intent.putExtra(EXTRA_HOUR, hour);
                intent.putExtra(EXTRA_MINUTE, minute);
                intent.putExtra(EXTRA_DEST, destination);
                intent.putExtra(EXTRA_START, startPoint);
                intent.putExtra(EXTRA_RIDER_FIRSTNAME, rideFirstName);
                intent.putExtra(EXTRA_RIDER_LASTNAME, rideLastName);
                intent.putExtra(EXTRA_RIDER_CARMAKE, rideCarMake);
                intent.putExtra(EXTRA_RIDER_CARMODEL, rideCarModel);
                intent.putExtra(EXTRA_RIDER_CARYEAR, rideCarYear);
                intent.putExtra(EXTRA_RIDER_EXPERIENCE, rideExperience);
                intent.putExtra(EXTRA_RIDER_RATING, rideRating);


                startActivity(intent);
            }
        };
    }

    public void removeButtons(){
        dataLayout.removeAllViews();
    }


    public void addRide() {
        db.collection("Users").document(userEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String firstName = documentSnapshot.getString(KEY_USER_FIRST_NAME);
                String lastName = documentSnapshot.getString(KEY_USER_LAST_NAME);
                String carMake = documentSnapshot.getString(KEY_USER_CAR_MAKE);
                String carModel = documentSnapshot.getString(KEY_USER_CAR_MODEL);
                double carYear = documentSnapshot.getDouble(KEY_USER_CAR_YEAR);
                double experience = documentSnapshot.getDouble(KEY_USER_EXPERIENCE);
                double rating = documentSnapshot.getDouble(KEY_USER_RATING);

                Intent intent = new Intent(getContext(), addRide.class);
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