package com.android.brewnotes;

/**
 * Created by jacobduron on 9/4/16.
 */
public class User {

    public String token;
    public Photo photo;
    public String firstName;
    public String lastName;
    public int followingCount;
    public int checkInCount;



    public static class Photo{
        public String profilePhoto;

    }
}
