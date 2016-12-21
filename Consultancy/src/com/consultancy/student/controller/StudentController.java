package com.consultancy.student.controller;

import java.io.*;
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
import com.consultancy.country.DAO.CountryDAO;
import com.consultancy.country.model.CountryModel;
import com.consultancy.student.DAO.StudentDAO;
import com.consultancy.student.model.StudentModel;
import com.consultancy.university.DAO.UniversityDAO;
import com.consultancy.university.model.UniversityModel;
import com.consultancy.user.model.User;


/**
 * Servlet implementation class StudentController
 */
@WebServlet("/Student")
public class StudentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String universityId = "0";
	String countryId = "0";
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		StudentDAO objStudentCont = new StudentDAO();
		CountryDAO countryDAO = new CountryDAO();
		UniversityDAO universityDAO = new UniversityDAO();
		ConsultancyDAO consultancyDAO = new ConsultancyDAO();
		String action = request.getParameter("action");
		String message = null;
		String dir = null;
                if (action == null)
                {
                    PrintWriter out = response.getWriter();
                    response.setContentType("text/html;charset=UTF-8");
			getSearchPara(request);
			List<UniversityModel> lstUni = new ArrayList<>();
			
			if ( countryId == null)
				countryId = "0";
				
                                if(countryId.equals("0")){
                                        
                                    lstUni = universityDAO.getAllUniversities();
                                }
                                else
                                {
                                   lstUni = universityDAO.getAllUniversitiesByCountry(Integer.valueOf(countryId));
                                   out.print("<option value='0'>Select</option>" );
                                   for(UniversityModel uni : lstUni)
                                   {
                                       
                                        out.print("<option value='"+uni.getUniId()+"'>"+uni.getName()+"</option>" );
                                   }
                                   out.close();
                                }
                    return;
                }

		switch (action) {

		case "QRY":
			/* List<User> lista = service.contactosQry(); */

			User user = new User();
			List<StudentModel> lista = new ArrayList<StudentModel>();
			if (request.getSession().getAttribute("LoggedInUser") == null)
			{
				dir = "Login?action=LOGIN";
			}
			else
			{
				 user = (User) request.getSession().getAttribute("LoggedInUser");

			}
			
			
			if (user.getRole().name().equals("Consultancy"))
			{
				
				ConsultancyModel consultancy = new ConsultancyModel();
				consultancy = consultancyDAO.getConsultancyByUserId(user.getUserId());
				lista = objStudentCont.getStudentByConsultancy(consultancy.getId());
			}
			else if (user.getRole().name().equals("Student"))
			{
				StudentModel student = new StudentModel();
				student = objStudentCont.getStudentByUserId(user.getUserId());
				request.setAttribute("student", student);
				dir = "/view/student/studentUpd.jsp";
				break;
				
			}
			else
			{
				lista = objStudentCont.getAllStudent();
			}
			
			
			request.setAttribute("lista", lista);
			dir = "/view/student/studentQry.jsp";

			break;

		case "INS":

			
			
			StudentModel student = new StudentModel();
			message = verify(request, student);
			 user = new User();
			if (message == null) {
				
				if (request.getSession().getAttribute("LoggedInUser") == null)
				{
					dir = "Login?action=LOGIN";
				}
				else
				{
					 user = (User) request.getSession().getAttribute("LoggedInUser");
	
				}
				student = getStudent(request);
				student.setUserId(user.getUserId());
				int id  = objStudentCont.addStudent(student);
				if (id != 0){
				if (user.getRole().name().equals("Consultancy"))
				{
					student = objStudentCont.getStudentById(id);
					ConsultancyModel consultancy = new ConsultancyModel();
					consultancy = consultancyDAO.getConsultancyByUserId(user.getUserId());
					message = consultancyDAO.addConsultancyStudent(consultancy,student);
				}
				}
				
				if (message == null && id == 0) {
					request.setAttribute("student", student);
					dir = "/view/student/studentInsert.jsp";
				} else {
					dir = "Student?action=QRY";
				}

			} else {
				request.setAttribute("student", student);
				dir = "/view/student/studentInsert.jsp";
			}
			break;
	
		case "UPD":
			student = new StudentModel();
			user = new User();
			String id = request.getParameter("sid");
			if (id != null)
				student = objStudentCont.getStudentById(Integer.valueOf(id));
			
			message = verify(request, student);

			
			if (message == null) {
				student = getStudent(request);
				message = objStudentCont.editStudent(student);

				if (message == null) {
					
					request.setAttribute("student", student);
					dir = "/view/student/studentUpd.jsp";
				} else {
					
					if (request.getSession().getAttribute("LoggedInUser") == null)
					{
						dir = "Login?action=LOGIN";
					}
					else
					{
						 user = (User) request.getSession().getAttribute("LoggedInUser");

					}
					
					if (user.getRole().name().equals("Student"))
					{
						
						request.setAttribute("message", message);
						dir = "/welcome.jsp";
						break;
						
					}
					dir = "Student?action=QRY";
				}

			} else {
				
				request.setAttribute("student", student);
				dir = "/view/student/studentUpd.jsp";
			}
			break;

		case "DEL":
			id = request.getParameter("sid");
			student = new StudentModel();
			if (id != null) {
				student = objStudentCont.getStudentById(Integer.valueOf(id));
				objStudentCont.removeStudent(student.getId());
			} else {
				message = "Delete Failed";
				dir = "Student?action=QRY";
			}

			dir = "Student?action=QRY";
			break;
			
		
			
		case "SEARCH":
			
			
			List<ConsultancyModel> lstb = new ArrayList<>();
			
			
			List<CountryModel> countries = new ArrayList<CountryModel>();
			countries = countryDAO.getAllCountries();
			getSearchPara(request);
			List<UniversityModel> lstUni = new ArrayList<>();
			if (universityId == null)
				{
				universityId = "0";
				
				}
			if ( countryId == null)
				countryId = "0";
				lstb = consultancyDAO.getAllConsultancByUniversityAndCountry(Integer.valueOf(universityId),Integer.valueOf(countryId));	
		
                                if(countryId.equals("0")){
                                        
                                    lstUni = universityDAO.getAllUniversities();
                                }
                                else
                                {
                                   lstUni = universityDAO.getAllUniversitiesByCountry(Integer.valueOf(countryId));
                                   
                                  
                                }
			 request.setAttribute("countries", countries);
			request.setAttribute("lista", lstb);
			request.setAttribute("universities", lstUni);
			dir = "/view/student/searchconsultancy.jsp";
			break;
                        
                        
		default:
			message = "No Action";

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

	private String verify(HttpServletRequest request, StudentModel student) {

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

	public StudentModel getStudent(HttpServletRequest request) {

		String fullName = request.getParameter("name");
		String address = request.getParameter("address");
		String pName = request.getParameter("pName");
		String email = request.getParameter("email");
		String phone = request.getParameter("contact");
		String id = request.getParameter("id");
		String userId = request.getParameter("userId");

		StudentModel student = new StudentModel();
		if (id != null)
			student.setId(Integer.valueOf(id));

		student.setPhone(phone);
		student.setEmail(email);
		student.setAddress(address);
		student.setParentName(pName);
		student.setName(fullName);
		
		return student;
	}
	
	public void getSearchPara(HttpServletRequest request)
	{
	
	 universityId =  request.getParameter("university");
	 countryId = request.getParameter("country");
	}
	

        
}
