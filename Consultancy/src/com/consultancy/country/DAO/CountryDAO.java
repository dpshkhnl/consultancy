package com.consultancy.country.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.consultancy.country.model.CountryModel;
import com.consultancy.user.model.User;
import com.consultancy.user.model.UserRole;
import com.consultancy.user.model.UserStatus;
import com.consultancy.util.DbConnection;

public class CountryDAO {

	


    private Connection conn;
    String message = null;

    public CountryDAO() {
    	DbConnection dbCon = new DbConnection();
    	conn = dbCon.getConnection();
    }

  public String addCountry(CountryModel country) {
        try {
        	
        	String sql = "INSERT INTO country(name,description) " +
            		" VALUES (?, ?)";
                    
            PreparedStatement ps = conn.prepareStatement(sql);
            
            ps.setString(1, country.getName());
            ps.setString(2, country.getDescription());
            ps.executeUpdate();
            message = "Save Successfull";
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
            return message;
        }
    }

      public void removeCountry(int countryId) {
        try {
        	String sql = "DELETE FROM country WHERE id=?";
            PreparedStatement ps = conn
                    .prepareStatement(sql);
            ps.setInt(1, countryId);
            ps.executeUpdate();
            message = "Delete Successfull";
        } catch (SQLException e) {
            e.printStackTrace();
        }
      }
      
    public String updateCountry(CountryModel country) {    	
    	try {
    		
    		String sql = "UPDATE country SET name=?, description=?"+
            " WHERE id=?";
            PreparedStatement ps = conn
                    .prepareStatement(sql);
            ps.setString(1, country.getName());
            ps.setString(2, country.getDescription());
            ps.setInt(3, country.getCountryId());
            ps.executeUpdate(); 
            message = "Update Successfull";
            return message;

        } catch (SQLException e) {
            e.printStackTrace();
            return message;
        }
    }
    
    public List<CountryModel> getAllCountries() {
        List<CountryModel> lstCountry = new ArrayList<CountryModel>();
        try {
        	String sql = "SELECT * FROM country";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CountryModel countryModel = new CountryModel();
                countryModel.setCountryId(rs.getInt("id"));
                countryModel.setName(rs.getString("name")); 
                countryModel.setDescription(rs.getString("description")); 

                lstCountry.add(countryModel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lstCountry;
    }

    public CountryModel getCountryById(int countryId) {

    	CountryModel countryModel = new CountryModel();
        try {
        	String sql = "SELECT * FROM country WHERE id=?";
            PreparedStatement ps = conn.
                    prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                countryModel.setCountryId(rs.getInt("id"));
                countryModel.setName(rs.getString("name")); 
                countryModel.setDescription(rs.getString("description")); 
                      
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return countryModel;
    }
    
    
}
