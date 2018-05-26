package org.tfds.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tfds.beans.Account;
import static org.tfds.AppConstants.*;

public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in 
		HttpSession session = req.getSession();
		Account account = (Account) req.getAttribute(SESSION_ACCOUNT);
		
		// Redirect user to the appropriate page
		if (account != null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}


}
