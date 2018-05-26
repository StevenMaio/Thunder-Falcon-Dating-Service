package org.tfds.utils;
 
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.tfds.beans.User;
 
public class MyUtils {
 
    public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
 
    private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
 
    // Store Connection in request attribute.
    // (Information stored only exist during requests)
    public static void storeConnection(ServletRequest request, Connection conn) {
        request.setAttribute(ATT_NAME_CONNECTION, conn);
    }
 
    // Get the Connection object has been stored in attribute of the request.
    public static Connection getStoredConnection(ServletRequest request) {
        Connection conn = (Connection) request.getAttribute(ATT_NAME_CONNECTION);
        return conn;
    }
 
    // Store user info in Session.
    public static void storeLoginedUser(HttpSession session, User loginedUser) {
        // On the JSP can access via ${loginedUser}
        session.setAttribute("loginedUser", loginedUser);
    }
 
    // Get the user information stored in the session.
    public static User getLoginedUser(HttpSession session) {
        User loginedUser = (User) session.getAttribute("loginedUser");
        return loginedUser;
    }
 
    // TODO: Edit this
    // Store info in Cookie
    public static void storeUserCookie(HttpServletResponse response, User user) {
        System.out.println("Store user cookie");
//        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUserName());
//        // 1 day (Converted to seconds)
//        cookieUserName.setMaxAge(24 * 60 * 60);
//        response.addCookie(cookieUserName);
    }
 
    public static String getUserNameInCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
 
    // Delete cookie.
    public static void deleteUserCookie(HttpServletResponse response) {
        Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
        // 0 seconds (This cookie will expire immediately)
        cookieUserName.setMaxAge(0);
        response.addCookie(cookieUserName);
    }
 
    /**
     * Returns a formatted string of the current system time
     * @return A formatted string of the current system time
     */
    public static String getCurrentTime() {
    	SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	java.util.Date date = new Date();
    	
    	return timeFormat.format(date);
    }
}