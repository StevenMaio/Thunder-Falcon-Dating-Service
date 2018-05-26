package org.tfds.servlet;

import java.io.IOException;

import java.sql.Connection;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;
import org.tfds.beans.Account;
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Person;
import org.tfds.beans.Profile;
import org.tfds.beans.Account.AccountType;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/customer/create", "/customer/edit/*"} )
public class EditCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in, and then check to see 
		// if they have permission
		HttpSession session = req.getSession();
		session.removeAttribute(ERROR_STRING);
		
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		Profile profile = (Profile) session.getAttribute(SESSION_PROFILE);
		session.removeAttribute(PAGE_PROFILE);
		session.removeAttribute(PAGE_ACCOUNT);
		session.removeAttribute(PAGE_PERSON);
		session.removeAttribute(CREATING_NEW_USER);

		// TODO: Change this so that customers can change their own profiles
		if (account == null) {
			// Redirect user to the editCustomerPage
			session.setAttribute(CREATING_NEW_USER, true);
			
			RequestDispatcher dispatcher = 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/editCustomerView.jsp");
			dispatcher.forward(req, resp);

			return;
		} else if (account.getAccountType() == AccountType.CUSTOMER) {
			if (! req.getPathInfo().substring(1).equals(profile.getProfileID())) {
				resp.sendRedirect(req.getContextPath());
				return;
			}
		}
		
		// Determine whether or not it's to create a customer
		String profileId;
		boolean creatingNewProfile;
		
		
		if (req.getServletPath().equals("/customer/create")) {
			profileId = "";
			creatingNewProfile = true;
		}
		else {
			Connection conn = MyUtils.getStoredConnection(req);

			// Get the fields
			profileId = req.getPathInfo().substring(1);
			creatingNewProfile = false;
			Packet<Profile> pageProfile = DBUtils.fetchProfile(conn, profileId);
			
			String ssn = pageProfile.getEntity().getSsn();
			Packet<Person> pagePerson = DBUtils.fetchPerson(conn, ssn);
			Packet<Account> pageAccount = DBUtils.getAccount(conn, ssn);
			
			// Set the session attributes
			session.setAttribute(PAGE_ACCOUNT, pageAccount.getEntity());
			session.setAttribute(PAGE_PROFILE, pageProfile.getEntity());
			session.setAttribute(PAGE_PERSON, pagePerson.getEntity());
		}
		
		// Redirect user to the editCustomerPage
		session.setAttribute(CREATING_NEW_USER, creatingNewProfile);
		
		RequestDispatcher dispatcher = 
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/editCustomerView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Determine if the user was trying to create a new user or edit an
		// existing one
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		Profile profile = (Profile) session.getAttribute(SESSION_PROFILE);
		Profile pageProfile = (Profile) session.getAttribute(PAGE_PROFILE);
		
		// Get all of the important values
		String ssn = req.getParameter("SSN");
		String password = req.getParameter("Password");
		String firstName = req.getParameter("FirstName");
		String lastName = req.getParameter("LastName");
		String street = req.getParameter("Street");
		String city = req.getParameter("City");
		String state = req.getParameter("State");
		String zipcode = req.getParameter("Zipcode");
		String email = req.getParameter("Email");
		String telephone = req.getParameter("Telephone");
		String cardNumber = req.getParameter("CardNumber");
		String accountNumber = req.getParameter("AccountNumber");
		String profileId = req.getParameter("ProfileId");
		String age = req.getParameter("Age");
		String datingAgeRangeStart = req.getParameter("DatingAgeRangeStart");
		String datingAgeRangeEnd = req.getParameter("DatingAgeRangeEnd");
		String datingGeoRange = req.getParameter("DatingGeoRange");
		String gender = req.getParameter("Gender");
		String hobbies = req.getParameter("Hobbies");
		String height = req.getParameter("Height");
		String weight = req.getParameter("Weight");
		String hairColor = req.getParameter("HairColor");
		
		// Determine whether it's to 
		boolean creatingNewCustomer = (boolean) session.getAttribute(CREATING_NEW_USER);
		Connection conn = MyUtils.getStoredConnection(req);
		HashMap<String, String> customerInfo = new HashMap<>();

		customerInfo.put(Person.SSN, ssn);
		customerInfo.put(Person.PASSWORD, password);
		customerInfo.put(Person.FIRST_NAME, firstName);
		customerInfo.put(Person.LAST_NAME, lastName);
		customerInfo.put(Person.STREET, street);
		customerInfo.put(Person.CITY, city);
		customerInfo.put(Person.STATE, state);
		customerInfo.put(Person.ZIPCODE, zipcode);
		customerInfo.put(Person.EMAIL, email);
		customerInfo.put(Person.TELEPHONE, telephone);
		
		customerInfo.put(Profile.PROFILE_ID, profileId);
		customerInfo.put(Profile.AGE, age);
		customerInfo.put(Profile.DATING_AGE_RANGE_START, datingAgeRangeStart);
		customerInfo.put(Profile.DATING_AGE_RANGE_END, datingAgeRangeEnd);
		customerInfo.put(Profile.DATING_GEO_RANGE, datingGeoRange);
		customerInfo.put(Profile.GENDER, gender);
		customerInfo.put(Profile.HOBBIES, hobbies);
		customerInfo.put(Profile.HEIGHT, height);
		customerInfo.put(Profile.WEIGHT, weight);
		customerInfo.put(Profile.HAIR_COLOR, hairColor);
		
		Packet<Entity> transaction;

		if (creatingNewCustomer) {
			transaction = DBUtils.registerCustomer(conn, ssn, password, email, profileId);
		} else {
			profileId = pageProfile.getProfileID();
			ssn = pageProfile.getSsn();

			// TODO: Edit an existing one
			customerInfo.put(Person.SSN, pageProfile.getSsn());
			customerInfo.put(Profile.PROFILE_ID, profileId);
			
		}
		transaction =  DBUtils.editProfile(conn, customerInfo);
		
		if (account != null && transaction.isSuccessful() && 
				account.getAccountType() == AccountType.CUSTOMER) {
			
			Packet<Profile> newProfile = DBUtils.fetchProfile(conn, profileId);
			Packet<Account> newAccount = DBUtils.getAccount(conn, ssn);
			
			// FIXME: I don't think this is necessary
			session.removeAttribute(SESSION_ACCOUNT);
			session.removeAttribute(SESSION_PROFILE);
			session.setAttribute(SESSION_ACCOUNT, newAccount.getEntity());
			session.setAttribute(SESSION_PROFILE, newProfile.getEntity());
		}
		
		// Redirect user to the profile that was edited/created
		resp.sendRedirect(req.getContextPath() + "/profile/" + profileId);
	}
}