package org.tfds.beans;

import java.util.Date;

public class SuggestedBy extends Entity {
	
	// Query Strinsg
	public static final String CUST_REP = "CustRep";
	public static final String PROFILE1 = "Profile1";
	public static final String PROFILE2 = "Profile2";
	public static final String DATE_TIME = "Date_Time";

    private String custRep;
    private String profile1;
    private String profile2;
    private Date dateTime;

    public SuggestedBy(String custRep, String profile1, String profile2, Date dateTime) {
        this.custRep = custRep;
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.dateTime = dateTime;
    }

    public String getCustRep() {
        return custRep;
    }

    public void setCustRep(String custRep) {
        this.custRep = custRep;
    }

    public String getProfile1() {
        return profile1;
    }

    public void setProfile1(String profile1) {
        this.profile1 = profile1;
    }

    public String getProfile2() {
        return profile2;
    }

    public void setProfile2(String profile2) {
        this.profile2 = profile2;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
