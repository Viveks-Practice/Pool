package com.example.myfirstapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class addRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private PlaceInfo destRideAutoComplete;
    private PlaceInfo startRideAutoComplete;

    private Button buttonToLobby;
    private static final String TAG = "poolDetails";
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

    private static final String EXTRA_FIRSTNAME = "ex_firstName";
    private static final String EXTRA_LASTNAME = "ex_lastName";
    private static final String EXTRA_CARMAKE = "ex_carMake";
    private static final String EXTRA_CARMODEL = "ex_carModel";
    private static final String EXTRA_CARYEAR = "ex_carYear";
    private static final String EXTRA_EXPERIENCE = "ex_experience";
    private static final String EXTRA_RATING = "ex_rating";
    private static final String EXTRA_PAGE_NUM = "ex_page_num";






    EditText strayRiderStartField;
    EditText strayRiderDestField;
    Button arriveDateField;
    Button arriveTimeField;
    int riderGlobalYear;
    int riderGlobalMonth;
    int riderGlobalDayOfMonth;
    int riderGlobalHour;
    int riderGlobalMinute;


    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    String userEmail = fAuth.getCurrentUser().getEmail();
    String userFirstName;
    String userLastName;

    int tripDurationSec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        buttonToLobby = findViewById(R.id.buttonDriverLobby);
        buttonToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                save();
            }
        });

        arriveTimeField = findViewById(R.id.riderArriveTime);
        arriveDateField = findViewById(R.id.riderArriveDate);
        strayRiderStartField = findViewById(R.id.strayRiderStart);
        strayRiderDestField = findViewById(R.id.strayRiderEnd);

        //Retrieve the user's first and last name for the GFSDS/GFSDD entries
        db.collection("Users").document(userEmail).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    userFirstName = documentSnapshot.getString("firstName");
                    userLastName = documentSnapshot.getString("lastName");

                } else {
                    makeText(addRide.this, "user info retrieval failed", LENGTH_SHORT).show();
                }
            }
        });



        /**
         * The following is the START of Places related material
         */

        destRideAutoComplete = new PlaceInfo();
        startRideAutoComplete = new PlaceInfo();

        String apikey= "AIzaSyCLFyJNU5vVLDdvO8jbhsjZEcXgHnwHqyY";

        if(!Places.isInitialized())
            Places.initialize(getApplicationContext(), apikey);


        final AutocompleteSupportFragment destRideAuto =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_ride);
        //This below line dictates what the "onPlaceSelected" method will return. I guess they
        //must be specified so that memory  is saved if all details are not used.
        destRideAuto.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER, Place.Field.RATING,
                Place.Field.WEBSITE_URI));
        SpannableString destHint = new SpannableString("Enter your destination");
        destHint.setSpan(new UnderlineSpan(), 0, destHint.length(), 0);
        destRideAuto.setHint(destHint);

        destRideAuto.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                try {
                    destRideAutoComplete.setName(place.getName());
                    destRideAutoComplete.setAddress(place.getAddress());
                    destRideAutoComplete.setPhoneNumber(place.getPhoneNumber());
                    destRideAutoComplete.setId(place.getId());
                    destRideAutoComplete.setWebsiteUri(place.getWebsiteUri());
                    destRideAutoComplete.setLatlng(place.getLatLng());
                    destRideAutoComplete.setRating(place.getRating());
                    destRideAutoComplete.setAttributions(place.getAttributions());



                    Log.i("PlaceApi", "onPlaceSelected: " + destRideAutoComplete.toString());
                }
                catch(NullPointerException e){
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        final AutocompleteSupportFragment startRideAuto =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_ride_start);

        startRideAuto.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER, Place.Field.RATING,
                Place.Field.WEBSITE_URI));
        SpannableString startHint = new SpannableString("Enter your start point");
        startHint.setSpan(new UnderlineSpan(), 0, startHint.length(), 0);
        startRideAuto.setHint(startHint);

        startRideAuto.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                try {
                    startRideAutoComplete.setName(place.getName());
                    startRideAutoComplete.setAddress(place.getAddress());
                    startRideAutoComplete.setPhoneNumber(place.getPhoneNumber());
                    startRideAutoComplete.setId(place.getId());
                    startRideAutoComplete.setWebsiteUri(place.getWebsiteUri());
                    startRideAutoComplete.setLatlng(place.getLatLng());
                    startRideAutoComplete.setRating(place.getRating());
                    startRideAutoComplete.setAttributions(place.getAttributions());



                    Log.i("PlaceApi", "onPlaceSelected: " + startRideAutoComplete.toString());
                }
                catch(NullPointerException e){
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        /**
         * Thus ENDS Places related material
         */
    }


    public void openRideTrips() {
        Intent intent = new Intent(this, tabbedActivity.class);
        startActivity(intent);

    }

    public void save () {

        String dest = destRideAutoComplete.getName();
        String start = startRideAutoComplete.getName();
        if(dest==null)
            dest="";
        if(start==null)
            start="";

        String sArriveTime = arriveTimeField.getText().toString();
        String sArriveDate = arriveDateField.getText().toString();
        String sStrayStart = strayRiderStartField.getText().toString();
        String sStrayEnd = strayRiderDestField.getText().toString();



        if( dest.equals("") ||
            start.equals("") ||
            sArriveTime.equals("") ||
            sArriveDate.equals("") ||
            sStrayStart.equals("") ||
            sStrayEnd.equals("")
        ) {
            makeText(getApplicationContext(),"Please complete the form", LENGTH_SHORT).show();
        }
        else {

            RequestQueue queue = Volley.newRequestQueue(this);

            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                    startRideAutoComplete.getLatlng().latitude + ","+ startRideAutoComplete.getLatlng().longitude
                    + "&destination=" + destRideAutoComplete.getLatlng().latitude + "," +
                    destRideAutoComplete.getLatlng().longitude +"&key=AIzaSyCLFyJNU5vVLDdvO8jbhsjZEcXgHnwHqyY";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        String status = response.getString("status");
                        if(status.equals("OK")){


                            Intent intent = getIntent();
                            String riderFirstName = intent.getStringExtra(EXTRA_FIRSTNAME);
                            String riderLastName = intent.getStringExtra(EXTRA_LASTNAME);
                            String riderCarMake = intent.getStringExtra(EXTRA_CARMAKE);
                            String riderCarModel = intent.getStringExtra(EXTRA_CARMODEL);
                            double riderCarYear = intent.getDoubleExtra(EXTRA_CARYEAR, 0);
                            double riderExperience = intent.getDoubleExtra(EXTRA_EXPERIENCE, 0);
                            double riderRating = intent.getDoubleExtra(EXTRA_RATING, 0);


                            JSONArray jsonArray1 = response.getJSONArray("routes");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("legs");
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("duration");
                            tripDurationSec = jsonObject3.getInt("value");

                            String dest = destRideAutoComplete.getName();
                            String start = startRideAutoComplete.getName();

                            double strayRiderStart = Double.parseDouble(strayRiderStartField.getText().toString());
                            double strayRiderDest = Double.parseDouble(strayRiderDestField.getText().toString());



                            Calendar arriveCal = Calendar.getInstance();
                            arriveCal.set(Calendar.YEAR, riderGlobalYear);
                            arriveCal.set(Calendar.MONTH, riderGlobalMonth);
                            arriveCal.set(Calendar.DAY_OF_MONTH, riderGlobalDayOfMonth);
                            arriveCal.set(Calendar.HOUR_OF_DAY, riderGlobalHour);
                            arriveCal.set(Calendar.MINUTE, riderGlobalMinute);
                            arriveCal.set(Calendar.SECOND, 0);
                            arriveCal.set(Calendar.MILLISECOND, 0);


                            Date today = arriveCal.getTime();
                            Timestamp tsTimeDate = new Timestamp(today);


                            double rideDestLat = destRideAutoComplete.getLatlng().latitude;
                            double rideDestLng = destRideAutoComplete.getLatlng().longitude;
                            double rideStartLat = startRideAutoComplete.getLatlng().latitude;
                            double rideStartLng = startRideAutoComplete.getLatlng().longitude;


                            Map<String, Object> rideTrip = new HashMap<>();
                            rideTrip.put(KEY_RIDER_DESTINATION, dest);
                            rideTrip.put(KEY_RIDER_DEST_LAT, rideDestLat);
                            rideTrip.put(KEY_RIDER_DEST_LNG, rideDestLng);
                            rideTrip.put(KEY_RIDER_ARRIVE_TIMESTAMP, tsTimeDate);
                            rideTrip.put(KEY_RIDER_STRAY_START, strayRiderStart);
                            rideTrip.put(KEY_RIDER_STRAY_DEST, strayRiderDest);
                            rideTrip.put(KEY_RIDER_START_POINT, start);
                            rideTrip.put(KEY_RIDER_START_LAT, rideStartLat);
                            rideTrip.put(KEY_RIDER_START_LNG, rideStartLng);
                            rideTrip.put(KEY_RIDER_FIRST_NAME, riderFirstName);
                            rideTrip.put(KEY_RIDER_LAST_NAME, riderLastName);
                            rideTrip.put(KEY_RIDER_CAR_MAKE, riderCarMake);
                            rideTrip.put(KEY_RIDER_CAR_MODEL, riderCarModel);
                            rideTrip.put(KEY_RIDER_CAR_YEAR, riderCarYear);
                            rideTrip.put(KEY_RIDER_EXPERIENCE, riderExperience);
                            rideTrip.put(KEY_RIDER_RATING, riderRating);

                            //The below creates a virtual document with no fields that cannot be referenced
                            //unless a subcollection of it is referenced? Hmm I hope this works
                            db.collection("Rider").document(userEmail)
                                    .collection("Rides").document(
                                    arriveCal.get(Calendar.DATE) + "_" +
                                            (arriveCal.get(Calendar.MONTH)+1) + "_"+
                                            arriveCal.get(Calendar.YEAR) + "_" +
                                            arriveCal.get(Calendar.HOUR_OF_DAY) + ":" +
                                            arriveCal.get(Calendar.MINUTE) + " " +
                                            dest + " " +
                                            userEmail
                            )
                                    .set(rideTrip).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    makeText(addRide.this, "Ride details saved", LENGTH_SHORT).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            makeText(addRide.this, "Error!", LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }
                                    });

                            openRideTrips();

                        }
                        else{
                            makeText(addRide.this, "That trip is not accessible via driving", LENGTH_SHORT).show();
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


        }
    }



    public void riderTimePicker(View view) {
        DialogFragment rideTimePicker = new TimePickerFragment();
        rideTimePicker.show(getSupportFragmentManager(), "ride time picker");
    }

    public void riderDatePicker(View view) {
        DialogFragment rideDatePicker = new DatePickerFragment();
        rideDatePicker.show(getSupportFragmentManager(), "ride date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        riderGlobalDayOfMonth = dayOfMonth;
        riderGlobalMonth = month;
        riderGlobalYear = year;

        TextView textView = findViewById(R.id.riderArriveDate);
        textView.setText(currentDateString);
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        riderGlobalHour = hourOfDay;
        riderGlobalMinute = minute;

        /*******************Rider arrive Mods - Start********************/
        //Determining whether or not to show AM or PM for the rider arrive time details
        String riderArriveAMPM = "AM";
        if(hourOfDay > 11) riderArriveAMPM = "PM";

        //Change the rider arrive value from 24 hours to 12 hours
        int riderArrive12Hour;
        if(hourOfDay > 12) riderArrive12Hour = hourOfDay - 12;
        else riderArrive12Hour = hourOfDay;

        //Ensure the rider arrive hour doesn't show a zero
        if(riderArrive12Hour == 0) riderArrive12Hour = 12 ;

        //Ensure the rider arrive minutes are displayed in double digits
        String riderArriveMin;
        if(minute < 10) riderArriveMin = "0" + minute;
        else riderArriveMin = "" + minute;
        /*******************Rider arrive Mods - End********************/

        TextView textView = findViewById(R.id.riderArriveTime);
        textView.setText(riderArrive12Hour + ":" + riderArriveMin + " " + riderArriveAMPM);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tabbedActivity.class);
        intent.putExtra(EXTRA_PAGE_NUM, 0);
        startActivity(intent);
        finish();
    }
}
