package com.android.brewnotes.servicelayer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jacobduron on 9/4/16.
 */
public class User{

    public String token;
    public Photo photo;
    public String firstName;
    public String lastName;
    public int followingCount;
    public int checkInCount;

    public List<CheckIn> checkIns;


    public String getFullName(){
        return firstName + " " + lastName;
    }



    public static class Photo{
        public String profilePhoto;

    }
}
