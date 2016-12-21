package com.consultancy.consultant.DAO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.consultancy.consultant.model.ConsultancyModel;
import com.consultancy.student.model.StudentModel;
import com.consultancy.user.model.User;
import com.consultancy.util.DbConnection;
import com.consultancy.util.SaltedPBKDFHash;
import com.consultancy.util.SummaryModel;

public class ConsultancyDAO {

	private Connection conn;
	String message = null;
	DbConnection dbCon = new DbConnection();

	public ConsultancyDAO() {

		conn = dbCon.getConnection();
	}

	public String addConsultancy(ConsultancyModel consult) {
		try {

			String sql = "INSERT INTO consultant(name,address,user_id,website,email,phone,contact_person,approch_count,latlng) "
					+ " VALUES (?, ?, ? ,?,?,?,?,0,?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, consult.getName());
			ps.setString(2, consult.getAddress());
			ps.setInt(3, consult.getUserId());
			ps.setString(4, consult.getWebsite());
			ps.setString(5, consult.getEmail());
			ps.setString(6, consult.getPhone());
			ps.setString(7, consult.getContactPerson());
                        ps.setString(8, consult.getLatlng());
			ps.executeUpdate();
			message = "Save Successfull";
			return message;
		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public String addConsultancyStudent(ConsultancyModel consult, StudentModel student) {
		try {

			String sql = "INSERT INTO student_consultant_record(student_id,consultant_id) " + " VALUES (?, ?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, student.getId());
			ps.setInt(2, consult.getId());
			ps.executeUpdate();
			
			
			String updSQl = "UPDATE consultant set approch_count = approch_count+1 WHERE id = "+consult.getId();
			PreparedStatement upd = conn.prepareStatement(updSQl);
			upd.executeUpdate();
			message = "Save Successfull";
			return message;
		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public void removeConsultancy(int consultID) {
		try {
			String sql = "DELETE FROM consultant WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, consultID);
			ps.executeUpdate();
			message = "Delete Successfull";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String editConsultancy(ConsultancyModel consult) {
		try {

			String sql = "UPDATE consultant SET name = ?,address=?,website=?,email=?,phone=?,contact_person=? ,latlng = ?"
					+ " WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, consult.getName());
			ps.setString(2, consult.getAddress());
			ps.setString(3, consult.getWebsite());
			ps.setString(4, consult.getEmail());
			ps.setString(5, consult.getPhone());
			ps.setString(6, consult.getContactPerson());
                        ps.setString(7, consult.getLatlng());
			ps.setInt(8, consult.getId());
			ps.executeUpdate();
			message = "Update Successfull";
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public List<ConsultancyModel> getAllConsultancys() {
		List<ConsultancyModel> lstConsult = new ArrayList<ConsultancyModel>();
		try {
			String sql = "SELECT * FROM consultant";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConsultancyModel consult = new ConsultancyModel();
				consult.setId(rs.getInt("id"));
				consult.setName(rs.getString("name"));
				consult.setUserId(rs.getInt("user_id"));
				consult.setAddress(rs.getString("address"));
				consult.setContactPerson(rs.getString("contact_person"));
				consult.setPhone(rs.getString("phone"));
				consult.setEmail(rs.getString("email"));
				consult.setWebsite(rs.getString("website"));
                                consult.setLatlng(rs.getString("latlng").replace("(", "").replace(")", ""));
				lstConsult.add(consult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstConsult;
	}
	
	public List<ConsultancyModel> getAllConsultancyDesc() {
		List<ConsultancyModel> lstConsult = new ArrayList<ConsultancyModel>();
		try {
			String sql = "SELECT * FROM consultant ORDER BY approch_count desc";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConsultancyModel consult = new ConsultancyModel();
				consult.setId(rs.getInt("id"));
				consult.setName(rs.getString("name"));
				consult.setUserId(rs.getInt("user_id"));
				consult.setAddress(rs.getString("address"));
				consult.setContactPerson(rs.getString("contact_person"));
				consult.setPhone(rs.getString("phone"));
				consult.setEmail(rs.getString("email"));
				consult.setWebsite(rs.getString("website"));
                                consult.setLatlng(rs.getString("latlng").replace("(", "").replace(")", ""));
				lstConsult.add(consult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstConsult;
	}



	public List<ConsultancyModel> getAllConsultancByUniversityAndCountry(int uniId, int countryId) {
		List<ConsultancyModel> lstConsult = new ArrayList<ConsultancyModel>();
		try {
			String cond = "";
			String sql = "SELECT con.* from consultant con \n"
					+ "INNER JOIN consultant_university_record uni on uni.consultant_id = con.id\n"
					+ "INNER JOIN university u on u.id = uni.university_id\n"
					+ " INNER JOIN country coun on coun.id = u.country_id ";

			if (countryId != 0) {
				cond += " coun.id = " + countryId;
			}
			if (uniId != 0) {
				if (!cond.equalsIgnoreCase(""))
					cond += " and ";
				cond += "u.id =" + uniId;
			}
			if (!cond.equals("")) {
				sql += " and " + cond;
			}

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ConsultancyModel consult = new ConsultancyModel();
				consult.setId(rs.getInt("id"));
				consult.setName(rs.getString("name"));
				consult.setUserId(rs.getInt("user_id"));
				consult.setAddress(rs.getString("address"));
				consult.setContactPerson(rs.getString("contact_person"));
				consult.setPhone(rs.getString("phone"));
				consult.setEmail(rs.getString("email"));
				consult.setWebsite(rs.getString("website"));
                                consult.setLatlng(rs.getString("latlng").replace("(", "").replace(")", ""));
				lstConsult.add(consult);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstConsult;
	}

	public ConsultancyModel getConsultancyById(int userId) {
		ConsultancyModel consult = new ConsultancyModel();
		try {
			String sql = "SELECT * FROM consultant WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				consult.setId(rs.getInt("id"));
				consult.setName(rs.getString("name"));
				consult.setUserId(rs.getInt("user_id"));
				consult.setAddress(rs.getString("address"));
				consult.setContactPerson(rs.getString("contact_person"));
				consult.setPhone(rs.getString("phone"));
				consult.setEmail(rs.getString("email"));
				consult.setWebsite(rs.getString("website"));
                                if (rs.getString("latlng") != null)
                                consult.setLatlng(rs.getString("latlng").replace("(", "").replace(")", ""));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return consult;
	}

	public ConsultancyModel getConsultancyByUserId(int userId) {
		ConsultancyModel consult = new ConsultancyModel();
		try {
			String sql = "SELECT * FROM consultant WHERE user_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {

				consult.setId(rs.getInt("id"));
				consult.setName(rs.getString("name"));
				consult.setUserId(rs.getInt("user_id"));
				consult.setAddress(rs.getString("address"));
				consult.setContactPerson(rs.getString("contact_person"));
				consult.setPhone(rs.getString("phone"));
				consult.setEmail(rs.getString("email"));
				consult.setWebsite(rs.getString("website"));
                                if (rs.getString("latlng") != null)
                                consult.setLatlng(rs.getString("latlng").replace("(", "").replace(")", ""));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return consult;
	}

	public List<SummaryModel> loadSummary(User user) {
		List<SummaryModel> lstSum = new ArrayList<>();
		ConsultancyModel conModel = new ConsultancyModel();
		conModel = getConsultancyByUserId(user.getUserId());

		SummaryModel sumModel = new SummaryModel();
		sumModel.setName("Name");
		sumModel.setCount(conModel.getName());
		lstSum.add(sumModel);

		sumModel = new SummaryModel();
		sumModel.setName("Address");
		sumModel.setCount(conModel.getAddress());
		lstSum.add(sumModel);

		sumModel = new SummaryModel();
		sumModel.setName("Phone");
		sumModel.setCount(conModel.getPhone());
		lstSum.add(sumModel);

		sumModel = new SummaryModel();
		sumModel.setName("Total University Listed");
		int cnt = dbCon.getCount("university", "u.id",
				"u INNER JOIN consultant_university_record c on u.id = c.university_id WHERE c.consultant_id = "
						+ conModel.getId());
		sumModel.setCount(String.valueOf(cnt));
		lstSum.add(sumModel);

		sumModel = new SummaryModel();
		sumModel.setName("Total Student Listed");
		int cnt1 = dbCon.getCount("student_record", "u.id",
				"u INNER JOIN student_consultant_record c on u.id = c.student_id WHERE c.consultant_id = "
						+ conModel.getId());
		sumModel.setCount(String.valueOf(cnt1));
		lstSum.add(sumModel);

		return lstSum;
	}

}
