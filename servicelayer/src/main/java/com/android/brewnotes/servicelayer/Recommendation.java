package com.android.brewnotes.servicelayer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jacobduron on 10/19/16.
 */

public class Recommendation implements Serializable{


    public Date createdOn;




    public int overallScore;
    public String comment;
    public List<String> aromas;
    public List<String> body;
    public List<String> flavor;
    public List<String> finish;

    public Recommendation(){}


    public Recommendation(String comment, Date createdOn){
        this.comment = comment;
        this.createdOn = createdOn;

        aromas = new ArrayList<>();
        body = new ArrayList<>();
        flavor = new ArrayList<>();
        finish = new ArrayList<>();

    }


}
