package com.android.brewnotes.servicelayer;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jacobduron on 9/11/16.
 */
public class CoffeeCompany implements Serializable {

    public String _id;
    public List<CoffeeCompany> bags;
    public String name;
    public String location;
    public Photo photo;

    public static class Photo implements Serializable{
        public String logo;
    }

}
