package com.consultancy.university.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.consultancy.consultant.model.ConsultancyModel;
import com.consultancy.student.model.StudentModel;
import com.consultancy.university.model.UniversityModel;
import com.consultancy.util.DbConnection;
import com.mysql.jdbc.Statement;

public class UniversityDAO {
	private Connection conn;
	String message = null;

	public UniversityDAO() {
		DbConnection dbCon = new DbConnection();
		conn = dbCon.getConnection();
	}

	public int addUniversity(UniversityModel university) {
		try {

			String sql = "INSERT INTO university(name,description,country_id,address,contact,website,latlng) " + " VALUES (?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, university.getName());
			ps.setString(2, university.getDescription());
			ps.setInt(3, university.getCountryId());
			ps.setString(4, university.getAddress());
			ps.setString(5, university.getContact());
			ps.setString(6, university.getWebsite());
                        ps.setString(7, university.getLatlang());
                        ps.executeUpdate();
			message = "Save Successfull";
			int id = 0;
			ResultSet generatedKeys = ps.getGeneratedKeys();
			if (generatedKeys.next()) {
				id = generatedKeys.getInt(1);
			}

			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public String addConsultancyUniversity(ConsultancyModel consult,UniversityModel university)  {
		try {

			String sql = "INSERT INTO consultant_university_record(university_id,consultant_id) "
					+ " VALUES (?, ?)";

			
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, university.getUniId());
			ps.setInt(2, consult.getId());
			ps.executeUpdate();
			message = "Save Successfull";
			return message;
		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}
	
	public void removeUniversity(int uniId) {
		try {
			String sql = "DELETE FROM university WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, uniId);
			ps.executeUpdate();
			message = "Delete Successfull";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String updateUniversity(UniversityModel university) {
		try {

			String sql = "UPDATE university SET name=?, description=?,country_id=?, address=?,contact=?,website=?,latlng=?" + " WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, university.getName());
			ps.setString(2, university.getDescription());
			ps.setInt(3, university.getCountryId());
			ps.setInt(8, university.getUniId());
			ps.setString(4, university.getAddress());
			ps.setString(5, university.getContact());
			ps.setString(6, university.getWebsite());
                        ps.setString(7, university.getLatlang());
			ps.executeUpdate();
			message = "Update Successfull";
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public List<UniversityModel> getAllUniversities() {
		List<UniversityModel> lstUniversity = new ArrayList<UniversityModel>();
		try {
			String sql = "SELECT u.*,coun.name as country_name FROM university u inner join country coun on coun.id = u.country_id ";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UniversityModel universityModel = new UniversityModel();
				universityModel.setUniId(rs.getInt("id"));
				universityModel.setCountryId(rs.getInt("country_id"));
				universityModel.setCountry(rs.getString("country_name"));
				universityModel.setName(rs.getString("name"));
				universityModel.setDescription(rs.getString("description"));
				universityModel.setAddress(rs.getString("address"));
				universityModel.setContact(rs.getString("contact"));
				universityModel.setWebsite(rs.getString("website"));
                                universityModel.setLatlang(rs.getString("latlng").replace("(", "").replace(")", ""));

				lstUniversity.add(universityModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstUniversity;
	}

	public List<UniversityModel> getAllUniversitiesByCountry(int countryId) {
		List<UniversityModel> lstUniversity = new ArrayList<UniversityModel>();
		try {
			String sql = "SELECT * FROM university where country_id = " + countryId;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UniversityModel universityModel = new UniversityModel();
				universityModel.setUniId(rs.getInt("id"));
				universityModel.setCountryId(rs.getInt("country_id"));
				universityModel.setName(rs.getString("name"));
				universityModel.setDescription(rs.getString("description"));
				universityModel.setAddress(rs.getString("address"));
				universityModel.setContact(rs.getString("contact"));
				universityModel.setWebsite(rs.getString("website"));
                                universityModel.setLatlang(rs.getString("latlng").replace("(", "").replace(")", ""));
				lstUniversity.add(universityModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstUniversity;
	}
	
	public List<UniversityModel> getAllUniversitiesByConsultancy(int consultancyId) {
		List<UniversityModel> lstUniversity = new ArrayList<UniversityModel>();
		try {
			String sql = "SELECT u.*,coun.name as country_name FROM university u inner join country coun on coun.id = u.country_id"
					+ " INNER JOIN consultant_university_record c on c.university_id = u.id where c.consultant_id =" + consultancyId;
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				UniversityModel universityModel = new UniversityModel();
				universityModel.setUniId(rs.getInt("id"));
				universityModel.setCountryId(rs.getInt("country_id"));
				universityModel.setCountry(rs.getString("country_name"));
				universityModel.setName(rs.getString("name"));
				universityModel.setDescription(rs.getString("description"));
				universityModel.setAddress(rs.getString("address"));
				universityModel.setContact(rs.getString("contact"));
				universityModel.setWebsite(rs.getString("website"));
                                 universityModel.setLatlang(rs.getString("latlng").replace("(", "").replace(")", ""));
				lstUniversity.add(universityModel);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstUniversity;
	}

	public UniversityModel getUniversityById(int countryId) {

		UniversityModel universityModel = new UniversityModel();
		try {
			String sql = "SELECT * FROM university WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, countryId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				universityModel.setUniId(rs.getInt("id"));
				universityModel.setCountryId(rs.getInt("country_id"));
				universityModel.setName(rs.getString("name"));
				universityModel.setDescription(rs.getString("description"));
				universityModel.setAddress(rs.getString("address"));
				universityModel.setContact(rs.getString("contact"));
				universityModel.setWebsite(rs.getString("website"));
                                universityModel.setLatlang(rs.getString("latlng").replace("(", "").replace(")", ""));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return universityModel;
	}

}
