package org.tfds.servlet;

import java.io.IOException;

import java.sql.Connection;
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
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

import static org.tfds.AppConstants.*;

@WebServlet(urlPatterns = { "/myprofile", "/profile/*" })
public class ProfileViewServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
		
//		session.removeAttribute(PAGE_PROFILE);
//		session.removeAttribute(PEOPLE_DATED);

		RequestDispatcher dispatcher = null;
		Connection conn = null;
		
		if (userAccount == null) {
			session.setAttribute(ERROR_STRING, USER_NOT_LOGGED_IN_ERROR);
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		} 

		// Determine if we're viewing user's profile or someone elses's
		if (req.getServletPath().equals("/myprofile")) {
			session.setAttribute(PAGE_PROFILE, userProfile);
			
			// Redirect an employee if they're trying to go to myprofile
			if (userAccount.getAccountType() != AccountType.CUSTOMER) {
				resp.sendRedirect(req.getContextPath() + "/employee");
				return;
			}
		}
		else {
			
			String profileID;

			if (req.getParameter("Profile") != null)
				profileID = req.getParameter("Profile");
			else 
				profileID = req.getPathInfo().substring(1);
			
			conn = MyUtils.getStoredConnection(req);
				
			// Get the profile, and then forward 
			Packet<ArrayList<String>> peopleDated = DBUtils.getDatingObjects(conn, profileID);
			Packet<Profile> pageProfile = DBUtils.fetchProfile(conn, profileID);

			if (pageProfile.isSuccessful())
				session.setAttribute(PAGE_PROFILE, pageProfile.getEntity());
			if (peopleDated.isSuccessful())
				session.setAttribute(PEOPLE_DATED, peopleDated.getEntity());

			// Redirect the user back if there was an error
			if (!peopleDated.isSuccessful() || !pageProfile.isSuccessful()) {
				session.setAttribute(ERROR_STRING, "There was an error while attempting to find that profile");
				resp.sendRedirect(req.getContextPath());
				return;
			}
		}
		
		session.setAttribute("connAttribute", MyUtils.getStoredConnection(req));

			
		dispatcher = 
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/profileView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Attempt to like a profile
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		Profile likee = (Profile) session.getAttribute(PAGE_PROFILE);
		
		Connection conn = MyUtils.getStoredConnection(req);

		String profileID = req.getPathInfo().substring(1);
		
		Packet<Entity> packet = DBUtils.like(conn, userProfile.getProfileID(), 
				profileID);
		
		if (packet.isSuccessful());
		else {
			System.out.printf("An error occurred\n");
		}
	
		resp.sendRedirect(req.getContextPath() + "/profile/" + profileID);
	}
}
