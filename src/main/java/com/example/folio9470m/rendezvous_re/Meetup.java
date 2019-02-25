package com.example.folio9470m.rendezvous_re;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Meetup {
    private String name;
    private String location;
    private boolean isPublic;
    private LatLong coordinates;
    private String date;
    private String time;
    private User creator;
    private ArrayList<User> members = new ArrayList<>();

    public Meetup(){

    }

    public Meetup(String name, String location, boolean isPublic) {
        this.name = name;
        this.location = location;
        this.isPublic = isPublic;
    }

    public Meetup(String name, boolean isPublic, String date, String time, User creator) {
        this.name = name;
        this.isPublic = isPublic;
        this.date = date;
        this.time = time;
        this.creator = creator;
    }

    public Meetup(String name, String location, boolean isPublic, LatLong coordinates, String date, String time, User creator) {
        this.name = name;
        this.location = location;
        this.isPublic = isPublic;
        this.coordinates = coordinates;
        this.date = date;
        this.time = time;
        this.creator = creator;
    }

    public void addMember(User us){
        members.add(us);
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }


    public LatLong getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(LatLong coordinates) {
        this.coordinates = coordinates;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }





    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
