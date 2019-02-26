package com.example.folio9470m.rendezvous_re;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private ListView searchMeetups;
    private ArrayAdapter<String> adapter;
    private DatabaseReference meetupRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        meetupRef = FirebaseDatabase.getInstance().getReference().child("meetups");
        searchMeetups = findViewById(R.id.searchPublicMeetups);


        meetupRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> publicMeetupsList = new ArrayList();
                for(DataSnapshot var : dataSnapshot.getChildren()){
                    Meetup current = var.getValue(Meetup.class);
                    if (current.isPublic())
                        publicMeetupsList.add(current.getName());
                }

                adapter = new ArrayAdapter<String>(SearchActivity.this,
                        android.R.layout.simple_list_item_1,
                        publicMeetupsList);
                searchMeetups.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        searchMeetups.setClickable(true);
        searchMeetups.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String)searchMeetups.getItemAtPosition(position);
                Rendezvous mApp =((Rendezvous)getApplicationContext());
                mApp.setMeetupName(name);
                startActivity(new Intent(SearchActivity.this, MeetupOverview.class));
            }
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item = menu.findItem(R.id.search_meetups);
        SearchView searchView = (SearchView) item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
