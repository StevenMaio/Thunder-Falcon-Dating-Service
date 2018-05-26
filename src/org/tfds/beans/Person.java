package org.tfds.beans;

public class Person extends Entity {
	
	// Query strings
	public static final String SSN = "SSN";
	public static final String PASSWORD = "Password";
	public static final String FIRST_NAME = "FirstName";
	public static final String LAST_NAME = "LastName";
	public static final String STREET = "Street";
	public static final String CITY = "City";
	public static final String STATE = "State";
	public static final String ZIPCODE = "Zipcode";
	public static final String EMAIL = "Email";
	public static final String TELEPHONE = "Telephone";

    private String ssn;
    private String password;
    private String firstName;
    private String lastName;
    private String street;
    private String city;
    private String state;
    private int zipcode;
    private String email;
    private String telephone;

    public Person(String ssn, String password, String firstName,
                  String lastName, String street, String city, String state,
                  int zipcode, String email, String telephone) {
        this.ssn = ssn;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipcode = zipcode;
        this.email = email;
        this.telephone = telephone;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
