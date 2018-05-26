package org.tfds.beans;

public class Report extends Entity {

    // Report attribute strings
    public static final String FIRST_NAME = "FN";
    public static final String LAST_NAME = "LN";
    public static final String SALARY = "salary";
    public static final String TOTAL = "total";

    private String firstName;
    private String lastName;
    private int salary;
    private double total;

    public Report(String firstName, String lastName, int salary, double total) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.total = total;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
