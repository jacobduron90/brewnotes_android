package com.android.brewnotes.servicelayer;

import java.util.Date;
import java.util.List;

/**
 * Created by jacobduron on 10/19/16.
 */

public class Recommendation {

    public String comment;
    public Date createdOn;
    public List<String> uploadedPhotos;
    public String userName;
    public String userIconUrl;

    public Recommendation(String comment, Date createdOn, String userName, String userIconUrl){
        this.comment = comment;
        this.createdOn = createdOn;
        this.userName = userName;
        this.userIconUrl = userIconUrl;
    }


}
