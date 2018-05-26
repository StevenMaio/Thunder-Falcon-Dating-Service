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
import org.tfds.beans.Account.AccountType;
import org.tfds.beans.Entity;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;

@WebServlet(urlPatterns = {"/delete/customer/*"})
public class DeleteCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect user to the login page
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is an employee
		HttpSession session = req.getSession();
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Redirect customers to the home page
		if (userAccount.getAccountType() == AccountType.CUSTOMER) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		// Get the profile Id
		String profileId, ssn;

		profileId = req.getPathInfo().substring(1);

		// TODO: Delete the user that is, delete user, person, profileId, account, etc.
		Packet<Entity> transaction = DBUtils.deleteProfile(conn, profileId);

		// FIXME: Handle errors
		
		// Redirect the employee back to the employee page
		resp.sendRedirect(req.getContextPath() + "/employee");
	}
}
