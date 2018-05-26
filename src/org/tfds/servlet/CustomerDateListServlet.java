package org.tfds.servlet;

import java.io.IOException;

import java.util.Date;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tfds.beans.Account;
import org.tfds.beans.Account.AccountType;
import org.tfds.beans.BlindDate;
import org.tfds.beans.CustomerDate;
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.beans.SuggestedBy;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

import static org.tfds.AppConstants.*;

@WebServlet( urlPatterns = { "/myprofile/dates", "/dates/*" })
public class CustomerDateListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		String profileId;

		if (account == null) {
			// Redirect user to the login servlet
			session.setAttribute(ERROR_STRING, USER_NOT_LOGGED_IN_ERROR);
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		// Handle the situation where the user is a Customer
		if (account.getAccountType() == AccountType.CUSTOMER) {
			profileId = userProfile.getProfileID(); 
		
			// Redirect if the context isn't what we want
			if (!req.getServletPath().equals("/myprofile/dates")) {
				resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
				return;
			}
		} else {
			profileId = req.getPathInfo().substring(1);
		}

		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<ArrayList<CustomerDate>> upcomingDates, pastDates;
		Packet<ArrayList<BlindDate>> blindDates;
		Packet<ArrayList<SuggestedBy>> suggestions;
		
		pastDates = DBUtils.getPastDates(conn, profileId);
		upcomingDates = DBUtils.getPendingDates(conn, profileId);
		blindDates = DBUtils.getReferrals(conn, profileId);
		suggestions = DBUtils.getSuggestedBy(conn, profileId);
		
		// Set session attributes and then Redirect to a page that will list all of the dates
		session.setAttribute(SESSION_UPCOMING_DATES, upcomingDates.getEntity());
		session.setAttribute(SESSION_PAST_DATES, pastDates.getEntity());
		session.setAttribute(SESSION_BLIND_DATES, blindDates.getEntity());
		session.setAttribute(SESSION_SUGGESTIONS, suggestions.getEntity());
		
		RequestDispatcher dispatcher
			= this.getServletContext().getRequestDispatcher("/WEB-INF/views/customerDatesView.jsp");
 
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Determine if the user has permissino to do this
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);

		// Get the fields for the date
		String profileA, profileB, location, dateString, custRep, function;
		
		Date date;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (account.getAccountType() == AccountType.CUSTOMER)
			profileA = userProfile.getProfileID();
		else {
			profileA = req.getParameter("profileA");
			custRep = account.getSsn();
		}

		profileB = req.getPathInfo().substring(1);
		dateString = req.getParameter("dateTime");
		location = req.getParameter("location");
		location = (location.length() == 0) ? null : location;
		
		// Determine if profileB is valid
		if (profileB == null || profileB.length() == 0) {
			this.doGet(req, resp);
		}
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		// TODO: Make it so that we can store customer rep stuff
		try {
			// Attempt to add the date to the database
			Packet<Entity> packet;
			date = dateFormat.parse(dateString);

			if (location == null)
				packet = DBUtils.createCustomerDate(conn, profileA, profileB, 
						date);
			else
				packet = DBUtils.createGeoDate(conn, profileA, profileB, 
						location, date);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Redirect customers to their date page
		if (account.getAccountType() == AccountType.CUSTOMER) {
			resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
			return;
		}
		
		this.doGet(req, resp);
	}
}