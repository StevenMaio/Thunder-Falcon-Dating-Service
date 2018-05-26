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
import org.tfds.beans.Packet;
import org.tfds.beans.Person;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = {"/profile-search/"})
public class ProfileSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in 
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		Profile profile = (Profile) session.getAttribute(SESSION_PROFILE);

		// Redirect users who aren't logged in
		if (account == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		RequestDispatcher dispatcher;
		
		if (req.getParameterMap().isEmpty()) {
			dispatcher = 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/searchView.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		HashMap<String, String> queryInfo = new HashMap<>();
		String firstName = req.getParameter("FirstName").trim();
		String lastName = req.getParameter("LastName").trim();
		String profileId = req.getParameter("ProfileId").trim();
		String zipcode = req.getParameter("Zipcode").trim();
		String city = req.getParameter("City").trim();
		String state = req.getParameter("State").trim();
		String gender = req.getParameter("Gender").trim();
		String height = req.getParameter("Height").trim();
		String weight = req.getParameter("Weight").trim();
		String hobbies = req.getParameter("Hobbies").trim();
		String hairColor = req.getParameter("HairColor").trim();
		String datingRangeStart, datingRangeEnd;
		
		// Add the appropriate attributes to the hash table
		if (firstName.length() != 0)
			queryInfo.put(Person.FIRST_NAME, firstName);
		if (lastName.length() != 0)
			queryInfo.put(Person.LAST_NAME, lastName);
		if (profileId.length() != 0)
			queryInfo.put(Profile.PROFILE_ID, profileId);
		if (zipcode.length() != 0)
			queryInfo.put(Person.ZIPCODE, zipcode);
		if (city.length() != 0)
			queryInfo.put(Person.CITY, city);
		if (state.length() != 0)
			queryInfo.put(Person.STATE, state);
		if (hobbies.length() != 0)
			queryInfo.put(Profile.HOBBIES, hobbies);
		if (gender.length() != 0)
			queryInfo.put(Profile.GENDER, gender);
		if (height.length() != 0)
			queryInfo.put(Profile.HEIGHT, height);
		if (weight.length() != 0)
			queryInfo.put(Profile.WEIGHT, weight);
		if (hairColor.length() != 0)
			queryInfo.put(Profile.HAIR_COLOR, hairColor);
		if (profile != null) {
			datingRangeStart = String.valueOf(profile.getDatingAgeRangeStart());
			datingRangeEnd = String.valueOf(profile.getDatingAgeRangeEnd());
			queryInfo.put(Profile.DATING_AGE_RANGE_START, datingRangeStart);
			queryInfo.put(Profile.DATING_AGE_RANGE_END, datingRangeEnd);
		} 		
		
		// Perform the query and return forward the user to the page
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<ArrayList<String>> searchResults = DBUtils.search(conn, queryInfo);
		
		// Forward the user back to the search page if the search was not successful
		/*if (! searchResults.isSuccessful() || 
				searchResults.getEntity().isEmpty()) {
			dispatcher = 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/searchView.jsp");
			dispatcher.forward(req, resp);
			return;
		}*/
		
		session.setAttribute(SEARCH_RESULTS, searchResults.getEntity());
		
		dispatcher =
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/searchResultView.jsp");
		dispatcher.forward(req, resp);
	}
}