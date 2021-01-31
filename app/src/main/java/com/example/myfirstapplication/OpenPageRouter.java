package com.example.myfirstapplication;

import android.app.Application;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class OpenPageRouter extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();


        if(firebaseUser != null) {
            Intent i = new Intent(OpenPageRouter.this, tabbedActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);

        }
    }
}
//shit - I'm trying to make the tabbed actvity the new homepage, but the back button is not working properly
// it keeps going to the login page
//Also currently, when I hit back from the tabbedhomepage, it closes app. And then re opening app takes me to login page. Wtf
// i am trying to mod the following pages: homepage, tabbedActivity, manifest, loginPage, openPageRouter, and firsttimeLoginPage.