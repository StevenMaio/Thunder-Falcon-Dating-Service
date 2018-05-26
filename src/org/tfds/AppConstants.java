package org.tfds;

public class AppConstants {
	// Login error messages
	public static final String LOGIN_ERROR_NO_USER_OR_PASS = 
			"Username and password are required";
	public static final String LOGIN_ERROR_USER_NOT_FOUND =
			"Username and password invalid";
	
	// Logout error messages
	public static final String USER_NOT_LOGGED_IN_ERROR = "You're not logged in.";

	// Query error messages
	public static final String QUERY_ERROR_INCONSISTENT_DATABASE = "Database inconsistent.";
	public static final String QUERY_ERROR_EMPTY_SET = "No match found.";
	public static final String OCCUPIED_SSN = "This SSN has already been occupied";
	public static final String OCCUPIED_EMAIL = "This E-mail address has already been occupied";
	public static final String OCCUPIED_ID = "This ID has already been occupied";
	public static final String CUSTOMER_BANK_ACCOUNTS = "bankAccounts";

	// Page stuff
	public static final String PAGE_PROFILE = "pageProfile";
	public static final String PAGE_PERSON = "pagePerson";
	public static final String PAGE_ACCOUNT = "pageAccount";
	public static final String PAGE_EMPLOYEE = "pageEmployee";
	public static final String SEARCH_RESULTS = "searchResults";
	
	// EMPLOYEE/Manager SESSION STUFF
	public static final String CUSTOMER_LIST = "customerList";
	public static final String CUSTOMER_DATE_LIST = "customerDateList";
	public static final String MOST_ACTIVE_PROFILES = "mostActiveProfiles";
	public static final String HIGHEST_RATED_PROFILES = "highestRatedProfiles";
	public static final String POPULAR_DATE_LOCATIONS = "popularDateLocations";
	public static final String HIGHEST_GROSSING_CUSTOMERS = "highestGrossingCustomers";
	public static final String CUSTOMER_MAILING_LIST = "customerMailingList";
	public static final String HIGHEST_GROSSING_EMPLOYEES = "highestGrossingEmployees";
	public static final String POPULAR_CALENDAR_DAYS = "popularCalendarDays";
	public static final String CREATING_NEW_USER = "creatingNewUser";
	public static final String CREATING_NEW_EMPLOYEE = "creatingNewEmployee";
	public static final String SALES_REPORT = "salesReport";
	
	// Session attributes
	public static final String SESSION_ACCOUNT = "userAccount";
	public static final String SESSION_EMPLOYEE = "userEmployee";
	public static final String SESSION_PROFILE = "userProfile";
	public static final String ERROR_STRING = "errorString";
	public static final String SESSION_USER_LIKES = "userLikes";
	public static final String SESSION_USER_IS_LIKED = "userIsLiked";
	public static final String SESSION_USER_FAVOURITES = "useFavourites";
	public static final String SESSION_UPCOMING_DATES = "upcomingDates";
	public static final String SESSION_PAST_DATES = "pastDates";
	public static final String SESSION_BLIND_DATES = "blindDates";
	public static final String SESSION_SUGGESTIONS = "suggestions";
	public static final String PEOPLE_DATED = "peopleDate";

	// Affected Lines
	public static final String COLLECTED_ROLES = " roles collected.";
	public static final String AFFECTED_ROLES = " roles affected.";

	// Success and Fail
	public static final boolean SUCCEEDED = true;
	public static final boolean FAILED = false;

	// Type
	public static final String MANAGER = "Manager";
	public static final String CUSTOMER_REP = "CustRep";

	/*
	 * Profile Editing Protocol
	 * All info is passed through an ArrayList<String>
	 * 0. SSN
	 * 1. Password
	 * 2. FirstName
	 * 3. LastName
	 * 4. Street
	 * 5. City
	 * 6. STATE
	 * 7. ZipCode
	 * 8. Email
	 * 9. Telephone
	 * 10. ProfileID
	 * 11. Age
	 * 12. DatingAgeRangeStart
	 * 13. DatingAgeRangeEnd
	 * 14. DatingGeoRange
	 * 15. Gender
	 * 16. Hobbies
	 * 17. Height
	 * 18. Weight
	 * 19. HairColor
	 */
}