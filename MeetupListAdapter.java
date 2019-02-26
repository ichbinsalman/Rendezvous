package com.example.folio9470m.rendezvous_re;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MeetupListAdapter extends ArrayAdapter<Meetup> {
    private Context mContext;
    private int mResource;
    public MeetupListAdapter(Context context, int resource, ArrayList<Meetup> objects) {
        super(context, resource, objects);
        mContext= context;
        mResource = resource;
    }


    @Override
    public View getView(int position,  View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String location = getItem(position).getLocation();

        Meetup meetup = new Meetup(name, location, false);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView nameText = convertView.findViewById(R.id.meetupName);
        TextView locationText = convertView.findViewById(R.id.location);
        TextView timeText = convertView.findViewById(R.id.timeText);

        timeText.setText("1");

        nameText.setText(name);
        locationText.setText(location);
        return convertView;
    }
}
