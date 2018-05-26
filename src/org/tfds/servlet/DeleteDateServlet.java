package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/delete/date" })
public class DeleteDateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Redirect the user to the home page
		resp.sendRedirect(req.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the date and the 
		String dateId = req.getParameter("DateId");
		Connection conn = MyUtils.getStoredConnection(req);
		
		Packet<Entity> packet = DBUtils.cancelDates(conn, dateId);
		
		// Check to see if it was success
		if (! packet.isSuccessful()) {
			System.out.printf("An error occurred\n");
		}
		
		this.doGet(req, resp);	// Return to the date views page
	}
}
