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
import org.tfds.beans.Like;
import org.tfds.beans.Packet;
import org.tfds.beans.Profile;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/myprofile/likes", "/likes/*" })
public class LikesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if hte user is logged in
		HttpSession session = req.getSession();
		session.removeAttribute(ERROR_STRING);
		
		Profile userProfile = (Profile) session.getAttribute(SESSION_PROFILE);
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		RequestDispatcher dispatcher = null;
		String profileID;
		
		if (account == null) {
			// Redirect to the login servlet
			session.setAttribute(ERROR_STRING, USER_NOT_LOGGED_IN_ERROR);
			resp.sendRedirect(req.getContextPath() + "/login");
			return;
		}
		
		// Determine if the user is an employee or a manager
		if (account.getAccountType() != AccountType.CUSTOMER) {
			if (req.getServletPath().equals("/myprofile/likes")) {
				resp.sendRedirect(req.getContextPath());
				return;
			}
			
			profileID = req.getPathInfo().substring(1);
		} else {
			if (!req.getServletPath().equals("/myprofile/likes")) {
				resp.sendRedirect(req.getContextPath() + "/myprofile/dates");
				return;
			}

			profileID = userProfile.getProfileID();
		}

		// Get the user likes
		Connection conn = MyUtils.getStoredConnection(req);

		Packet<ArrayList<Like>> userLikes = DBUtils.getUserLikes(conn, profileID);
		Packet<ArrayList<Like>> userIsLiked = DBUtils.getUserBeLiked(conn, profileID);
		Packet<ArrayList<String>> userFavorites = DBUtils.getFavourites(conn, profileID);
		
		// If there was an error retrieving information on the user's likes
		if (!userLikes.isSuccessful() || !userIsLiked.isSuccessful() || 
				!userFavorites.isSuccessful()) {
			resp.sendRedirect(req.getContextPath() + "/myprofile");	
			return;
		} 

		session.setAttribute(SESSION_USER_LIKES, userLikes.getEntity());
		session.setAttribute(SESSION_USER_IS_LIKED, userIsLiked.getEntity());
		session.setAttribute(SESSION_USER_FAVOURITES, userFavorites.getEntity());
		
		session.setAttribute("connAttribute", conn);
		
		// Redirect to some jsp/
		dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/likesView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO: Stuff?
	}
}
