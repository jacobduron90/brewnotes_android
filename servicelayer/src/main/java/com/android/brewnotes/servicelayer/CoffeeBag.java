package com.android.brewnotes.servicelayer;

import java.io.Serializable;
import java.util.Locale;

/**
 * Created by jacobduron on 9/4/16.
 */
public class CoffeeBag implements Serializable{

    public String name;
    public String countryOfOrigin;
    public String roast;
    public Photo photo;
    public String _id;


    @Override
    public String toString() {
        String baseString = "CompanyName: %s, BagName %s, Country %s, Roast %s";
        return String.format(Locale.US, baseString, name, countryOfOrigin, roast);
    }

    public static class Photo implements Serializable{
        public String detailPhoto;
    }
}
