package org.tfds.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static org.tfds.AppConstants.*;
import org.tfds.beans.Account;
import org.tfds.beans.Account.AccountType;

@WebServlet(urlPatterns = {"/employee/profilesuggestions/*"})
public class ProfileSuggestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is a manager or employee
		HttpSession session = req.getSession();
		Account userAccount = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Redirect customers back to their profile pages
		if (userAccount.getAccountType() == AccountType.CUSTOMER) {
			resp.sendRedirect(req.getContextPath() + "/login");
			return;	
		}
		
		// TODO: Get the list of suggestions and then redirect to suggestionView.jsp
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
