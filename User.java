package com.example.folio9470m.rendezvous_re;

import java.util.ArrayList;

public class User {
    private String name;
    private String email;
    private ArrayList<Meetup> meetupArrayList = new ArrayList<>();

    public User(){

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email, ArrayList<Meetup> meetupArrayList) {
        this.name = name;
        this.email = email;
        this.meetupArrayList = meetupArrayList;
    }


    public void addMeetup(Meetup m1){
        meetupArrayList.add(m1);
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ArrayList<Meetup> getMeetupArrayList() {
        return meetupArrayList;
    }

    public void setMeetupArrayList(ArrayList<Meetup> meetupArrayList) {
        this.meetupArrayList = meetupArrayList;
    }
}
