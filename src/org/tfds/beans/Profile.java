package org.tfds.beans;

import java.util.Date;

public class Profile extends Entity {
	
	// String attribute constants
	public static final String PROFILE_ID = "ProfileID";
	public static final String SSN = "OwnerSSN";
	public static final String AGE = "AGE";
	public static final String DATING_AGE_RANGE_START = 
			"DatingAgeRangeStart";
	public static final String DATING_AGE_RANGE_END =
			"DatingAgeRangeEnd";
	public static final String DATING_GEO_RANGE = "DatinGeoRange";
	public static final String GENDER = "M_F";
	public static final String HOBBIES = "Hobbies";
	public static final String HEIGHT = "Height";
	public static final String WEIGHT = "Weight";
	public static final String HAIR_COLOR = "HairColor";
	public static final String CREATION_DATE = "CreationDate";
	public static final String LAST_MOD_DATE = "LastModDate";

    private String profileID;
    private String ssn;
    private int age;
    private int datingAgeRangeStart;
    private int datingAgeRangeEnd;
    private int datingGeoRange;
    private String gender;
    private String hobbies;
    private double height;
    private double weight;
    private String hairColor;
    private Date creationDate;
    private Date lastModDate;

    public Profile(String profileID, String ssn, int age,
                   int datingAgeRangeStart, int datingAgeRangeEnd, int datingGeoRange,
                   String gender, String hobbies, double height, double weight,
                   String hairColor, Date creationDate, Date lastModDate) {
        this.profileID = profileID;
        this.ssn = ssn;
        this.age = age;
        this.datingAgeRangeStart = datingAgeRangeStart;
        this.datingAgeRangeEnd = datingAgeRangeEnd;
        this.datingGeoRange = datingGeoRange;
        this.gender = gender;
        this.hobbies = hobbies;
        this.height = height;
        this.weight = weight;
        this.hairColor = hairColor;
        this.creationDate = creationDate;
        this.lastModDate = lastModDate;
    }

    public String getProfileID() {
        return profileID;
    }

    public void setProfileID(String profileID) {
        this.profileID = profileID;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getDatingAgeRangeStart() {
        return datingAgeRangeStart;
    }

    public void setDatingAgeRangeStart(int datingAgeRangeStart) {
        this.datingAgeRangeStart = datingAgeRangeStart;
    }

    public int getDatingAgeRangeEnd() {
        return datingAgeRangeEnd;
    }

    public void setDatingAgeRangeEnd(int datingAgeRangeEnd) {
        this.datingAgeRangeEnd = datingAgeRangeEnd;
    }

    public int getDatingGeoRange() {
        return datingGeoRange;
    }

    public void setDatingGeoRange(int datingGeoRange) {
        this.datingGeoRange = datingGeoRange;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getHairColor() {
        return hairColor;
    }

    public void setHairColor(String hairColor) {
        this.hairColor = hairColor;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastModDate() {
        return lastModDate;
    }

    public void setLastModDate(Date lastModDate) {
        this.lastModDate = lastModDate;
    }
}
