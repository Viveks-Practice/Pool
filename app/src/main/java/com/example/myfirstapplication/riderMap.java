package com.example.myfirstapplication;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class riderMap extends FragmentActivity implements OnMapReadyCallback {

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

    private static final String EXTRA_DRIVER_DEST_LAT = "ex_driverDestLat";
    private static final String EXTRA_DRIVER_DEST_LNG = "ex_driverDestLng";
    private static final String EXTRA_DRIVER_START_LAT = "ex_driverStartLat";
    private static final String EXTRA_DRIVER_START_LNG = "ex_driverStartLng";

    private static final String EXTRA_DRIVER_EMAIL = "ex_driverEmail";

    private static final String EXTRA_DRIVER_MAP_EMAIL = "ex_ex";

    private static final String EXTRA_DRIVER_ARRIVE_DATE = "ex_driverArriveDate";
    private static final String EXTRA_DRIVER_ARRIVE_WEEKDAY = "ex_driverArriveWeekday";
    private static final String EXTRA_DRIVER_ARRIVE_MONTH = "ex_driverArriveMonth";
    private static final String EXTRA_DRIVER_ARRIVE_STRING_MONTH = "ex_driverArriveStringMonth";
    private static final String EXTRA_DRIVER_ARRIVE_YEAR = "ex_driverArriveYear";
    private static final String EXTRA_DRIVER_ARRIVE_HOUR = "ex_driverArriveHour";
    private static final String EXTRA_DRIVER_ARRIVE_MINUTE = "ex_driverArriveMinute";
    private static final String EXTRA_PAGE_NUM = "ex_page_num";


    private static final String EXTRA_DRIVER_DEPART_DATE = "ex_driverDepartDate";
    private static final String EXTRA_DRIVER_DEPART_WEEKDAY = "ex_driverDepartWeekday";
    private static final String EXTRA_DRIVER_DEPART_MONTH = "ex_driverDepartMonth";
    private static final String EXTRA_DRIVER_DEPART_STRING_MONTH = "ex_driverDepartStringMonth";
    private static final String EXTRA_DRIVER_DEPART_YEAR = "ex_driverDepartYear";
    private static final String EXTRA_DRIVER_DEPART_HOUR = "ex_driverDepartHour";
    private static final String EXTRA_DRIVER_DEPART_MINUTE = "ex_driverDepartMinute";

    private static final String EXTRA_RIDER_ARRIVE_DATE = "ex_date";
    private static final String EXTRA_RIDER_ARRIVE_WEEKDAY = "ex_weekday";
    private static final String EXTRA_RIDER_ARRIVE_MONTH = "ex_month";
    private static final String EXTRA_RIDER_ARRIVE_STRING_MONTH = "ex_string_month";
    private static final String EXTRA_RIDER_ARRIVE_YEAR = "ex_year";
    private static final String EXTRA_RIDER_ARRIVE_HOUR = "ex_hour";
    private static final String EXTRA_RIDER_ARRIVE_MINUTE = "ex_minute";
    private static final String EXTRA_RIDER_DEST = "ex_dest";
    private static final String EXTRA_RIDER_START = "ex_start";



    private static final String TAG = "riderMap";

    TextView lowerText;
    TextView upperText;
    TextView currentTimeView;
    TextView departTimeView;
    TextView countDownView;

    String globalDepartHour;
    String globalDepartMinute;
    String globalDepartSecond;
    String globalDepartAMPM;

    String globalCurrentHour;
    String globalCurrentMinute;
    String globalCurrentSecond;
    String globalCurrentAMPM;

    //Create all Calendar objects
    Calendar departDrive = Calendar.getInstance();
    Calendar countDown  = Calendar.getInstance();
    Calendar currentTime = Calendar.getInstance();

    double driverDestLat;
    double driverDestLng;
    double driverStartLat;
    double driverStartLng;

    String driverEmail;

    private GoogleMap mMap;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        lowerText = findViewById(R.id.lowerView);
        lowerText.setTextSize(16);
        lowerText.setMovementMethod(new ScrollingMovementMethod());

        upperText = findViewById(R.id.upperView);
        upperText.setTextSize(16);
        upperText.setMovementMethod(new ScrollingMovementMethod());



        Intent intent = getIntent();
        driverDestLat = intent.getDoubleExtra(EXTRA_DRIVER_DEST_LAT, 0);
        driverDestLng = intent.getDoubleExtra(EXTRA_DRIVER_DEST_LNG, 0);
        driverStartLat = intent.getDoubleExtra(EXTRA_DRIVER_START_LAT, 0);
        driverStartLng = intent.getDoubleExtra(EXTRA_DRIVER_START_LNG, 0);
        driverEmail = intent.getStringExtra(EXTRA_DRIVER_MAP_EMAIL);


        driverEmail = intent.getStringExtra(EXTRA_DRIVER_EMAIL);
        String driverStartPoint = intent.getStringExtra(EXTRA_DRIVER_START);
        driverStartLat = intent.getDoubleExtra(EXTRA_DRIVER_START_LAT, 0);
        driverStartLng = intent.getDoubleExtra(EXTRA_DRIVER_START_LNG, 0);
        String driverDestination = intent.getStringExtra(EXTRA_DRIVER_DEST);
        driverDestLat = intent.getDoubleExtra(EXTRA_DRIVER_DEST_LAT, 0);
        driverDestLng = intent.getDoubleExtra(EXTRA_DRIVER_DEST_LNG, 0);
        String driverFirstName = intent.getStringExtra(EXTRA_DRIVER_FIRSTNAME);
        String driverLastName = intent.getStringExtra(EXTRA_DRIVER_LASTNAME);
        String driverCarMake = intent.getStringExtra(EXTRA_DRIVER_CARMAKE);
        String driverCarModel = intent.getStringExtra(EXTRA_DRIVER_CARMODEL);
        double driverCarYear = intent.getDoubleExtra(EXTRA_DRIVER_CARYEAR,0);
        double driverExperience = intent.getDoubleExtra(EXTRA_DRIVER_EXPERIENCE, 0);
        double driverRating = intent.getDoubleExtra(EXTRA_DRIVER_RATING, 0);
        double driverMaxTip = intent.getDoubleExtra(EXTRA_DRIVER_MAX_TIP, 0);
        double driverMinTip = intent.getDoubleExtra(EXTRA_DRIVER_MIN_TIP, 0);
        int driverArriveDate = intent.getIntExtra(EXTRA_DRIVER_ARRIVE_DATE, 0);
        String driverArriveWeekday = intent.getStringExtra(EXTRA_DRIVER_ARRIVE_WEEKDAY);
        int driverArriveMonth = intent.getIntExtra(EXTRA_DRIVER_ARRIVE_MONTH, 0);
        String driverArriveStringMonth = intent.getStringExtra(EXTRA_DRIVER_ARRIVE_STRING_MONTH);
        int driverArriveYear = intent.getIntExtra(EXTRA_DRIVER_ARRIVE_YEAR, 0);
        int driverArriveHour = intent.getIntExtra(EXTRA_DRIVER_ARRIVE_HOUR, 0);
        int driverArriveMinute = intent.getIntExtra(EXTRA_DRIVER_ARRIVE_MINUTE, 0);
        final int driverDepartDate = intent.getIntExtra(EXTRA_DRIVER_DEPART_DATE, 0);
        String driverDepartWeekday = intent.getStringExtra(EXTRA_DRIVER_DEPART_WEEKDAY);
        final int driverDepartMonth = intent.getIntExtra(EXTRA_DRIVER_DEPART_MONTH, 0);
        String driverDepartStringMonth = intent.getStringExtra(EXTRA_DRIVER_DEPART_STRING_MONTH);
        final int driverDepartYear = intent.getIntExtra(EXTRA_DRIVER_DEPART_YEAR, 0);
        final int driverDepartHour = intent.getIntExtra(EXTRA_DRIVER_DEPART_HOUR, 0);
        final int driverDepartMinute = intent.getIntExtra(EXTRA_DRIVER_DEPART_MINUTE, 0);
        int riderArriveDate = intent.getIntExtra(EXTRA_RIDER_ARRIVE_DATE, 0);
        String riderArriveWeekday = intent.getStringExtra(EXTRA_RIDER_ARRIVE_WEEKDAY);
        int riderArriveMonth = intent.getIntExtra(EXTRA_RIDER_ARRIVE_MONTH, 0);
        String riderArriveStringMonth = intent.getStringExtra(EXTRA_RIDER_ARRIVE_STRING_MONTH);
        int riderArriveYear = intent.getIntExtra(EXTRA_RIDER_ARRIVE_YEAR, 0);
        int riderArriveHour = intent.getIntExtra(EXTRA_RIDER_ARRIVE_HOUR, 0);
        int riderArriveMinute = intent.getIntExtra(EXTRA_RIDER_ARRIVE_MINUTE, 0);
        String riderDestination = intent.getStringExtra(EXTRA_RIDER_DEST);
        String riderStartPoint = intent.getStringExtra(EXTRA_RIDER_START);



        /*******************Rider arrive Mods - Start********************/
        //Determining whether or not to show AM or PM for the rider arrive time details
        String riderArriveAMPM = "AM";
        if(riderArriveHour > 11) riderArriveAMPM = "PM";

        //Change the rider arrive value from 24 hours to 12 hours
        int riderArrive12Hour;
        if(riderArriveHour > 12) riderArrive12Hour = riderArriveHour - 12;
        else riderArrive12Hour = riderArriveHour;

        //Ensure the rider arrive hour doesn't show a zero
        if(riderArrive12Hour == 0) riderArrive12Hour = 12 ;

        //Ensure the rider arrive minutes are displayed in double digits
        String riderArriveMin;
        if(riderArriveMinute < 10) riderArriveMin = "0" + riderArriveMinute;
        else riderArriveMin = "" + riderArriveMinute;
        /*******************Rider arrive Mods - End********************/

        /*******************Driver arrive Mods - Start********************/
        //Determining whether or not to show AM or PM for the driver arrive time details
        String driverArriveAMPM = "AM";
        if(driverArriveHour > 11) driverArriveAMPM = "PM";

        //Change the driver arrive value from 24 hours to 12 hours
        int driverArrive12Hour;
        if(driverArriveHour > 12) driverArrive12Hour = driverArriveHour - 12;
        else driverArrive12Hour = driverArriveHour;

        //Ensure the driver arrive hour doesn't show a zero
        if(driverArrive12Hour == 0) driverArrive12Hour = 12;

        //Ensure the driver arrive minutes are displayed in double digits
        String driverArriveMin;
        if(driverArriveMinute < 10) driverArriveMin = "0" + driverArriveMinute;
        else driverArriveMin = "" + driverArriveMinute;
        /*******************Driver arrive Mods - End********************/

        /*******************Driver depart Mods - Start********************/
        //Determining whether or not to show AM or PM for the driver depart time details
        String driverDepartAMPM = "AM";
        if(driverDepartHour > 11) driverDepartAMPM = "PM";

        //Change the driver depart value from 24 hours to 12 hours
        int driverDepart12Hour;
        if(driverDepartHour > 12) driverDepart12Hour = driverDepartHour - 12;
        else driverDepart12Hour = driverDepartHour;

        //Ensure the driver depart hour doesn't show a zero
        if(driverDepart12Hour == 0) driverDepart12Hour = 12;

        //Ensure the driver depart minutes are displayed in double digits
        String driverDepartMin;
        if(driverDepartMinute < 10) driverDepartMin = "0" + driverDepartMinute;
        else driverDepartMin = "" + driverDepartMinute;
        /*******************Driver depart Mods - End********************/

        String tripsData = "My desired trip: \n" + "Begin: \t\t" + riderStartPoint + "\n" + "End: \t\t\t" + riderDestination + "\n" +
                "Arrival:\t\t" + riderArriveWeekday + ", " + riderArriveStringMonth + " " + riderArriveDate +
                ", " + riderArriveYear + " @ " + riderArrive12Hour + ":" + riderArriveMin + " " + riderArriveAMPM +
                "\n\n" +
                "Selected carpool trip: \n" + "Begin: \t\t" + driverStartPoint + "\n" + "End: \t\t\t" + driverDestination + "\n" +
                "Depart: \t" + driverDepartWeekday + ", " + driverDepartStringMonth + " " + driverDepartDate +
                ", " + driverDepartYear + " @ " + driverDepart12Hour + ":" + driverDepartMin + " " + driverDepartAMPM +  "\n" +
                "Arrive: \t\t" + driverArriveWeekday + ", " + driverArriveStringMonth + " " + driverArriveDate +
                ", " + driverArriveYear + " @ " + driverArrive12Hour  + ":" + driverArriveMin + " " + driverArriveAMPM + "\n" +
                "Tip:\t\t\t\t" + "$" + driverMinTip + "0 - " + "$" + driverMaxTip + "0" +
                "\n\n" +
                "Driver information:\n" + "Name:\t\t" + driverFirstName + " " + driverLastName + "\n" +
                "Car:\t\t\t\t" + driverCarMake + " " + driverCarModel + " " + (int) driverCarYear + "\n" +
                "Exp:\t\t\t\t" + driverExperience + "\n" + "Rating:\t\t" + driverRating;


        lowerText.setText(tripsData);

        //Create a calendar object with the depart time from the driverdepart information

        //Set the trip depart calendar to the specifications retrieved from previous activity
        departDrive.set(Calendar.YEAR, driverDepartYear);
        departDrive.set(Calendar.MONTH, driverDepartMonth); //driverDepartMonth has been adjusted to be on a 1-12 scale
        departDrive.set(Calendar.DAY_OF_MONTH, driverDepartDate);
        departDrive.set(Calendar.HOUR_OF_DAY, driverDepartHour);
        departDrive.set(Calendar.MINUTE, driverDepartMinute);
        departDrive.set(Calendar.SECOND, 0);
        departDrive.set(Calendar.MILLISECOND, 0);
        //set the countDown to equal trip depart time
        countDown.set(Calendar.YEAR, driverDepartYear);
        countDown.set(Calendar.MONTH, driverDepartMonth); //driverDepartMonth has been adjusted to be on a 1-12 scale
        countDown.set(Calendar.DAY_OF_MONTH, driverDepartDate);
        countDown.set(Calendar.HOUR_OF_DAY, driverDepartHour);
        countDown.set(Calendar.MINUTE, driverDepartMinute);
        countDown.set(Calendar.SECOND, 0);
        countDown.set(Calendar.MILLISECOND, 0);

        //Create values to be subtracted from countdown (depart time - current time)
        int negCurrentYear =  -1*currentTime.get(Calendar.YEAR);
        int negCurrentMonth =  -1*(currentTime.get(Calendar.MONTH) + 1);
        int negCurrentDate =  -1*currentTime.get(Calendar.DATE);
        int negCurrentHour =  -1*currentTime.get(Calendar.HOUR_OF_DAY);
        int negCurrentMinute =  -1*currentTime.get(Calendar.MINUTE);
        int negCurrentSecond =  -1*currentTime.get(Calendar.SECOND);
        //implement the subtraction (depart time - current time)
        countDown.add(Calendar.YEAR, negCurrentYear);
        countDown.add(Calendar.MONTH, negCurrentMonth);
        countDown.add(Calendar.DATE, negCurrentDate);
        countDown.add(Calendar.HOUR_OF_DAY, negCurrentHour);
        countDown.add(Calendar.MINUTE, negCurrentMinute);
        countDown.add(Calendar.SECOND, negCurrentSecond + 1);

        //Use a countdown timer to display the current time as it changes
        CountDownTimer newtimer = new CountDownTimer(1000000000, 1000) {
            @Override
            public void onTick(long l) {


                currentTime = Calendar.getInstance();

                countDown.add(Calendar.SECOND, -1);

                //set the values of the countdown
                int countdownMonth = countDown.get(Calendar.MONTH) + 1;
                int countdownDate = countDown.get(Calendar.DATE);
                int countdownHour = countDown.get(Calendar.HOUR_OF_DAY);
                int countdownMinute = countDown.get(Calendar.MINUTE);
                int countdownSecond = countDown.get(Calendar.SECOND);
                int countdownYear;
                if(driverDepartYear <= currentTime.get(Calendar.YEAR)) countdownYear = 0;
                else countdownYear = countDown.get(Calendar.YEAR);



                //ensuring the current time minutes are always double digits
                if(currentTime.get(Calendar.MINUTE) < 10) globalCurrentMinute = "0" + currentTime.get(Calendar.MINUTE);
                else globalCurrentMinute = "" + currentTime.get(Calendar.MINUTE);
                //ensuring the current time seconds are always double digits
                if(currentTime.get(Calendar.SECOND) < 10) globalCurrentSecond = "0" + currentTime.get(Calendar.SECOND);
                else globalCurrentSecond = "" + currentTime.get(Calendar.SECOND);
                //correcting the time to be 12 when the hour is 0
                if(currentTime.get(Calendar.HOUR) == 0) globalCurrentHour = "" + 12;
                else globalCurrentHour = "" + currentTime.get(Calendar.HOUR);
                //Adding the AM or PM to the end of the time
                if(currentTime.get(Calendar.HOUR_OF_DAY) > 11) globalCurrentAMPM = "PM";
                else globalCurrentAMPM = "AM";

                //ensuring the current time minutes are always double digits
                if(driverDepartMinute < 10) globalDepartMinute = "0" + driverDepartMinute;
                else globalDepartMinute = "" + driverDepartMinute;
                //correcting the time to be 12 when the hour is 0
                if(departDrive.get(Calendar.HOUR) == 0) globalDepartHour = "" + 12;
                else globalDepartHour = "" + departDrive.get(Calendar.HOUR);
                //Adding the AM or PM to the end of the time
                if(departDrive.get(Calendar.HOUR_OF_DAY) > 11) globalDepartAMPM = "PM";
                else globalDepartAMPM = "AM";


                //Set the text in currentTimeView

                String upperViewText ="Trip start:\t\t" + driverDepartDate + "/" +
                        driverDepartMonth+1 + "/" + driverDepartYear + " " + globalDepartHour + ":" +
                        globalDepartMinute + " " + globalDepartAMPM;

                /*currentTimeView.setText("Current time is:\t" + currentTime.get(Calendar.DATE) + "/" +
                        (currentTime.get(Calendar.MONTH)+1) + "/" + currentTime.get(Calendar.YEAR) +" "+ globalCurrentHour + ":" +
                        globalCurrentMinute + ":" + globalCurrentSecond + " " + globalCurrentAMPM + "\n\n" +
                        "Trip begins at:\t\t" + driverDepartDate + "/" +
                        driverDepartMonth + "/" + driverDepartYear + " " + globalDepartHour + ":" +
                        globalDepartMinute + " " + globalDepartAMPM);*/
                //Set the text in departView
                /*departTimeView.setText("Trip begins at:\t\t" + driverDepartDate + "/" +
                        driverDepartMonth + "/" + driverDepartYear + " " + globalDepartHour + ":" +
                        globalDepartMinute + " " + globalDepartAMPM);*/
                //Set the text in the countDownView
                if(departDrive.getTimeInMillis() < currentTime.getTimeInMillis()) {
                    upperViewText = "Trip has started!";
                }
                else{
                    upperViewText += "\n\nTime remaining until trip beings: " + "\n" +
                            countdownYear + " Years " +
                            "\n" +
                            countdownMonth + " Months " +
                            "\n" +
                            countdownDate + " Days " +
                            "\n" +
                            countdownHour + " Hours " +
                            "\n" +
                            countdownMinute + " Minutes " +
                            "\n" +
                            countdownSecond + " Seconds "
                    ;
                }

                upperText.setText(upperViewText);
                upperText.setGravity(Gravity.LEFT);

            }

            @Override
            public void onFinish() {

            }
        };
        newtimer.start();




    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        /***Start of Map Style code***/

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        /***End of Map Style code***/

        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        /**Driving directions retrieval**/
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                driverStartLat + ","+ driverStartLng
                + "&destination=" + driverDestLat + "," +
                driverDestLng +"&key=AIzaSyCLFyJNU5vVLDdvO8jbhsjZEcXgHnwHqyY";


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {


                    List<LatLng> latLngList = new ArrayList<>();
                    List<Marker> markerList = new ArrayList<>();
                    String status = response.getString("status");
                    if(status.equals("OK")){

                        Polyline polyline = null;

                        JSONArray jsonArray1 = response.getJSONArray("routes");
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                        JSONArray jsonArray2 = jsonObject1.getJSONArray("legs");
                        JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
                        JSONArray jsonArray3 = jsonObject2.getJSONArray("steps");
                        int numSteps = jsonArray3.length();
                        for(int i=0; i<numSteps; i++) {

                            JSONObject jsonObject3 = jsonArray3.getJSONObject(i);
                            JSONObject jsonObject4 = jsonObject3.getJSONObject("start_location");
                            double startLat = jsonObject4.getDouble("lat");
                            double startLng = jsonObject4.getDouble("lng");
                            latLngList.add(new LatLng(startLat, startLng));
                        }

                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(latLngList).clickable(true);
                        googleMap.addPolyline(polylineOptions).setColor(getApplicationContext().getResources().getColor(R.color.quantum_lightblue700));
                        //tripDurationSec = jsonObject3.getInt("value");

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLngList.get(0).latitude, latLngList.get(0).longitude), 16));

                        MarkerOptions markerOptionsStart = new MarkerOptions().position(new LatLng(latLngList.get(0).latitude, latLngList.get(0).longitude));

                        Marker markerStart = googleMap.addMarker(markerOptionsStart);
                        markerList.add(markerStart);

                        MarkerOptions markerOptionsEnd = new MarkerOptions().position(new LatLng(latLngList.get(latLngList.size()-1).latitude, latLngList.get(latLngList.size()-1).longitude));
                        Marker markerEnd = googleMap.addMarker(markerOptionsEnd);
                        markerList.add(markerEnd);
                    }
                    else{
                        makeText(riderMap.this, "Driving directions could not be found", LENGTH_SHORT).show();
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue.add(request);

        /**Driving directions retrieval end**/


        //Get location of the driver to show their position on the map

        final Marker[] markerDriver = {null};
        db.collection("Users").document(driverEmail).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if(e != null) {
                    Log.w(TAG, "Listen failed. ", e);
                    return;
                }
                if(documentSnapshot != null && documentSnapshot.exists()) {

                    double driverLat = documentSnapshot.getDouble("lat");
                    double driverLng = documentSnapshot.getDouble("lng");
                    MarkerOptions markerOptionsDriver = new MarkerOptions().position(new LatLng(driverLat, driverLng));
                    markerOptionsDriver.icon(BitmapDescriptorFactory.fromResource(R.drawable.dark_blue_car));
                    markerOptionsDriver.rotation((float) 37.9);
                    if(markerDriver[0] != null)
                        markerDriver[0].setPosition(new LatLng(driverLat, driverLng));
                    else{
                        markerDriver[0] = googleMap.addMarker(markerOptionsDriver);
                    }
                }
            }
        });
        final Marker[] markerRider = {null};
        db.collection("Users").document(userEmail).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if(e != null) {
                    Log.w(TAG, "Listen failed. ", e);
                    return;
                }
                if(documentSnapshot != null && documentSnapshot.exists()) {

                    double riderLat = documentSnapshot.getDouble("lat");
                    double riderLng = documentSnapshot.getDouble("lng");
                    double riderBearing = documentSnapshot.getDouble("bearing");
                    MarkerOptions markerOptionsRider = new MarkerOptions().position(new LatLng(riderLat, riderLng));
                    markerOptionsRider.icon(BitmapDescriptorFactory.fromResource(R.drawable.dark_blue_car));
                    markerOptionsRider.rotation((float) riderBearing);
                    markerOptionsRider.anchor((float) 0.5, (float) 0.5 );

                    if(markerRider[0] != null){
                        markerRider[0].setPosition(new LatLng(riderLat, riderLng));
                        markerRider[0].setRotation((float) riderBearing);
                        markerRider[0].setAnchor((float) 0.5, (float) 0.5);
                    }

                    else{
                        markerRider[0] = googleMap.addMarker(markerOptionsRider);
                    }
                }
            }
        });

        //overlay other views with the map view, and give information to the start of the trip
        // and other important information like distance from start, late or not, ETA, times, and stuff
        // (Going to require more http requests and JSON stuff, esp. to check if the driver is not on the route, and to re calc, but maybe that optional)
        //  as well as polylines from rider to the trip start location, and a polyline from the driver to the trip start location
        // a button to click that will center the camera to include the trip, the rider, and the driver, and maybe another one to
        // track where the driver is
        // Also: Have different modes for what the rider might want to see. Such as details in the google directions api JSON
        // make sure the app doesn't crash when the proper data is not in the database: always check for fields that are null (check diff betweeen poke food place and the 3rd entry to see prob)









    }
}