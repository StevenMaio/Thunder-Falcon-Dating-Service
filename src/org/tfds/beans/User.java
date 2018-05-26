package org.tfds.beans;

import java.util.Date;

public class User extends Entity {
	
	// Query Strings
	public static final String SSN = "SSN";
	public static final String PPP = "PPP";
	public static final String RATING = "Rating";
	public static final String DATE_LAST_ACTIVE = "DateOfLastAct";

    private String ssn;
    private String ppp;
    private int rating;
    private Date dateOfLastActive;

    public User(String ssn, String ppp, int rating, Date dateOfLastActive) {
        super();
        this.ssn = ssn;
        this.ppp = ppp;
        this.rating = rating;
        this.dateOfLastActive = dateOfLastActive;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getPpp() {
        return ppp;
    }

    public void setPpp(String ppp) {
        this.ppp = ppp;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDateOfLastActive() {
        return dateOfLastActive;
    }

    public void setDateOfLastActive(Date dateOfLastActive) {
        this.dateOfLastActive = dateOfLastActive;
    }
}
