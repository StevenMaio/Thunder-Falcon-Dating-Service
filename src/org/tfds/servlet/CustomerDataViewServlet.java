package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
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
import org.tfds.beans.CustomerDate;
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Person;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/stats" })
public class CustomerDataViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in
		HttpSession session = req.getSession();
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);

		if (userAccount == null) {
			resp.sendRedirect(req.getContextPath());
			session.setAttribute(ERROR_STRING, USER_NOT_LOGGED_IN_ERROR);
			return;
		}
		
		// Get the most active profiles, the most highested rated profiles, 
		// and the most popular dating locations
		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<ArrayList<String>> mostActiveProfiles = DBUtils.getMostActiveProfiles(conn);
		Packet<ArrayList<String[]>> highestRatedProfiles = DBUtils.getHighlyRatedProfiles(conn);
		Packet<ArrayList<String>> popularLocations = DBUtils.getPopularLocations(conn);
		
		session.setAttribute(MOST_ACTIVE_PROFILES, mostActiveProfiles.getEntity());
		session.setAttribute(HIGHEST_RATED_PROFILES, highestRatedProfiles.getEntity());
		session.setAttribute(POPULAR_DATE_LOCATIONS, popularLocations.getEntity());
		
		// Test out some jazz
		for (int i = 0; i < mostActiveProfiles.getEntity().size();i ++) {
			System.out.println(mostActiveProfiles.getEntity().get(i));
		}
		
		
		// Redirect to the view
		RequestDispatcher dispatcher;
		dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/customerStatsView.jsp");
		dispatcher.forward(req, resp);
	}
}
