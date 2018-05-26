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
import org.tfds.beans.Employee;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;import org.tfds.beans.Packet;

@WebServlet(urlPatterns = { "/employee/login"})
public class EmployeeLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		RequestDispatcher dispatcher = null;
		
		// Redirect the user to the home page if they're already logged in
		if (account != null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Redirect the user to the employee login page
		dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/employeeLoginView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		String ssn = req.getParameter("Username");
		String password = req.getParameter("Password");
		
		Packet<Account> accountPacket = null;
		Packet<Employee> employeePacket = null;
		boolean hasError = false;
		String errorString = null;
		
		// Attempt to login
		Connection conn = MyUtils.getStoredConnection(req);
		
		// TODO: Fix this, this doesn't authenticate, so anyone can login as 
		// an employee if they know the SSN of an employee
		try  {
			accountPacket = DBUtils.getAccount(conn, ssn);
			employeePacket = DBUtils.employeeLogIn(conn, ssn, password);
			
			if (!employeePacket.isSuccessful()) {
				hasError = true;
				errorString = "Login unsuccessful";
			}
		} catch (Exception e) {
			e.printStackTrace();
			hasError = true;
			errorString = e.getMessage();
		}
		
		if (hasError) {
			req.setAttribute(ERROR_STRING, errorString);

			// Redirect the user to the employee login page
			RequestDispatcher dispatcher = 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/employeeLoginView.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		
		// Set the session and forward to... soemthing
		session.setAttribute(SESSION_ACCOUNT, accountPacket.getEntity());
		session.setAttribute(SESSION_EMPLOYEE, employeePacket.getEntity());
		session.removeAttribute(ERROR_STRING);
		
		// Redirect to the stats page
		resp.sendRedirect(req.getContextPath() + "/employee");
	}
}
