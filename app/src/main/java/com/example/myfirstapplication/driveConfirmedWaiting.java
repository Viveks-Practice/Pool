package com.example.myfirstapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class driveConfirmedWaiting extends AppCompatActivity {

    private static final String EXTRA_DEST = "ex_dest";
    private static final String EXTRA_START = "ex_start";
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
    private static final String EXTRA_FIRSTNAME = "ex_firstName";
    private static final String EXTRA_LASTNAME = "ex_lastName";
    private static final String EXTRA_CARMAKE = "ex_carMake";
    private static final String EXTRA_CARMODEL = "ex_carModel";
    private static final String EXTRA_CARYEAR = "ex_carYear";
    private static final String EXTRA_EXPERIENCE = "ex_experience";
    private static final String EXTRA_RATING = "ex_rating";
    private static final String EXTRA_PAGE_NUM = "ex_page_num";


    TextView infoTextView;
    TextView currentTimeTextView;
    TextView departTimeTextView;
    TextView countDownTextView;

    String globalDepartHour;
    String globalDepartMinute;
    String globalDepartSecond;
    String globalDepartAMPM;

    String globalCurrentHour;
    String globalCurrentMinute;
    String globalCurrentSecond;
    String globalCurrentAMPM;

    //Create all Calendar objects
    Calendar arriveDrive = Calendar.getInstance();
    Calendar departDrive = Calendar.getInstance();
    Calendar countDown  = Calendar.getInstance();
    Calendar currentTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drive_confirmed_waiting);


        infoTextView = findViewById(R.id.infoTextView);
        currentTimeTextView = findViewById(R.id.currentTimeTextView);
        departTimeTextView = findViewById(R.id.departTextView);
        countDownTextView = findViewById(R.id.countDownTextView);
        infoTextView.setTextSize(16);
        currentTimeTextView.setTextSize(16);
        departTimeTextView.setTextSize(16);
        countDownTextView.setTextSize(20);
        infoTextView.setPadding(10,0, 10, 0);
        currentTimeTextView.setPadding(10, 0, 10, 0);
        departTimeTextView.setPadding(10, 0, 10, 0);
        countDownTextView.setPadding(10, 0, 10, 0);

        /********************Start of retrieving data from previous activity************************/
        Intent intent = getIntent();
        String driverStartPoint = intent.getStringExtra(EXTRA_START);
        String driverDestination = intent.getStringExtra(EXTRA_DEST);
        String driverFirstName = intent.getStringExtra(EXTRA_FIRSTNAME);
        String driverLastName = intent.getStringExtra(EXTRA_LASTNAME);
        String driverCarMake = intent.getStringExtra(EXTRA_CARMAKE);
        String driverCarModel = intent.getStringExtra(EXTRA_CARMODEL);
        double driverCarYear = intent.getDoubleExtra(EXTRA_CARYEAR,0);
        int driverArriveDate = intent.getIntExtra(EXTRA_DATE_ARRIVE, 0);
        int driverArriveMonth = intent.getIntExtra(EXTRA_MONTH_ARRIVE, 0);
        int driverArriveYear = intent.getIntExtra(EXTRA_YEAR_ARRIVE, 0);
        int driverArriveHour = intent.getIntExtra(EXTRA_HOUR_ARRIVE, 0);
        int driverArriveMinute = intent.getIntExtra(EXTRA_MINUTE_ARRIVE, 0);
        final int driverDepartDate = intent.getIntExtra(EXTRA_DATE_DEPART, 0);
        final int driverDepartMonth = intent.getIntExtra(EXTRA_MONTH_DEPART, 0);
        final int driverDepartYear = intent.getIntExtra(EXTRA_YEAR_DEPART, 0);
        final int driverDepartHour = intent.getIntExtra(EXTRA_HOUR_DEPART, 0);
        final int driverDepartMinute = intent.getIntExtra(EXTRA_MINUTE_DEPART, 0);
        /********************End of retrieving data from previous activity************************/


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






        //Create a calendar object with the depart time from the driverdepart information

        //Set the trip depart calendar to the specifications retrieved from previous activity
        departDrive.set(Calendar.YEAR, driverDepartYear);
        departDrive.set(Calendar.MONTH, driverDepartMonth-1); //driverDepartMonth has been adjusted to be on a 1-12 scale
        departDrive.set(Calendar.DAY_OF_MONTH, driverDepartDate);
        departDrive.set(Calendar.HOUR_OF_DAY, driverDepartHour);
        departDrive.set(Calendar.MINUTE, driverDepartMinute);
        departDrive.set(Calendar.SECOND, 0);
        departDrive.set(Calendar.MILLISECOND, 0);

        String sMonthDepart = new DateFormatSymbols().getMonths()[departDrive.get(Calendar.MONTH)];
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);
        String weekDayDepart = dayFormat.format(departDrive.getTime());

        arriveDrive.set(Calendar.YEAR, driverArriveYear);
        arriveDrive.set(Calendar.MONTH, driverArriveMonth-1);
        arriveDrive.set(Calendar.DAY_OF_MONTH, driverArriveDate);
        arriveDrive.set(Calendar.HOUR_OF_DAY, driverArriveHour);
        arriveDrive.set(Calendar.MINUTE, driverArriveMinute);
        arriveDrive.set(Calendar.SECOND, 0);
        arriveDrive.set(Calendar.MILLISECOND, 0);

        String sMonthArrive = new DateFormatSymbols().getMonths()[arriveDrive.get(Calendar.MONTH)];
        String weekDayArrive = dayFormat.format(arriveDrive.getTime());

        String tripsData = "Your trip details: \n" + "Begin: \t\t" + driverStartPoint + "\n" + "End: \t\t\t" + driverDestination + "\n" +
                "Depart: \t" + weekDayDepart + ", " + sMonthDepart + " " + driverDepartDate +
                ", " + driverDepartYear + " @ " + driverDepart12Hour + ":" + driverDepartMin + " " + driverDepartAMPM +  "\n" +
                "Arrive: \t\t" + weekDayArrive + ", " + sMonthArrive + " " + driverArriveDate +
                ", " + driverArriveYear + " @ " + driverArrive12Hour  + ":" + driverArriveMin + " " + driverArriveAMPM
                //+ "\n\n"
                //+ "Driver information:\n" + "Name: " + driverFirstName + " " + driverLastName + "\n" +
                // "Car: " + driverCarMake + " " + driverCarModel + " " + (int) driverCarYear
                ;
        infoTextView.setText(tripsData);

        //set the countDown to equal trip depart time
        countDown.set(Calendar.YEAR, driverDepartYear);
        countDown.set(Calendar.MONTH, driverDepartMonth-1); //driverDepartMonth has been adjusted to be on a 1-12 scale
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
                currentTimeTextView.setText("Current time is:\t\t"+ currentTime.get(Calendar.DATE) + "/" +
                        (currentTime.get(Calendar.MONTH)+1) + "/" + currentTime.get(Calendar.YEAR) + " " + globalCurrentHour + ":" +
                        globalCurrentMinute + ":" + globalCurrentSecond + " " + globalCurrentAMPM);
                //Set the text in departView
                departTimeTextView.setText("Trip begins at:\t\t\t" + driverDepartDate + "/" +
                        driverDepartMonth + "/" + driverDepartYear + " " + globalDepartHour + ":" +
                        globalDepartMinute + " " + globalDepartAMPM);
                //Set the text in the countDownView

                if(departDrive.getTimeInMillis() < currentTime.getTimeInMillis()){
                    countDownTextView.setText("Trip has started!");
                }
                else{
                    countDownTextView.setText("Time remaining until trip beings: " + "\n\n" +
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
                    );
                }



            }

            @Override
            public void onFinish() {

            }
        };
        newtimer.start();


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