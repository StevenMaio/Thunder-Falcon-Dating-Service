package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tfds.beans.Account;
import org.tfds.beans.Packet;
import org.tfds.beans.Account.AccountType;
import org.tfds.beans.Entity;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;
import org.tfds.beans.Profile;

import static org.tfds.AppConstants.*;

@WebServlet(urlPatterns = {"/referals"})
public class ReferalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect user to the home page
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Make sure that the user a customer
		HttpSession session = req.getSession();
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Redirect the user if they're a manager
		
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		Profile pageProfile = (Profile) session.getAttribute(PAGE_PROFILE);

		String profileA, profileB, profileC;
		
		profileB = req.getParameter("ProfileB");
		profileC = req.getParameter("ProfileC");
		
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<Entity> packet; 
		
		if (userAccount.getAccountType() == AccountType.CUSTOMER) {
			profileA = userProfile.getProfileID();
			packet = DBUtils.createReferredDate(conn, profileB, profileC, profileA);
		} else {
			profileA = userAccount.getSsn();
			packet = DBUtils.createSuggestion(conn, profileC, profileC, profileA);
		}
		
		// TODO: Check to see if it was successful
		
		// Redirect user to the home page
		resp.sendRedirect(req.getContextPath() + "/profile/" + pageProfile.getProfileID());
	}
}
