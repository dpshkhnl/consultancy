package com.consultancy.user.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consultancy.consultant.DAO.ConsultancyDAO;
import com.consultancy.consultant.model.ConsultancyModel;
import com.consultancy.user.DAO.UserDAO;
import com.consultancy.user.model.User;
import com.consultancy.user.model.UserRole;
import com.consultancy.user.model.UserStatus;
import com.consultancy.util.SummaryModel;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/Login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		UserDAO objUserCont = new UserDAO();
		ConsultancyDAO consultanDAO = new ConsultancyDAO();
		
		String action = request.getParameter("action");
		String message = null;
		String dir = null;

		switch (action) {
		case "WELCOME":
			if (request.getSession().getAttribute("LoggedInUser") == null)
			{
				dir = "Login?action=LOGIN";
			}
			else{
				User usr = new User();
				usr = (User) request.getSession().getAttribute("LoggedInUser");
				if (usr.getRole().name().equals("Admin"))
				{
					List<SummaryModel> lista = new ArrayList<>();
					lista = objUserCont.loadSummary(usr);
					request.setAttribute("lista", lista);
					 dir = "/welcome.jsp";
					
				}
				else if (usr.getRole().name().equals("Consultancy"))
				{
					List<SummaryModel> lista = new ArrayList<>();
					lista = consultanDAO.loadSummary(usr);
					request.setAttribute("lista", lista);
					 dir = "/view/consultancy/welcomeconsult.jsp";
				}
				else if (usr.getRole().name().equals("Student"))
				{
					List<ConsultancyModel> lista = new ArrayList<ConsultancyModel>();
					lista = consultanDAO.getAllConsultancyDesc();
					request.setAttribute("lista", lista);
					
					 dir = "/view/student/welcomestd.jsp";
				}
				else{
					dir = "Login?action=LOGIN";
				}
           
			}
			break;
			
		case "LOGOUT":
			
				
				request.getSession().invalidate();
				dir = "/login.jsp";
			
			break;
	
		case "LOGIN":

			User user = new User();
			message = verify(request, user);

			if (message == null) {
				user = getUser(request);
				
				String username = user.getUserName();
				 String role = user.getRole().name();
				 String password = user.getPassword();
				 User usr  = null;
				try {
					 usr  = objUserCont.isValidLogin(username,role,password);
				} catch (NoSuchAlgorithmException e) {
					
					e.printStackTrace();
				} catch (InvalidKeySpecException e) {
					
					e.printStackTrace();
				}

				if (usr == null) {
					message ="Incorrect Credentials!";
					request.setAttribute("message", message);
					request.setAttribute("user", user);
					dir = "/login.jsp";
				} else {
					request.getSession().setAttribute("LoggedInUser", usr);
					request.getSession().setAttribute("role", usr.getRole().name());
					request.getSession().setAttribute("userName", usr.getFullName());
					request.setAttribute("message", message);
					request.setAttribute("name", usr.getFullName());
					
					dir = "Login?action=WELCOME";
				}

			} else {
				request.setAttribute("message", message);
				request.setAttribute("user", user);
				dir = "/login.jsp";
			}
			break;

		}
		if (message != null) {
			String msg = "<div class=\"col-md-5 col-md-offset-3\" style=\"text-align: center\">";
			msg += "<div class=\"alert alert-danger\">";
			msg += "<button class=\"close\" data-dismiss=\"alert\"><span>&times;</span></button>";
			msg += "<strong>Alert!!</strong><br/>";
			msg += message;
			msg += "</div></div>";
			request.setAttribute("message", msg);
		}
		
		RequestDispatcher despachador = request.getRequestDispatcher(dir);
		despachador.forward(request, response);
	}
	private String verify(HttpServletRequest request, User user) {
        
        
        String message = "<ul>";
//        System.out.println("tamano " + message.length());
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

      
        
        
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
	
	public User getUser(HttpServletRequest request)
	{
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        User user = new User();
        
        user.setRole(UserRole.valueOf(role));
        user.setUserName(userName);
        user.setPassword(password);
       
		return user;
	}
	

}
