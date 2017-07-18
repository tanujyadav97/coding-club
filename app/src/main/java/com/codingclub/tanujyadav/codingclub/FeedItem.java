package com.codingclub.tanujyadav.codingclub;

import android.widget.Button;

/**
 * Created by TANUJ YADAV on 8/25/2016.
 */

public class FeedItem {
    private int id;
    private String name, status, image, profilePic, timeStamp, url,time,location,organiser,contact;

    public FeedItem() {
    }

    public FeedItem(int id, String name, String image, String status,
                    String profilePic, String timeStamp, String url, String time, String location, String organiser, String contact) {
        super();
        this.id = id;
        this.name = name;
        this.image = image;
        this.status = status;
        this.profilePic = profilePic;
        this.timeStamp = timeStamp;
        this.url = url;
        this.time=time;
        this.location="Venue:  "+location;
        this.organiser="Event Type:  "+organiser;
        this.contact="Coordinator:  "+contact;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImge() {
        return image;
    }

    public void setImge(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getTimeStamp() {

        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String gettime() {
        return time;
    }

    public void settime(String url) {
        this.time = url;
    }

    public String getloc() {
        return location;
    }

    public void setlocation(String url) {
        this.location = "Venue: "+url;
    }

    public String getorganiser() {
        return organiser;
    }

    public void setorganiser(String url) {
        this.organiser = "Type: "+url;
    }

    public String getcontact() {
        return contact;
    }

    public void setcontact(String url) {
        this.contact = "Coordinator: "+url;
    }
}