package org.tfds.beans;

import java.util.Date;

public class BlindDate extends Entity {
	
	// Query Strings
	public static final String PROFILE_A = "ProfileA";
	public static final String PROFILE_B = "ProfileB";
	public static final String PROFILE_C = "ProfileC";
	public static final String DATE_TIME = "Date_Time";

    private String profileA;
    private String profileB;
    private String profileC;
    private Date dateTime;

    public BlindDate(String profileA, String profileB, String profileC,
                     Date dateTime) {
        this.profileA = profileA;
        this.profileB = profileB;
        this.profileC = profileC;
        this.dateTime = dateTime;
    }

    public String getProfileA() {
        return profileA;
    }

    public void setProfileA(String profileA) {
        this.profileA = profileA;
    }

    public String getProfileB() {
        return profileB;
    }

    public void setProfileB(String profileB) {
        this.profileB = profileB;
    }

    public String getProfileC() {
        return profileC;
    }

    public void setProfileC(String profileC) {
        this.profileC = profileC;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}