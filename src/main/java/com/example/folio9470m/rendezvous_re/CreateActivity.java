package com.example.folio9470m.rendezvous_re;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;



public class CreateActivity extends AppCompatActivity implements
        View.OnClickListener {

    private Button btnDatePicker, btnTimePicker;
    private EditText txtDate, txtTime;
    private EditText nameText;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String mID;
    private ImageButton locationButton;

    private FirebaseDatabase database;
    private DatabaseReference meetupDatabase;
    private DatabaseReference totalDatabase;
    private Rendezvous mApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mApp =((Rendezvous)getApplicationContext());

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        nameText = (EditText) findViewById(R.id.nameyyy);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        database = FirebaseDatabase.getInstance();
        meetupDatabase = database.getReference().child("meetups");
        totalDatabase = database.getReference().child("totalmeetups");
        locationButton = findViewById(R.id.location_image_button);
        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDataIntoDatabase();

                startActivity(new Intent(CreateActivity.this, PlacesActivity.class));

            }
        });




        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);

        FloatingActionButton nextButton = findViewById(R.id.fab2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                saveDataIntoDatabase();

                //Go to next Activity
                Intent intent = new Intent(CreateActivity.this, CreateTabsActivity.class);
                intent.putExtra("message", mID);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
    }

    public void saveDataIntoDatabase(){
        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);
        boolean isPublic = false;
        if(radioButton.getText().equals("Public"))
            isPublic = true;
        User admin = new User("admin", "admin@admin.admin");
        final Meetup newMeetup = new Meetup(nameText.getText().toString(),
                isPublic, txtDate.getText().toString(),
                txtTime.getText().toString(),admin);

        //Accessing Database
        totalDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int number = (int)dataSnapshot.getValue(Integer.class);
                number++;
                mID = "m"+number;
                mApp.setMeetupToBeSaved(mID);
                meetupDatabase.child(mID).setValue(newMeetup);
                totalDatabase.setValue(number);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
