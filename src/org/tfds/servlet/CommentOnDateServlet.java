package org.tfds.servlet;

import static org.tfds.AppConstants.*;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/comment/*" })
public class CommentOnDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect user to their dates
		resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		
		// Get the date Id
		String dateId = req.getPathInfo().substring(1);
		String profileId = userProfile.getProfileID();
		String comment = req.getParameter("Comment").trim();
		String rating = req.getParameter("Rating").trim();
		
		Connection conn = MyUtils.getStoredConnection(req);
		Packet<Entity> transaction;
		
		// Attempt to update the date
		if (comment.length() != 0) {
			transaction = DBUtils.writeComment(conn, profileId, dateId, comment);
		
			if (!transaction.isSuccessful()) {
				session.setAttribute(ERROR_STRING, "An error occured");
				
				resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
				return;
			}
		}
		
		
		transaction = DBUtils.rateDate(conn, dateId, profileId, rating);

		// Check to see if an error occurred
		if (!transaction.isSuccessful()) {
			session.setAttribute(ERROR_STRING, "An error occured");
				
			resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
			return;
		}
		
		this.doGet(req, resp);
	}
}
