package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;
import org.tfds.beans.Account;
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

public class CustomerStatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see ifthe user is logged in
		HttpSession session = req.getSession();
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Redirect user to the login screen if they're not logged in
		if (userAccount == null) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		/* TODO: Get the following items and then redirect to the customerStats view servlet 
		 * 		Most active profiles
		 * 		Highest Rated Profiles
		 * 		Popular Geo-Date Locations
		 */
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<ArrayList<String>> mostActiveProfiles = DBUtils.getMostActiveProfiles(conn);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
