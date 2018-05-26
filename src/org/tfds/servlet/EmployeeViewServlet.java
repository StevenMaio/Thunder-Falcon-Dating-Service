package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

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
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/employee" })
public class EmployeeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is a manage
		HttpSession session = req.getSession();
		session.removeAttribute(ERROR_STRING);
		
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		RequestDispatcher dispatcher;
		
		// Remove all of the previous attributes
		session.removeAttribute(CUSTOMER_LIST);
		session.removeAttribute(MOST_ACTIVE_PROFILES);
		session.removeAttribute(HIGHEST_RATED_PROFILES);
		session.removeAttribute(POPULAR_DATE_LOCATIONS);
		session.removeAttribute(HIGHEST_GROSSING_CUSTOMERS);
		session.removeAttribute(CUSTOMER_MAILING_LIST);
		session.removeAttribute(HIGHEST_GROSSING_EMPLOYEES);
		session.removeAttribute(POPULAR_CALENDAR_DAYS);
		session.removeAttribute(SALES_REPORT);
		
		if (account.getAccountType() == Account.AccountType.CUSTOMER) {
			session.setAttribute(ERROR_STRING, "You do not have permission");
			
			// Redirect user to the homepage
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Retrieve data that we need for the page
		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<ArrayList<String[]>> highestGrossingCustomers = DBUtils.getHighestGrossingCustomers(conn);
		Packet<ArrayList<String[]>> highestGrossingEmployees = DBUtils.getHighestGrossingEmployees(conn);
		Packet<ArrayList<String[]>> highestRatedProfiles = DBUtils.getHighlyRatedProfiles(conn);
		Packet<ArrayList<String[]>> mailingList = DBUtils.getMailingList(conn);
		Packet<ArrayList<String>> popularLocations = DBUtils.getPopularLocations(conn);
		Packet<ArrayList<String>> mostActiveProfiles = DBUtils.getMostActiveProfiles(conn);
		Packet<ArrayList<Date>> highestCalendarDays = DBUtils.getHighestCalendarDate(conn);
		
		session.setAttribute(CUSTOMER_LIST, mailingList.getEntity());
		session.setAttribute(MOST_ACTIVE_PROFILES, mostActiveProfiles.getEntity());
		session.setAttribute(HIGHEST_RATED_PROFILES, highestRatedProfiles.getEntity());
		session.setAttribute(POPULAR_DATE_LOCATIONS, popularLocations.getEntity());
		session.setAttribute(HIGHEST_GROSSING_CUSTOMERS, highestGrossingCustomers.getEntity());
		session.setAttribute(CUSTOMER_MAILING_LIST, mailingList.getEntity());
		session.setAttribute(HIGHEST_GROSSING_EMPLOYEES, highestGrossingEmployees.getEntity());
		session.setAttribute(POPULAR_CALENDAR_DAYS, highestCalendarDays.getEntity());
		
		session.setAttribute("connAttribute", conn);

		dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/employeeView.jsp");
        dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
