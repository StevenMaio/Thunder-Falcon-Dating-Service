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

import org.tfds.beans.Account;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Check to see if the user is logged in
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		RequestDispatcher dispatcher = null;

		if (account == null) {
			dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
			dispatcher.forward(request, response);
			return;
		}

		// Redirect the user appropriately
		switch (account.getAccountType()) {
			case CUSTOMER:
				response.sendRedirect(request.getContextPath() + "/myprofile");
				return;

			case EMPLOYEE:
				response.sendRedirect(request.getContextPath() + "/employee");
				return;

			case MANAGER:
				response.sendRedirect(request.getContextPath() + "/employee");
				return;
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		
		Packet<Profile> userProfile = null;
		Packet<Account> userAccount = null;
		boolean hasError = false;
		String errorString = null;
		
		if (username == null || password == null || username.length() == 0 ||
				password.length() == 0) {
			hasError = true;
			errorString = LOGIN_ERROR_NO_USER_OR_PASS;
		} else {
			Connection conn = MyUtils.getStoredConnection(request);
			
			try {
				userProfile = DBUtils.logIn(conn, username, password);
				userAccount = DBUtils.getAccount(conn, username);
				
				if (!userProfile.isSuccessful() || !userAccount.isSuccessful()){
					hasError = true;
				}
			} catch (Exception e) {
//				e.printStackTrace();
				hasError = true;
			}
		
		}
		
		// Determine if an error occurred
		if (hasError) {
			errorString = "Login error";
			request.setAttribute(ERROR_STRING, errorString);

            // Set the error string and then forward to the login string
//            RequestDispatcher dispatcher
//                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
// 
//            dispatcher.forward(request, response);
            response.sendRedirect(request.getContextPath());
            return;
		}
		
		// Redirect the logged in user to their profile page
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_PROFILE, userProfile.getEntity());
		session.setAttribute(SESSION_ACCOUNT, userAccount.getEntity());
			
		// Redirect to userInfo page or profile servlet?.
		response.sendRedirect(request.getContextPath() + "/myprofile");
	}
}
