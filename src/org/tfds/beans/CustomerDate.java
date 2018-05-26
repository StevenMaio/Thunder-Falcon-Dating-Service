package org.tfds.beans;

import java.util.Date;

public class CustomerDate extends Entity {
	
	// Query strings
    public static final String DATE_ID = "DateId";
	public static final String PROFILE_1 = "Profile1";
	public static final String PROFILE_2 = "Profile2";
	public static final String CUSTOMER_REP = "CustRep";
	public static final String DATE_TIME = "Date_Time";
	public static final String LOCATION = "Location";
	public static final String BOOKING_FEE = "BookingFee";
	public static final String COMMENTS = "Comments";
	public static final String USER1_RATING = "User1Rating";
	public static final String USER2_RATING = "User2Rating";

	private String dateId;
    private String profile1;
    private String profile2;
    private String custRep;
    private Date dateTime;
    private String location;
    private double bookingFee;
    private String comments;
    private int user1Rating;
    private int user2Rating;

    public CustomerDate(String dateId, String profile1, String profile2,
                        String custRep, Date dateTime, String location, double bookingFee,
                        String comments, int user1Rating, int user2Rating) {
        super();
        this.dateId = dateId;
        this.profile1 = profile1;
        this.profile2 = profile2;
        this.custRep = custRep;
        this.dateTime = dateTime;
        this.location = location;
        this.bookingFee = bookingFee;
        this.comments = comments;
        this.user1Rating = user1Rating;
        this.user2Rating = user2Rating;
    }

    public String getDateId() {
        return dateId;
    }

    public void setDateId(String dateId) {
        this.dateId = dateId;
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

    public String getCustRep() {
        return custRep;
    }

    public void setCustRep(String custRep) {
        this.custRep = custRep;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getBookingFee() {
        return bookingFee;
    }

    public void setBookingFee(double bookingFee) {
        this.bookingFee = bookingFee;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getUser1Rating() {
        return user1Rating;
    }

    public void setUser1Rating(int user1Rating) {
        this.user1Rating = user1Rating;
    }

    public int getUser2Rating() {
        return user2Rating;
    }

    public void setUser2Rating(int user2Rating) {
        this.user2Rating = user2Rating;
    }
}
