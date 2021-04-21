package com.example.myfirstapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
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
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class addDrive extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "fuelDetails";
    private static final String KEY_DRIVER_DEPART_TIMESTAMP = "departTimeStamp";
    private static final String KEY_DRIVER_ARRIVE_TIMESTAMP = "arriveTimeStamp";
    private static final String KEY_DRIVER_DESTINATION = "dest";
    private static final String KEY_DRIVER_DEST_LAT= "destLat";
    private static final String KEY_DRIVER_DEST_LNG = "destLng";
    private static final String KEY_DRIVER_MAXTIP = "maxTip";
    private static final String KEY_DRIVER_MINTIP = "minTip";
    private static final String KEY_DRIVER_RIDER_COUNT = "riderCount";
    private static final String KEY_DRIVER_SEATS_AVAIL = "seatsAvail";
    private static final String KEY_DRIVER_START_POINT = "startPoint";
    private static final String KEY_DRIVER_START_LAT = "startLat";
    private static final String KEY_DRIVER_START_LNG = "startLng";
    private static final String KEY_DRIVER_FIRST_NAME = "firstName";
    private static final String KEY_DRIVER_LAST_NAME = "lastName";
    private static final String KEY_DRIVER_CAR_MAKE = "carMake";
    private static final String KEY_DRIVER_CAR_MODEL = "carModel";
    private static final String KEY_DRIVER_CAR_YEAR = "carYear";
    private static final String KEY_DRIVER_EXPERIENCE = "experience";
    private static final String KEY_DRIVER_RATING = "rating";
    private static final String KEY_DRIVER_EMAIL = "email";
    private static final String KEY_DRIVER_DRIVE_CONFIRMED = "driveConfirmed";

    private static final String EXTRA_FIRSTNAME = "ex_firstName";
    private static final String EXTRA_LASTNAME = "ex_lastName";
    private static final String EXTRA_CARMAKE = "ex_carMake";
    private static final String EXTRA_CARMODEL = "ex_carModel";
    private static final String EXTRA_CARYEAR = "ex_carYear";
    private static final String EXTRA_EXPERIENCE = "ex_experience";
    private static final String EXTRA_RATING = "ex_rating";
    private static final String EXTRA_PAGE_NUM = "ex_page_num";



    private Button buttonToLobby;
    private PlaceInfo destAutoComplete;
    private PlaceInfo startAutoComplete;

    Button departTimeField;
    Button departDateField;
    EditText seatsField;
    EditText minTipField;
    EditText maxTipField;
    int globalYear;
    int globalMonth;
    int globalDayOfMonth;
    int globalHour;
    int globalMinute;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser firebaseUser = fAuth.getCurrentUser();
    public String userEmail = fAuth.getCurrentUser().getEmail();
    String userFirstName;
    String userLastName;

    LocationManager locationManager;
    LocationListener addDrivelocationListener;
    double addLatitude;
    double addLongitude;

    private Toolbar toolbarAddDrive;

    private FusedLocationProviderClient fusedLocationClient;
    int tripDurationSec = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drive);

        toolbarAddDrive = findViewById(R.id.toolbarAddDrive);
        setSupportActionBar(toolbarAddDrive);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");


        departTimeField = findViewById(R.id.driverDepartTime);
        departDateField = findViewById(R.id.driverDepartDate);
        seatsField = findViewById(R.id.seatsText);
        minTipField = findViewById(R.id.minTipText);
        maxTipField = findViewById(R.id.maxTipText);


        /**
         * The following is the START of Places related code
         */

        destAutoComplete = new PlaceInfo();
        startAutoComplete = new PlaceInfo();

        String apikey= "AIzaSyCLFyJNU5vVLDdvO8jbhsjZEcXgHnwHqyY";

        if(!Places.isInitialized())
            Places.initialize(getApplicationContext(), apikey);


        final AutocompleteSupportFragment autocompleteDest =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);


        //This below line dictates what the "onPlaceSelected" method will return. I guess they
        //must be specified so that memory  is saved if all details are not used.
        autocompleteDest.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER, Place.Field.RATING,
                Place.Field.WEBSITE_URI));
        SpannableString destHint = new SpannableString("Enter your destination");
        destHint.setSpan(new UnderlineSpan(), 0, destHint.length(), 0);
        autocompleteDest.setHint(destHint);

        autocompleteDest.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                try {
                    destAutoComplete.setName(place.getName());
                    destAutoComplete.setAddress(place.getAddress());
                    destAutoComplete.setPhoneNumber(place.getPhoneNumber());
                    destAutoComplete.setId(place.getId());
                    destAutoComplete.setWebsiteUri(place.getWebsiteUri());
                    destAutoComplete.setLatlng(place.getLatLng());
                    destAutoComplete.setRating(place.getRating());
                    destAutoComplete.setAttributions(place.getAttributions());






                    Log.i("PlaceApi", "onPlaceSelected: " + destAutoComplete.toString());
                }
                catch(NullPointerException e){
                    Log.e(TAG, e.getMessage());
                }
            }

            @Override
            public void onError(@NonNull Status status) {

            }
        });

        final AutocompleteSupportFragment autocompleteStart =
                (AutocompleteSupportFragment) getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment_startpoint);
        autocompleteStart.setPlaceFields(Arrays.asList(Place.Field.ID,Place.Field.LAT_LNG,
                Place.Field.NAME, Place.Field.ADDRESS, Place.Field.PHONE_NUMBER, Place.Field.RATING,
                Place.Field.WEBSITE_URI));
        SpannableString startHint = new SpannableString("Enter your start point");
        startHint.setSpan(new UnderlineSpan(), 0, startHint.length(), 0);
        autocompleteStart.setHint(startHint);


        autocompleteStart.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                try {
                    startAutoComplete.setName(place.getName());
                    startAutoComplete.setAddress(place.getAddress());
                    startAutoComplete.setPhoneNumber(place.getPhoneNumber());
                    startAutoComplete.setId(place.getId());
                    startAutoComplete.setWebsiteUri(place.getWebsiteUri());
                    startAutoComplete.setLatlng(place.getLatLng());
                    startAutoComplete.setRating(place.getRating());
                    startAutoComplete.setAttributions(place.getAttributions());



                    Log.i("PlaceApi", "onPlaceSelected: " + startAutoComplete.toString());
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
         * Thus ENDS Places related code
         */

        buttonToLobby = ( Button ) findViewById(R.id.buttonPoolLobby);
        buttonToLobby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1){
                saveDriveTrip();
            }
        });
    }


    public void openDriveTrips() {
        Intent intent = new Intent(this, tabbedActivity.class);
        intent.putExtra(EXTRA_PAGE_NUM, 1);
        startActivity(intent);
    }


    public void saveDriveTrip() {


        String dest = destAutoComplete.getName();
        String start = startAutoComplete.getName();

        String sDepartTime = departTimeField.getText().toString();
        String sDepartDate = departDateField.getText().toString();
        String sSeats = seatsField.getText().toString();
        String sMinTip = minTipField.getText().toString();
        String sMaxTip = maxTipField.getText().toString();

        if(dest == null)
            dest = "";
        if(start == null)
            start = "";


        if(     dest.equals("") ||
                start.equals("") ||
                sDepartTime.equals("") ||
                sDepartDate.equals("") ||
                sSeats.equals("") ||
                sMinTip.equals("")||
                sMaxTip.equals("")) {
            makeText(getApplicationContext(),"Please complete the form" , LENGTH_SHORT).show();
        }
        else {//If the fields are filled out, save the values to the database


            /* HTTP retrieval of trip duration code via directions API call below */

            final TextView volleyView = findViewById(R.id.whatAVolley);

            RequestQueue queue = Volley.newRequestQueue(this);
            volleyView.setText("empty");

            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +
                    startAutoComplete.getLatlng().latitude + ","+ startAutoComplete.getLatlng().longitude
                    + "&destination=" + destAutoComplete.getLatlng().latitude + "," +
                    destAutoComplete.getLatlng().longitude +"&key=AIzaSyCLFyJNU5vVLDdvO8jbhsjZEcXgHnwHqyY";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {

                        /*JSONArray statusArray = response.getJSONArray("status");
                        JSONObject statusObject = statusArray.getJSONObject(0);*/

                        String status = response.getString("status");
                        if(status.equals("OK")){


                            Intent intent = getIntent();
                            String firstName = intent.getStringExtra(EXTRA_FIRSTNAME);
                            String lastName = intent.getStringExtra(EXTRA_LASTNAME);
                            String carMake = intent.getStringExtra(EXTRA_CARMAKE);
                            String carModel = intent.getStringExtra(EXTRA_CARMODEL);
                            double carYear = intent.getDoubleExtra(EXTRA_CARYEAR, 0);
                            double experience = intent.getDoubleExtra(EXTRA_EXPERIENCE, 0);
                            double rating = intent.getDoubleExtra(EXTRA_RATING, 0);


                            JSONArray jsonArray1 = response.getJSONArray("routes");
                            JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                            JSONArray jsonArray2 = jsonObject1.getJSONArray("legs");
                            JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
                            JSONObject jsonObject3 = jsonObject2.getJSONObject("duration");
                            tripDurationSec = jsonObject3.getInt("value");
                            volleyView.setText(String.valueOf(tripDurationSec));

                            //Inside the retrieval of the JSON data add the document to the database
                            String dest = destAutoComplete.getName();
                            String start = startAutoComplete.getName();
                            String sDepartTime = departTimeField.getText().toString();
                            String sDepartDate = departDateField.getText().toString();
                            String sSeats = seatsField.getText().toString();
                            String sMinTip = minTipField.getText().toString();
                            String sMaxTip = maxTipField.getText().toString();


                            double seats = Double.parseDouble(seatsField.getText().toString());
                            double minTip = Double.parseDouble(minTipField.getText().toString());
                            double maxTip = Double.parseDouble(maxTipField.getText().toString());

                            Calendar departDrive = Calendar.getInstance();
                            departDrive.set(Calendar.YEAR, globalYear);
                            departDrive.set(Calendar.MONTH, globalMonth);
                            departDrive.set(Calendar.DAY_OF_MONTH, globalDayOfMonth);
                            departDrive.set(Calendar.HOUR_OF_DAY, globalHour);
                            departDrive.set(Calendar.MINUTE, globalMinute);
                            departDrive.set(Calendar.SECOND, 0);
                            departDrive.set(Calendar.MILLISECOND, 0);

                            Date departDate = departDrive.getTime();
                            Timestamp departTimeDate = new Timestamp(departDate);

                            Calendar arrivalDrive = Calendar.getInstance();
                            arrivalDrive.set(Calendar.YEAR, globalYear);
                            arrivalDrive.set(Calendar.MONTH, globalMonth);
                            arrivalDrive.set(Calendar.DAY_OF_MONTH, globalDayOfMonth);
                            arrivalDrive.set(Calendar.HOUR_OF_DAY, globalHour);
                            arrivalDrive.set(Calendar.MINUTE, globalMinute);
                            arrivalDrive.set(Calendar.SECOND, 0);
                            arrivalDrive.set(Calendar.MILLISECOND, 0);

                            int tripDurationMin = tripDurationSec/60;

                            arrivalDrive.add(Calendar.MINUTE, tripDurationMin);

                            Date arriveDate = arrivalDrive.getTime();
                            Timestamp arriveTimeDate = new Timestamp(arriveDate);

                            double destLat = destAutoComplete.getLatlng().latitude;
                            double destLng = destAutoComplete.getLatlng().longitude;
                            double startLat = startAutoComplete.getLatlng().latitude;
                            double startLng = startAutoComplete.getLatlng().longitude;


                            Map<String, Object> driveTrip = new HashMap<>();
                            driveTrip.put(KEY_DRIVER_DESTINATION, dest);
                            driveTrip.put(KEY_DRIVER_DEST_LAT, destLat);
                            driveTrip.put(KEY_DRIVER_DEST_LNG, destLng);
                            driveTrip.put(KEY_DRIVER_DEPART_TIMESTAMP, departTimeDate);
                            driveTrip.put(KEY_DRIVER_ARRIVE_TIMESTAMP, arriveTimeDate);
                            driveTrip.put(KEY_DRIVER_START_POINT, start);
                            driveTrip.put(KEY_DRIVER_START_LAT, startLat);
                            driveTrip.put(KEY_DRIVER_START_LNG, startLng);
                            driveTrip.put(KEY_DRIVER_SEATS_AVAIL, seats);
                            driveTrip.put(KEY_DRIVER_MINTIP, minTip);
                            driveTrip.put(KEY_DRIVER_MAXTIP, maxTip);
                            driveTrip.put(KEY_DRIVER_RIDER_COUNT, 0);
                            driveTrip.put(KEY_DRIVER_FIRST_NAME, firstName);
                            driveTrip.put(KEY_DRIVER_LAST_NAME, lastName);
                            driveTrip.put(KEY_DRIVER_CAR_MAKE, carMake);
                            driveTrip.put(KEY_DRIVER_CAR_MODEL, carModel);
                            driveTrip.put(KEY_DRIVER_CAR_YEAR, carYear);
                            driveTrip.put(KEY_DRIVER_EXPERIENCE, experience);
                            driveTrip.put(KEY_DRIVER_RATING, rating);
                            driveTrip.put(KEY_DRIVER_EMAIL, userEmail);
                            driveTrip.put(KEY_DRIVER_DRIVE_CONFIRMED, false);



                            db.collection("Drive").document(userEmail).
                                    collection("Drives").document(
                                    arrivalDrive.get(Calendar.DATE) + "_" +
                                            (arrivalDrive.get(Calendar.MONTH)+1) + "_"+
                                            arrivalDrive.get(Calendar.YEAR) + "_" +
                                            arrivalDrive.get(Calendar.HOUR_OF_DAY) + ":" +
                                            arrivalDrive.get(Calendar.MINUTE) + " " +
                                            dest + " " +
                                            userEmail
                            )
                                    .set(driveTrip).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    makeText(addDrive.this, "Driving Trip details saved", LENGTH_SHORT).show();
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            makeText(addDrive.this, "Error!", LENGTH_SHORT).show();
                                            Log.d(TAG, e.toString());
                                        }
                                    });


                            openDriveTrips();


                        }
                        else{
                            status = "NOT OK";
                            volleyView.setText(status);
                            makeText(addDrive.this, "That trip is not accessible via driving", LENGTH_SHORT).show();
                        }


                    } catch (JSONException e) {
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


            /* HTTP retrieval of trip duration code via directions API call above */

        }
    }
    /***************************** Below *********************************/
    /**************Toolbar/Menu configurations and imports***************/
    /********************************************************************/
    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_drive_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.check:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

    /***************************** Above *********************************/
    /**************Toolbar/Menu configurations and imports***************/
    /********************************************************************/

    public void timePicker(View view) {
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }

    public void datePicker(View view) {

        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), "date picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        globalDayOfMonth = dayOfMonth;
        globalMonth = month;
        globalYear = year;

        TextView textView = findViewById(R.id.driverDepartDate);
        textView.setText(currentDateString);
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        globalHour = hourOfDay;
        globalMinute = minute;

        /*******************Driver arrive Mods - Start********************/
        //Determining whether or not to show AM or PM for the driver depart time details
        String driverDepartAMPM = "AM";
        if(hourOfDay > 11) driverDepartAMPM = "PM";

        //Change the driver depart value from 24 hours to 12 hours
        int driverDepart12Hour;
        if(hourOfDay > 12) driverDepart12Hour = hourOfDay - 12;
        else driverDepart12Hour = hourOfDay;

        //Ensure the driver depart hour doesn't show a zero
        if(driverDepart12Hour == 0) driverDepart12Hour = 12;

        //Ensure the driver depart minutes are displayed in double digits
        String driverDepartMin;
        if(minute < 10) driverDepartMin = "0" + minute;
        else driverDepartMin = "" + minute;
        /*******************Driver arrive Mods - End********************/

        TextView textView = findViewById(R.id.driverDepartTime);
        textView.setText(driverDepart12Hour + ":" + driverDepartMin + " " + driverDepartAMPM);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, tabbedActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(EXTRA_PAGE_NUM, 1);
        startActivity(intent);
        finish();
    }
}

