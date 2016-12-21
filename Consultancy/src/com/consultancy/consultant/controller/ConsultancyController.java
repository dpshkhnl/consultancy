package com.consultancy.consultant.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consultancy.country.model.CountryModel;
import com.consultancy.student.DAO.StudentDAO;
import com.consultancy.student.model.StudentModel;
import com.consultancy.user.model.User;
import com.consultancy.consultant.DAO.ConsultancyDAO;
import com.consultancy.consultant.model.*;

@WebServlet("/Consultancy")
public class ConsultancyController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ConsultancyDAO objConsultancyCont = new ConsultancyDAO();
		StudentDAO studentDAO = new StudentDAO();

		String action = request.getParameter("action");
		String message = null;
		String dir = null;

		switch (action) {

		case "QRY":
			/* List<User> lista = service.contactosQry(); */

			User user = new User();
			if (request.getSession().getAttribute("LoggedInUser") == null) {
				dir = "Login?action=LOGIN";
			} else {
				user = (User) request.getSession().getAttribute("LoggedInUser");

			}
                        
			if (user.getRole().name().equals("Consultancy")) {

				ConsultancyModel consultancy = new ConsultancyModel();
				consultancy = objConsultancyCont.getConsultancyByUserId(user.getUserId());
				request.setAttribute("consultancy", consultancy);
				dir = "/view/consultancy/consultancyUpd.jsp";

			} else {

				List<ConsultancyModel> lista = new ArrayList<ConsultancyModel>();
				lista = objConsultancyCont.getAllConsultancys();
				request.setAttribute("lista", lista);
				dir = "/view/consultancy/consultancyQry.jsp";
			}
			break;

		case "INS":

			ConsultancyModel consultancy = new ConsultancyModel();
			message = verify(request, consultancy);

			if (message == null) {
				consultancy = getConsultancy(request);
                                 user = new User();
			if (request.getSession().getAttribute("LoggedInUser") == null) {
				dir = "Login?action=LOGIN";
			} else {
				user = (User) request.getSession().getAttribute("LoggedInUser");

			}
				consultancy.setUserId(user.getUserId());
				message = objConsultancyCont.addConsultancy(consultancy);

				if (message == null) {
					request.setAttribute("consultancy", consultancy);
					dir = "/view/consultancy/consultancyInsert.jsp";
				} else {
					dir = "Consultancy?action=QRY";
				}

			} else {
				request.setAttribute("consultancy", consultancy);
				dir = "/view/consultancy/consultancyInsert.jsp";
			}
			break;

		case "UPD":
			String id = request.getParameter("id");
			consultancy = new ConsultancyModel();
			user = new User();
			if (id != null)
				consultancy = objConsultancyCont.getConsultancyById(Integer.valueOf(id));
			message = verify(request, consultancy);

			if (message == null) {
				consultancy = getConsultancy(request);
				message = objConsultancyCont.editConsultancy(consultancy);

				if (message == null) {
					request.setAttribute("consultancy", consultancy);
					dir = "/view/consultancy/consultancyUpd.jsp";
				} else {

					if (request.getSession().getAttribute("LoggedInUser") == null) {
						dir = "Login?action=LOGIN";
					} else {
						user = (User) request.getSession().getAttribute("LoggedInUser");

					}

					if (user.getRole().name().equals("Consultancy")) {

						request.setAttribute("consultancy", consultancy);
						request.setAttribute("message", message);
						dir = "/welcome.jsp";
						break;

					}

					dir = "Consultancy?action=QRY";
				}

			} else {
				request.setAttribute("consultancy", consultancy);
				dir = "/view/consultancy/consultancyUpd.jsp";
			}
			break;

		case "DEL":
			id = request.getParameter("id");
			consultancy = new ConsultancyModel();
			if (id != null) {
				consultancy = objConsultancyCont.getConsultancyById(Integer.valueOf(id));
				objConsultancyCont.removeConsultancy(consultancy.getId());
			} else {
				message = "Delete Failed";
				dir = "Consultancy?action=QRY";
			}

			dir = "Consultancy?action=QRY";
			break;

		case "FAV":

			user = new User();
			if (request.getSession().getAttribute("LoggedInUser") == null) {
				dir = "Login?action=LOGIN";
			} else {
				user = (User) request.getSession().getAttribute("LoggedInUser");

			}

			StudentModel student = studentDAO.getStudentByUserId(user.getUserId());
			consultancy = new ConsultancyModel();

			consultancy = getConsultancy(request);
			
			message = objConsultancyCont.addConsultancyStudent(consultancy, student);
			dir = "Student?action=SEARCH";
			break;

		default:
			message = "No Action";

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

	private String verify(HttpServletRequest request, ConsultancyModel consultancy) {

		String message = "<ul>";
		// System.out.println("tamano " + message.length());
		String name = request.getParameter("name");

		if ((name == null) || (name.trim().length() == 0)) {
			message += "<li>Please Enter Name</li>";
		}

		if (message.equals("<ul>")) {
			message = null;
		} else {
			message += "</ul>";
		}

		return message;
	}

	public ConsultancyModel getConsultancy(HttpServletRequest request) {

		String fullName = request.getParameter("name");
		String address = request.getParameter("address");
		String contactPerson = request.getParameter("contactPerson");
		String email = request.getParameter("email");
		String website = request.getParameter("website");
		String phone = request.getParameter("contact");
		String id = request.getParameter("id");
                String latlng = request.getParameter("latlng");
                

		com.consultancy.consultant.model.ConsultancyModel consultancy = new ConsultancyModel();
		if (id != null)
			consultancy.setId(Integer.valueOf(id));

		consultancy.setPhone(phone);
		consultancy.setEmail(email);
		consultancy.setAddress(address);
		consultancy.setContactPerson(contactPerson);
		consultancy.setName(fullName);
		consultancy.setWebsite(website);
                consultancy.setLatlng(latlng);
                        

		return consultancy;
	}

}
