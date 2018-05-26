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
import org.tfds.beans.Account.AccountType;
import org.tfds.beans.Packet;
import org.tfds.beans.Report;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = {"/employee/sales"})
public class SalesReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is a manager
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		if (account == null || account.getAccountType() != AccountType.MANAGER) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		String month, year;
		
		month = req.getParameter("Month");
		year = req.getParameter("Year");
		
		// Redirect user back to the home page
		if (month == null || year == null || month.length() == 0 || year.length() == 0) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Get a list of the sales report for the month
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<ArrayList<Report>> salesReport = DBUtils.generateMonthlyReport(conn, year, month);
		
		if (salesReport.isSuccessful()) {
			session.setAttribute(SALES_REPORT, salesReport.getEntity());

			// Redirect to the proper jsp
			RequestDispatcher dispatcher = 
					this.getServletContext().getRequestDispatcher("/WEB-INF/views/salesReportView.jsp");
			
			dispatcher.forward(req, resp);
			return;
		}
		
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
}
