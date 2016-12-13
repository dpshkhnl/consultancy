package com.consultancy.country.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.consultancy.country.DAO.CountryDAO;
import com.consultancy.country.model.CountryModel;

/**
 * Servlet implementation class CountryController
 */
@WebServlet("/Country")
public class CountryController extends HttpServlet {
	

		private static final long serialVersionUID = 1L;


		protected void service(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {

			CountryDAO objCountryCont = new CountryDAO();
			String action = request.getParameter("action");
			String message = null;
			String dir = null;
			
			switch (action) {
			
			case "QRY":
				/*List<User> lista = service.contactosQry();*/
				
				List<CountryModel> lista = new ArrayList<CountryModel>();
				lista = objCountryCont.getAllCountries();
				request.setAttribute("lista", lista);
	            dir = "/view/country/countryQry.jsp";
				
				break;
				
			case "INS":
				
				CountryModel country = new CountryModel();
	            message = verify(request, country);
	            
				if (message == null) {
					country = getCountry(request);
					message =objCountryCont.addCountry(country);
	                
	                if (message == null) {
	                	 request.setAttribute("country", country);
	                     dir = "/view/country/countryInsert.jsp";
	                } else {
	                	dir = "Country?action=QRY";
	                }
	                
	            } else {
	            	 request.setAttribute("user", country);
	                 dir = "/view/country/countryInsert.jsp";
	            }
				break;
	               
	         
		case "UPD":
				String id = request.getParameter("id");
				country = new CountryModel();
				if (id !=null)
					country = objCountryCont.getCountryById(Integer.valueOf(id));
	            message = verify(request, country);
	            
				if (message == null) {
					country = getCountry(request);
					message =objCountryCont.updateCountry(country);
	                
	                if (message == null) {
	                	 request.setAttribute("country", country);
	                     dir = "/view/country/countryUpd.jsp";
	                } else {
	                	dir = "Country?action=QRY";
	                }
	                
	            } else {
	            	 request.setAttribute("country", country);
	            	  dir = "/view/country/countryUpd.jsp";
	            }
				break;
				
			 case "DEL":
				  id = request.getParameter("id");
					country = new CountryModel();
					if (id !=null){
						country = objCountryCont.getCountryById(Integer.valueOf(id));                   
						objCountryCont.removeCountry(country.getCountryId());
	            } else {
	                message = "Delete Failed";
	                dir = "Country?action=QRY";
	            }

	            dir = "Country?action=QRY";
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
		
		private String verify(HttpServletRequest request, CountryModel country) {
		        
		        
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
		
		public CountryModel getCountry(HttpServletRequest request)
		{
			
			String id = request.getParameter("id");
			String name = request.getParameter("name");
	        String description = request.getParameter("description");
	        
	        CountryModel country = new CountryModel();
	        if (id != null)
	        country.setCountryId(Integer.valueOf(id));
	        country.setName(name);
	        country.setDescription(description);
	        
	          
			return country;
		}

	

}
