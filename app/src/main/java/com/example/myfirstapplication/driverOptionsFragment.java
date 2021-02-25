package com.example.myfirstapplication;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Vector;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link driverOptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class driverOptionsFragment extends Fragment {

    private static final String KEY_DRIVER_EMAIL = "email";
    private static final String KEY_DRIVER_START_POINT = "startPoint";
    private static final String KEY_DRIVER_DEST = "dest";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_MAXTIP = "maxTip";
    private static final String KEY_DRIVER_MINTIP = "minTip";

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

    LinearLayout driverOptionsLL;

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


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private GeoFirestore geoFirestore;
    String userEmail = fAuth.getCurrentUser().getEmail();

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
        riderFirstName = getArguments().getString(BUNDLE_RIDER_FIRST_NAME);
        riderLastName = getArguments().getString(BUNDLE_RIDER_LAST_NAME);
        riderCarMake = getArguments().getString(BUNDLE_RIDER_CAR_MAKE);
        riderCarModel = getArguments().getString(BUNDLE_RIDER_CAR_MODEL);
        riderCarYear = getArguments().getDouble(BUNDLE_RIDER_CAR_YEAR);
        riderExperience = getArguments().getDouble(BUNDLE_RIDER_EXPERIENCE);
        riderRating = getArguments().getDouble(BUNDLE_RIDER_RATING);
        riderDestination = getArguments().getString(BUNDLE_RIDER_DESTINATION);
        riderStartPoint = getArguments().getString(BUNDLE_RIDER_START_POINT);
        riderStrayDest = getArguments().getDouble(BUNDLE_RIDER_STRAY_DEST);
        riderStrayStart = getArguments().getDouble(BUNDLE_RIDER_STRAY_START);
        riderStartLat = getArguments().getDouble(BUNDLE_RIDER_START_LAT);
        riderStartLng = getArguments().getDouble(BUNDLE_RIDER_START_LNG);
        riderDestLat = getArguments().getDouble(BUNDLE_RIDER_DEST_LAT);
        riderDestLng = getArguments().getDouble(BUNDLE_RIDER_DEST_LNG);
        riderArriveDate = getArguments().getInt(BUNDLE_RIDER_ARRIVE_DATE);
        riderArriveWeekday = getArguments().getString(BUNDLE_RIDER_ARRIVE_WEEKDAY);
        riderArriveMonth = getArguments().getInt(BUNDLE_RIDER_ARRIVE_MONTH);
        riderArriveStringMonth = getArguments().getString(BUNDLE_RIDER_ARRIVE_STRING_MONTH);
        riderArriveYear = getArguments().getInt(BUNDLE_RIDER_ARRIVE_YEAR);
        riderArriveHour = getArguments().getInt(BUNDLE_RIDER_ARRIVE_HOUR);
        riderArriveMinute = getArguments().getInt(BUNDLE_RIDER_ARRIVE_MINUTE);

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


                for (int i = 0; i <= querySnapshots.size() - 1; i++) {//for each task, but the last one that is
                    //not meant for the list of drivers, it is
                    //meant for getting rider data for later use
                    QuerySnapshot queryDocumentSnapshots = querySnapshots.get(i);

                    for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {//for each document retrieved per task

                        if (queryCounter == 0) q1Documents.add(documentSnapshot);
                        else if (queryCounter == 1) q2Documents.add(documentSnapshot);
                        else if (queryCounter == 2) q3Documents.add(documentSnapshot);
                        else if (queryCounter == 3) q4Documents.add(documentSnapshot);
                        else if (queryCounter == 4) q5Documents.add(documentSnapshot);
                        else// do nothing
                        {
                        }
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


                for (int i = 0; i < finalDocuments.size(); i++) {

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
                    if (ampmDepart == 0) ampmDepartString = "AM";
                    else ampmDepartString = "PM";
                    if (hourDepart == 0) hourDepart = 12;

                    String sMonthDepart = new DateFormatSymbols().getMonths()[monthDepart - 1];//Jan Feb...
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
                    if (ampmArrive == 0) ampmArriveString = "AM";
                    else ampmArriveString = "PM";
                    if (hourArrive == 0) hourArrive = 12;

                    String sMonthArrive = new DateFormatSymbols().getMonths()[monthArrive - 1];//Jan Feb...
                    SimpleDateFormat dayFormatArrive = new SimpleDateFormat("EEEE", Locale.US);
                    String weekDayArrive = dayFormatArrive.format(arriveCal.getTime());// Monday Tues...

                    createButton(startPoint, destination, hourDepart, minuteDepart,
                            ampmDepartString, weekDayDepart, dayDepart, sMonthDepart, monthDepart,
                            yearDepart, hourArrive, minuteArrive, ampmArriveString,
                            weekDayArrive, dayArrive, sMonthArrive, monthArrive, yearArrive,
                            firstName, lastName, carMake, carModel, carYear, experience,
                            rating, riderFirstName, riderLastName, driverEmail,
                            riderArriveDate, riderArriveMonth + 1, riderArriveYear,
                            riderArriveHour, riderArriveMinute, riderDestination, departTS, arrivalTS,
                            minTip, maxTip);
                }
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_driver_options, container, false);
        driverOptionsLL = view.findViewById(R.id.listlayoutDriverOptions);
        return view;
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

        driverOptionsLL.addView(myButton);
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

}