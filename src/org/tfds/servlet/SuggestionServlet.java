package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;
import org.tfds.beans.Account;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/suggest/*"})
public class SuggestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect user to the home page
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the employee account
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Get the fields for the suggestion
		String profile1, profile2, custRep;
		custRep = account.getSsn();
		profile2 = req.getParameter("Profile2");
		profile1 = req.getPathInfo().substring(1);
		
		Connection conn = MyUtils.getStoredConnection(req);
		DBUtils.createSuggestion(conn, profile1, profile2, custRep);

		this.doGet(req, resp);
	}
}
