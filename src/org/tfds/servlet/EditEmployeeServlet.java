package org.tfds.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;

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
import org.tfds.beans.Employee;
import org.tfds.beans.Entity;
import org.tfds.beans.Packet;
import org.tfds.beans.Person;
import org.tfds.utils.DBUtils;
import org.tfds.utils.MyUtils;

@WebServlet(urlPatterns = { "/employee/create", "/employee/edit/*"})
public class EditEmployeeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Check to see if the user is logged in or is a customer
		HttpSession session = req.getSession();
		Account account = (Account) session.getAttribute(SESSION_ACCOUNT);
		
		// Remove attributes from the page
		session.removeAttribute(PAGE_ACCOUNT);
		session.removeAttribute(PAGE_PERSON);
		session.removeAttribute(CREATING_NEW_EMPLOYEE);
		
		if (account == null || account.getAccountType() != AccountType.MANAGER) {
			resp.sendRedirect(req.getContextPath());
			return;
		}
		
		// Determine if the user trying to create a new employee or 
		// edit an existing one
		
		boolean creatingNewEmployee = false;
		
		if (req.getServletPath().equals("/employee/create")) {
			creatingNewEmployee = true;
		} else {
			Connection conn = MyUtils.getStoredConnection(req);
			String ssn = req.getPathInfo().substring(1);
			
			Packet<Account> pageAccount = DBUtils.getAccount(conn, ssn); 
			Packet<Person> pagePerson = DBUtils.fetchPerson(conn, ssn);
			Packet<Employee> pageEmployee = DBUtils.fetchEmployee(conn, ssn);
			
			session.setAttribute(PAGE_PERSON, pagePerson.getEntity());
			session.setAttribute(PAGE_ACCOUNT, pageAccount.getEntity());
			session.setAttribute(PAGE_EMPLOYEE, pageEmployee.getEntity());
		}
		
		session.setAttribute(CREATING_NEW_EMPLOYEE, creatingNewEmployee);
		RequestDispatcher dispatcher =
				this.getServletContext().getRequestDispatcher("/WEB-INF/views/employeeEditView.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Connection conn = MyUtils.getStoredConnection(req);
		
		// Get all of the attributes
		String ssn = req.getParameter("SSN");
		String password = req.getParameter("Password");
		String firstName = req.getParameter("FirstName");
		String lastName = req.getParameter("LastName");
		String street = req.getParameter("Street");
		String city = req.getParameter("City");
		String state = req.getParameter("State");
		String zipcode = req.getParameter("Zipcode");
		String email = req.getParameter("Email");
		String telephone = req.getParameter("Telephone");
		String role = req.getParameter("Role");
		String hourlyRate = req.getParameter("HourlyRate");
		
		boolean creatingNewEmployee = (boolean) session.getAttribute(CREATING_NEW_EMPLOYEE);
		HashMap<String, String> employeeInfo = new HashMap<>();
		
		employeeInfo.put(Person.SSN, ssn);
		employeeInfo.put(Person.PASSWORD, password);
		employeeInfo.put(Person.FIRST_NAME, firstName);
		employeeInfo.put(Person.LAST_NAME, lastName);
		employeeInfo.put(Person.STREET, street);
		employeeInfo.put(Person.CITY, city);
		employeeInfo.put(Person.STATE, state);
		employeeInfo.put(Person.ZIPCODE, zipcode);
		employeeInfo.put(Person.EMAIL, email);
		employeeInfo.put(Person.TELEPHONE, telephone);
		employeeInfo.put(Employee.ROLE, role);
		employeeInfo.put(Employee.HOURLY_RATE, hourlyRate);
		
		Packet<Entity> transaction;
		if (creatingNewEmployee) {
			// TODO: Create new employee
			transaction = DBUtils.createEmployee(conn, employeeInfo);
		} else {
			// TODO: Edit an existing employee
		}

		// Redirect the manager to the homepage
		resp.sendRedirect(req.getContextPath());
	}

}
