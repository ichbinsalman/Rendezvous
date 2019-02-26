package com.example.folio9470m.rendezvous_re;

import android.content.Intent;
import android.net.Uri;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MeetupOverview extends AppCompatActivity {

    private DatabaseReference myRef;

    private TextView nameText;
    private TextView locationText;
    private TextView timeText;
    private TextView dateText;
    private Button navButton;
    private Button alertButton;



    private Meetup currentMeetup;
    private Rendezvous mApp;
    private double longitude;
    private double latitude;
    private String argument;



    private String time;
    private String date;
    private String[] dateArray;
    private String[] timeArray;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int min;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetup_overview);
        navButton = findViewById(R.id.navigation_button);
        mApp =((Rendezvous)getApplicationContext());
        alertButton = findViewById(R.id.alert_button);





        myRef = FirebaseDatabase.getInstance().getReference().child("meetups");
        nameText = findViewById(R.id.nameText);
        locationText = findViewById(R.id.locationText);
        timeText = findViewById(R.id.timeText);
        dateText = findViewById(R.id.dateText);

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
                dateText.setText(currentMeetup.getDate());
                latitude = currentMeetup.getCoordinates().getLatitude();
                longitude =currentMeetup.getCoordinates().getLongitude();
                time = currentMeetup.getTime();
                date = currentMeetup.getDate();
                argument = "google.navigation:q="+latitude+","+longitude;
                dateArray = date.split("-");
                timeArray = time.split(":");
                day = Integer.parseInt(dateArray[0]);
                month = Integer.parseInt(dateArray[1]);
                year = Integer.parseInt(dateArray[2]);
                hour = Integer.parseInt(timeArray[0]);
                min = Integer.parseInt(timeArray[1]);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });









        alertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar beginTime = Calendar.getInstance();
                beginTime.set(year, (month-1), day, hour, min);
                Calendar endTime = Calendar.getInstance();
                endTime.set(year, (month-1), day, (hour+1), min);
                Intent intent = new Intent(Intent.ACTION_INSERT)
                        .setData(Events.CONTENT_URI)
                        .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                        .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                        .putExtra(Events.TITLE, currentMeetup.getName())
                        .putExtra(Events.DESCRIPTION, "Hangout")
                        .putExtra(Events.EVENT_LOCATION, currentMeetup.getLocation())
                        .putExtra(Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
                        .putExtra(Intent.EXTRA_EMAIL, "one@example.com,two@example.com");
                startActivity(intent);
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
