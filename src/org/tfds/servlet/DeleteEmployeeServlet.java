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
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Account.AccountType;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = {"/employee/delete/*"})
public class DeleteEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user has permission
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		session.removeAttribute(ERROR_STRING);
		resp.sendRedirect(req.getContextPath());
		
		if (account == null && account.getAccountType() != AccountType.MANAGER) {
			return;
		}
		
		// Attempt to delete the account
		Connection conn = MyUtils.getStoredConnection(req);
		String ssn = req.getPathInfo().substring(1);
		
		if (ssn.equals(account.getSsn())) {
			return;
		}
		
		Packet<Entity> transaction;
		transaction = DBUtils.deleteEmployee(conn, ssn);
		
		if (transaction.isSuccessful()) {
			return;
		}
		
		String error = "An error occurred";
		session.setAttribute(ERROR_STRING, error);
	}
}
