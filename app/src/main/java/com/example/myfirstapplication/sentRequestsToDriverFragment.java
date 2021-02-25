package com.example.myfirstapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link sentRequestsToDriverFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class sentRequestsToDriverFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    LinearLayout sentReqsLL;

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



        String reqDocs = "" + riderArriveDate + "_" + (riderArriveMonth+1) + "_" + riderArriveYear +
                "_" + riderArriveHour + ":" + riderArriveMinute + " " + riderDestination +
                " " + userEmail;

        //Create the button to display which driver has been sent a rideRequest
        db.collection("Rider").document(userEmail).collection("Rides")
                .document(reqDocs).collection("sentRequests")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Toast.makeText(rideTripLobby.this, "Error while loading!", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, e.toString());
                        }

                        lowerlayout.removeAllViews();//This is here because current implementation is only one request at a time
                        requestTextView.setText("");


                        for (DocumentSnapshot doc : queryDocumentSnapshots) {


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
                            );

                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sent_requests, container, false);
    }
}