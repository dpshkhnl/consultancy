package com.consultancy.user.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consultancy.consultant.DAO.ConsultancyDAO;
import com.consultancy.consultant.model.ConsultancyModel;
import com.consultancy.student.DAO.StudentDAO;
import com.consultancy.student.model.StudentModel;
import com.consultancy.user.DAO.UserDAO;
import com.consultancy.user.model.User;
import com.consultancy.user.model.UserRole;
import com.consultancy.user.model.UserStatus;

@WebServlet({ "/User" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	String isSignUp = "";
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO objUserCont = new UserDAO();
		StudentDAO studentDAO = new StudentDAO();
		ConsultancyDAO consultancyDAO = new ConsultancyDAO();
		
		String action = request.getParameter("action");
		String message = null;
		String dir = null;
		
/*
		if (request.getSession().getAttribute("LoggedInUser") == null && !action.equals("SIGNUP")) {
			action = "LOGINFAILED";
		}
*/
		switch (action) {
		
		case "SIGNUP":

			dir = "/view/user/signup.jsp";
			break;

		case "LOGINFAILED":
			dir = "Login?action=LOGIN";
			break;

		case "QRY":
			/* List<User> lista = service.contactosQry(); */

			List<User> lista = new ArrayList<User>();
			lista = objUserCont.getAllUsers();
			request.setAttribute("lista", lista);
			dir = "/view/user/userQry.jsp";

			break;

		case "INS":

			User user = new User();
			message = verify(request, user);

			if (message == null) {
				
				user = getUser(request);
				try {
				int res = objUserCont.addUser((user));
					if (isSignUp.equals("True") && res != 0){
					if (user.getRole().name().equals("Student"))
					{
						StudentModel student = new StudentModel();
						student.setName(user.getFullName());
						student.setUserId(res);
						studentDAO.addStudent(student);
					}
					else if (user.getRole().name().equals("Consultancy"))
					{
						ConsultancyModel consult = new ConsultancyModel();
						consult.setName(user.getFullName());
						consult.setUserId(res);
						consultancyDAO.addConsultancy(consult);
						
					}
					}
					if (isSignUp.equals("True"))
					{
						request.setAttribute("message", "Registration Successful");
						dir = "/login.jsp";
						break;
					}
					
				} catch (NoSuchAlgorithmException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (message == null) {
					request.setAttribute("user", user);
					dir = "/view/user/userInsert.jsp";
				} else {
					dir = "User?action=QRY";
				}

			} else {
				request.setAttribute("user", user);
				dir = "/view/user/userInsert.jsp";
			}
			break;

		

		case "UPD":
			String id = request.getParameter("id");
			user = new User();
			if (id != null)
				user = objUserCont.getUserById(Integer.valueOf(id));
			message = verify(request, user);

			if (message == null) {
				user = getUser(request);
				message = objUserCont.editUser((user));

				if (message == null) {
					request.setAttribute("user", user);
					dir = "/view/user/userUpd.jsp";
				} else {
					dir = "User?action=QRY";
				}

			} else {
				request.setAttribute("user", user);
				dir = "/view/user/userUpd.jsp";
			}
			break;

		case "DEL":
			id = request.getParameter("id");
			user = new User();
			if (id != null) {
				user = objUserCont.getUserById(Integer.valueOf(id));
				objUserCont.removeUser(user.getUserId());
			} else {
				message = "Delete Failed";
				dir = "User?action=QRY";
			}

			dir = "User?action=QRY";
			break;

		default:
			message = "action no reconicida";

		}

		if (message != null) {
			String msg = "<div class=\"col-md-5 col-md-offset-3\" style=\"text-align: center\">";
			msg += "<div class=\"alert alert-danger\">";
			msg += "<button class=\"close\" data-dismiss=\"alert\"><span>&times;</span></button>";
			msg += "<strong>Alerta!!</strong><br/>";
			msg += message;
			msg += "</div></div>";
			request.setAttribute("message", msg);
		}

		RequestDispatcher despachador = request.getRequestDispatcher(dir);
		despachador.forward(request, response);

	}

	private String verify(HttpServletRequest request, User user) {

		String message = "<ul>";
		// System.out.println("tamano " + message.length());
		String fullName = request.getParameter("fullName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		String role = request.getParameter("role");
		String status = request.getParameter("status");
		String email = request.getParameter("email");
		String phone = request.getParameter("contact");

		if ((fullName == null) || (fullName.trim().length() == 0)) {
			message += "<li>Please Enter Full Name</li>";
		}
		if ((userName == null) || (userName.trim().length() == 0)) {
			message += "<li>Please Enter Username</li>";
		}
		if ((password == null) || (password.trim().length() == 0)) {
			message += "<li>Please Enter Password</li>";
		}

		if (message.equals("<ul>")) {
			message = null;
		} else {
			message += "</ul>";
		}

		return message;
	}

	public User getUser(HttpServletRequest request) {
		String fullName = request.getParameter("fullName");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		String status = request.getParameter("status");
		String email = request.getParameter("email");
		String phone = request.getParameter("contact");
		String id = request.getParameter("id");
		
		 isSignUp = request.getParameter("signup");
		User user = new User();
		user.setContactNo(phone);
		user.setEmail(email);

		user.setRole(UserRole.valueOf(role));
		user.setStatus(UserStatus.valueOf(status));
		user.setUserName(userName);
		user.setFullName(fullName);
		user.setPassword(password);

		if (id != null)
			user.setUserId(Integer.valueOf(id));

		return user;
	}

}