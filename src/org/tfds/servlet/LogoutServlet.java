package org.tfds.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;

import org.tfds.beans.Account;
import org.tfds.beans.Profile;

@WebServlet(urlPatterns = { "/logout" })
public class LogoutServlet  extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// If user is not logged in, redirect to login page with an error message
		if (account == null) {
			session.setAttribute(ERROR_STRING, USER_NOT_LOGGED_IN_ERROR);
		} else {
			// Remove all of the session attributes
			session.removeAttribute(SESSION_PROFILE);
			session.removeAttribute(ERROR_STRING);
			session.removeAttribute(SESSION_ACCOUNT);
			session.removeAttribute(SESSION_EMPLOYEE);
		}

		// Forward the user back to the login page
		response.sendRedirect(request.getContextPath() + "/login");	
	}
	
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		// Forward the user back to the login page
		response.sendRedirect(request.getContextPath() + "/login");	
	}
}
