package com.android.brewnotes.servicelayer;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

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
    public String companyName;

//    public DateTime madeOn;

    public CoffeeBag() {

    }

    @Override
    public String toString() {
        String baseString = "CompanyName: %s, BagName %s, Country %s, Roast %s";
        return String.format(Locale.US, baseString, name, countryOfOrigin, roast);
    }

    public static class Photo implements Serializable{
        public String detailPhoto;
        public String iconPhoto;
    }

}
