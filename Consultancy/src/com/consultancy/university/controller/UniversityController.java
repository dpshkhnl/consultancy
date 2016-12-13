package com.consultancy.university.controller;

import java.io.IOException;
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
import com.consultancy.university.DAO.UniversityDAO;
import com.consultancy.university.model.UniversityModel;
import com.consultancy.user.model.User;

@WebServlet("/University")
public class UniversityController extends HttpServlet {
	

	private static final long serialVersionUID = 1L;


	protected void service(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		UniversityDAO objUniversityCont = new UniversityDAO();
		CountryDAO countryDAO = new CountryDAO();
		ConsultancyDAO consultancyDAO = new ConsultancyDAO();
		String action = request.getParameter("action");
		String message = null;
		String dir = null;
		
		switch (action) {
		
		case "QRY":
			/*List<User> lista = service.contactosQry();*/
			UniversityModel university = new UniversityModel();
			User user = new User();

			List<UniversityModel> lista = new ArrayList<UniversityModel>();
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
				lista = objUniversityCont.getAllUniversitiesByConsultancy(consultancy.getId());
			}
			else
			{
				lista = objUniversityCont.getAllUniversities();
			}
			
			
			
			request.setAttribute("lista", lista);
			if (user.getRole().name().equals("Student"))
			{
				dir = "/view/university/universitview.jsp";
			}
			else
            dir = "/view/university/universityQry.jsp";
			
			break;
			
		case "INS":
			
			List<CountryModel> countries = new ArrayList<CountryModel>();
			countries = countryDAO.getAllCountries();
			
			 university = new UniversityModel();
			 user = new User();
            message = verify(request, university);
            
			if (message == null) {
				university = getUniversity(request);
				int id  =objUniversityCont.addUniversity(university);
				
				if (request.getSession().getAttribute("LoggedInUser") == null)
				{
					dir = "Login?action=LOGIN";
				}
				else
				{
					 user = (User) request.getSession().getAttribute("LoggedInUser");
	
				}
				
				if (id != 0){
					message = "success";
					if (user.getRole().name().equals("Consultancy"))
					{
						university = objUniversityCont.getUniversityById(id);
						ConsultancyModel consultancy = new ConsultancyModel();
						consultancy = consultancyDAO.getConsultancyByUserId(user.getUserId());
						message = objUniversityCont.addConsultancyUniversity(consultancy,university);
					}
					}
                
                if (message == null) {
                	 request.setAttribute("university", university);
                	 request.setAttribute("countries", countries);
                     dir = "/view/university/universityInsert.jsp";
                } else {
                	dir = "University?action=QRY";
                }
                
            } else {
            	 request.setAttribute("user", university);
            	 request.setAttribute("countries", countries);
                 dir = "/view/university/universityInsert.jsp";
            }
			break;
               
         
	case "UPD":
		 countries = new ArrayList<CountryModel>();
		countries = countryDAO.getAllCountries();
			String id = request.getParameter("id");
			university = new UniversityModel();
			if (id !=null)
				university = objUniversityCont.getUniversityById(Integer.valueOf(id));
            message = verify(request, university);
            
			if (message == null) {
				university = getUniversity(request);
				message =objUniversityCont.updateUniversity(university);
                
                if (message == null) {
                	 request.setAttribute("university", university);
                	 request.setAttribute("countries", countries);
                     dir = "/view/university/universityUpd.jsp";
                } else {
                	dir = "University?action=QRY";
                }
                
            } else {
            	 request.setAttribute("university", university);
            	 request.setAttribute("countries", countries);
            	  dir = "/view/university/universityUpd.jsp";
            }
			break;
			
		 case "DEL":
			  id = request.getParameter("id");
				university = new UniversityModel();
				if (id !=null){
					university = objUniversityCont.getUniversityById(Integer.valueOf(id));                   
					objUniversityCont.removeUniversity(university.getUniId());
            } else {
                message = "Delete Failed";
                dir = "University?action=QRY";
            }

            dir = "University?action=QRY";
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
	
	private String verify(HttpServletRequest request, UniversityModel university) {
	        
	        
	        String message = "<ul>";
	//        System.out.println("tamano " + message.length());
	        String name = request.getParameter("name");
	        String description = request.getParameter("description");

	        
	        
	        if ((name == null) || (name.trim().length() == 0)) {
	            message += "<li>Please Enter Name</li>";
	        }
	        if ((description == null) || (description.trim().length() == 0)) {
	            message += "<li>Please Enter Description</li>";
	        }
	        
	        
	        if (message.equals("<ul>")) {
	            message = null;
	        } else {
	            message += "</ul>";
	        }
	
	        return message;
	    }
	
	public UniversityModel getUniversity(HttpServletRequest request)
	{
		
		String id = request.getParameter("id");
		String name = request.getParameter("name");
        String description = request.getParameter("description");
        String countryID = request.getParameter("country");
        String address = request.getParameter("address");
        String contact = request.getParameter("contact");
        String website = request.getParameter("website");
        String latlng = request.getParameter("latlng");
        
        
        UniversityModel university = new UniversityModel();
        if (id != null)
        university.setUniId(Integer.valueOf(id));
        university.setCountryId(Integer.valueOf(countryID));
        university.setName(name);
        university.setAddress(address);
        university.setContact(contact);
        university.setWebsite(website);
        university.setDescription(description);
         university.setLatlang(latlng);
        
          
		return university;
	}




}
