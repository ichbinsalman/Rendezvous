package com.example.folio9470m.rendezvous_re;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class Rendezvous extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
