package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
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
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = {"/customer/settings"})
public class CreditCardViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Make sure it's a user logged in
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		
		if (userProfile == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<ArrayList<Account>> bankAccounts = DBUtils.getBankAccount(conn, userProfile.getProfileID());
		
		session.setAttribute(CUSTOMER_BANK_ACCOUNTS, bankAccounts.getEntity());
		
		// TODO: Handle errors
		RequestDispatcher dispatcher =
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/customerSettingsView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		
		if (userProfile == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}

		String ssn, cardNumber, accountNumber, date;
		ssn = userProfile.getSsn();
		cardNumber = req.getParameter("CardNumber");
		accountNumber = req.getParameter("AccountNumber");
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<Entity> transaction = DBUtils.attachAccount(conn, ssn, cardNumber, accountNumber);
		
		this.doGet(req, resp);
	}
}
