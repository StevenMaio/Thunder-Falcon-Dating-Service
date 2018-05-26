package org.tfds.beans;

import java.util.Date;

public class Like extends Entity {
	
	public static final String LIKER = "Liker";
	public static final String LIKEE = "Likee";
	public static final String DATE_TIME = "Date_Time";
	
    private String liker;
    private String likee;
    private Date dateTime;

    public Like(String liker, String likee, Date dateTime) {
        this.liker = liker;
        this.likee = likee;
        this.dateTime = dateTime;
    }

    public Like(Profile liker, Profile likee, Date dateTime) {
        this.liker = liker.getProfileID();
        this.likee = likee.getProfileID();
        this.dateTime = dateTime;
    }

    public String getLiker() {
        return liker;
    }

    public void setLiker(String liker) {
        this.liker = liker;
    }

    public String getLikee() {
        return likee;
    }

    public void setLikee(String likee) {
        this.likee = likee;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


}
