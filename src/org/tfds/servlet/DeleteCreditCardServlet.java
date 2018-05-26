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

import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = {"/credit-account/delete"})
public class DeleteCreditCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Profile userProfile  = (Profile) session.getAttribute(SESSION_PROFILE);
		
		if (userProfile == null) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		String ssn = userProfile.getSsn();
		String accountNumber = req.getParameter("AccountNumber");
		String cardNumber = req.getParameter("CardNumber");
		
		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<Entity> transaction = DBUtils.deleteAccount(conn, ssn, cardNumber, accountNumber);
		
		// TODO: Handle if an error occurs
		
		resp.sendRedirect(req.getContextPath() + "/customer/settings");
	}
}
