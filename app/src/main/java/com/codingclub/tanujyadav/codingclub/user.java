package com.codingclub.tanujyadav.codingclub;

/**
 * Created by 15121 on 1/27/2017.
 */

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Ravi Tamada on 07/10/16.
 * www.androidhive.info
 */

@IgnoreExtraProperties
public class user {

    public String name;
    public String branch;
    public String year;
    public String institute;
    public String phone;
    public String email;

    // Default constructor required for calls to
    // DataSnapshot.getValue(User.class)
    public user() {
    }

    public user(String name,String branch,String year,String institute,String phone, String email) {
        this.name = name;
        this.branch = branch;
        this.year = year;
        this.institute = institute;
        this.phone = phone;
        this.email = email;
    }
}
