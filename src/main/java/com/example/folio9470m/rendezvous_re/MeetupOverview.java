package com.example.folio9470m.rendezvous_re;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MeetupOverview extends AppCompatActivity {
    private DatabaseReference myRef;

    private EditText nameText;
    private EditText locationText;
    private EditText timeText;
    private Meetup currentMeetup;
    private Rendezvous mApp;
    private double longitude;
    private double latitude;
    private  String argument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetup_overview);
        Button navButton = findViewById(R.id.navigation_button);
        mApp =((Rendezvous)getApplicationContext());



        myRef = FirebaseDatabase.getInstance().getReference().child("meetups");
        nameText = findViewById(R.id.nameText);
        locationText = findViewById(R.id.locationText);
        timeText = findViewById(R.id.timeText);

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot meetup: dataSnapshot.getChildren()){
                    if (meetup.getValue(Meetup.class).getName().equals(mApp.getMeetupName())){
                        currentMeetup = meetup.getValue(Meetup.class);
                    }
                }
                nameText.setText(currentMeetup.getName());
                locationText.setText(currentMeetup.getLocation());
                timeText.setText(currentMeetup.getTime());
                latitude = currentMeetup.getCoordinates().getLatitude();
                longitude =currentMeetup.getCoordinates().getLongitude();
                argument = "google.navigation:q="+latitude+","+longitude;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });




        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri gmmIntentUri = Uri.parse(argument);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(mapIntent);
                }

            }
        });




    }
}
