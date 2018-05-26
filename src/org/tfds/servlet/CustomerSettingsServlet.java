package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;
import org.tfds.beans.Profile;
import org.tfds.utils.MyUtils;

public class CustomerSettingsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if it's a customer logged in
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		
		if (userProfile == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Get the user's credit stuff
		String profileId = userProfile.getProfileID();
		Connection conn = MyUtils.getStoredConnection(req);
		
		// TODO: Query
		
		RequestDispatcher dispatcher = 
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/customerSettingsView.jsp");

		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
