package com.android.brewnotes.servicelayer;

import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by jacobduron on 9/11/16.
 */
public class CoffeeCompany implements Serializable, Freshness {

    public String _id;
    public List<CoffeeCompany> bags;
    public String name;
    public String location;
    public Photo photo;

    public CoffeeCompany() {
        madeOn = DateTime.now();
    }

    public DateTime madeOn;

    public static class Photo implements Serializable{
        public String logo;
    }

    @Override
    public boolean isFresh() {
        long difference = DateTime.now().getMillis() - madeOn.getMillis();
        if(difference > MAX_FRESH()){
            return false;
        }
        return true;
    }

    @NotNull
    @Override
    public DateTime madeOn() {
        return madeOn;
    }

    @Override
    public long MAX_FRESH() {
        return 4 * 1000 * 60 * 60;
    }
}
