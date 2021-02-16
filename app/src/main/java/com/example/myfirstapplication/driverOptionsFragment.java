package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.google.firebase.Timestamp;

import java.text.DecimalFormat;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driverOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driverOptionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public driverOptionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment driverOptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static driverOptionsFragment newInstance(String param1, String param2) {
        driverOptionsFragment fragment = new driverOptionsFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_options, container, false);
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

        Button myButton = new Button(this.getContext());

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

}