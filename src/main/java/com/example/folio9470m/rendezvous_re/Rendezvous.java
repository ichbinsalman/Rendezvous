package com.example.folio9470m.rendezvous_re;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class Rendezvous extends Application {

    private String meetupName;
    private String meetupToBeSaved;

    public String getMeetupToBeSaved() {
        return meetupToBeSaved;
    }

    public void setMeetupToBeSaved(String meetupToBeSaved) {
        this.meetupToBeSaved = meetupToBeSaved;
    }

    public String getMeetupName() {
        return meetupName;
    }

    public void setMeetupName(String meetupName) {
        this.meetupName = meetupName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        meetupName = "one";
        meetupToBeSaved = "m5";
    }
}
