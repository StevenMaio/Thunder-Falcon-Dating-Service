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
import org.tfds.beans.CustomerDate;
import org.tfds.beans.Account.AccountType;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;
import org.tfds.beans.Packet;

@WebServlet(urlPatterns = {"/calendar-date/"})
public class DateListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user has permission to see htis
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		if (account == null || account.getAccountType() != AccountType.MANAGER) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Perform the query
		String year, month, day, dateString;
		
		day = req.getParameter("Day");
		month = req.getParameter("Month");
		year = req.getParameter("Year");
		
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<ArrayList<CustomerDate>> datePacket = DBUtils.getDateFromCalendar(conn, year, month, day);
		
		// Forward the user to a list if the query was successful, otherwise 
		// forward them to the home page
		if (datePacket.isSuccessful()) {
			session.setAttribute(CUSTOMER_DATE_LIST, datePacket.getEntity());
			RequestDispatcher dispatcher =
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/dateListView.jsp");
	
			
			dispatcher.forward(req, resp);
			return;
		} else {
			resp.sendRedirect(req.getContextPath());
			return;
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}