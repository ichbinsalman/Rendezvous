package com.example.folio9470m.rendezvous_re;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class CurrMeetupsActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference mDatabase;
   // private Meetup m3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_curr_meetups);
        database = FirebaseDatabase.getInstance();
        mDatabase = database.getReference().child("meetups");

      //  Meetup m1 = new Meetup("one","islamabad");
      //  Meetup m2 = new Meetup("two", "karachi");
      //  mDatabase.child("m1").setValue(m1);
      //  mDatabase.child("m2").setValue(m2);

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                ArrayList<Meetup> meetupList = new ArrayList<>();
                for(DataSnapshot meetupSnapshot : dataSnapshot.getChildren()){
                    meetupList.add(meetupSnapshot.getValue(Meetup.class));
                }
                ListView mList = findViewById(R.id.listView);
                MeetupListAdapter adapter = new MeetupListAdapter(CurrMeetupsActivity.this, R.layout.adapter_view_layout,meetupList);
                mList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });












    }
}
