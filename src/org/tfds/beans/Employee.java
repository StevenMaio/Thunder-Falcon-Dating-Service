package org.tfds.beans;

import java.util.Date;

public class Employee extends Entity {
	
	// Query strings
	public static final String SSN = "SSN";
	public static final String ROLE = "Role";
	public static final String START_DATE = "StartDate";
	public static final String HOURLY_RATE = "HourlyRate";

    private String ssn;
    private String role;
    private Date startDate;
    private int hourlyRate;

    public Employee(String ssn, String role, Date startDate, int hourlyRate) {
        this.ssn = ssn;
        this.role = role;
        this.startDate = startDate;
        this.hourlyRate = hourlyRate;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(int hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}
