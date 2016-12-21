package com.consultancy.student.DAO;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.consultancy.student.model.StudentModel;
import com.consultancy.util.DbConnection;
import com.mysql.jdbc.Statement;

public class StudentDAO {

	private Connection conn;
	String message = null;

	public StudentDAO() {
		DbConnection dbCon = new DbConnection();
		conn = dbCon.getConnection();
	}

	public int addStudent(StudentModel student) {
		try {

			String sql = "INSERT INTO student_record(student_name,address,user_id,parents_name,email,phone) "
					+ " VALUES (?, ?, ? ,?,?,?)";

			
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, student.getName());
			ps.setString(2, student.getAddress());
			ps.setInt(3, student.getUserId());
			ps.setString(4, student.getParentName());
			ps.setString(5, student.getEmail());
			ps.setString(6, student.getPhone());
			
			ps.executeUpdate();
			
			int id  = 0;
			 ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	              id =  generatedKeys.getInt(1);
	            }
			
			return id;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void removeStudent(int stuId) {
		try {
                    
                    String sql = "DELETE FROM student_record WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, stuId);
			ps.executeUpdate();
                    
			String sql1 = "DELETE FROM student_consultant_record WHERE id=?";
			PreparedStatement ps1 = conn.prepareStatement(sql1);
			ps1.setInt(1, stuId);
			ps1.executeUpdate();
			message = "Delete Successfull";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String editStudent(StudentModel student) {
		try {

			String sql = "UPDATE student_record SET student_name =?,address=?,parents_name=?,email=?,phone=? "
					+ " WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, student.getName());
			ps.setString(2, student.getAddress());
			ps.setString(3, student.getParentName());
			ps.setString(4, student.getEmail());
			ps.setString(5, student.getPhone());
			ps.setInt(6, student.getId());
			ps.executeUpdate();
			message = "Update Successfull";
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public List<StudentModel> getAllStudent() {
		List<StudentModel> lstStudent = new ArrayList<StudentModel>();
		try {
			String sql = "SELECT * FROM student_record";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				StudentModel student = new StudentModel();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("student_name"));
				student.setUserId(rs.getInt("user_id"));
				student.setAddress(rs.getString("address"));
				student.setParentName(rs.getString("parents_name"));
				student.setPhone(rs.getString("phone"));
				student.setEmail(rs.getString("email"));

				lstStudent.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return lstStudent;
	}
	
	
	
	public StudentModel getStudentById(int userId) {
		StudentModel student = new StudentModel();
		try {
			String sql = "SELECT * FROM student_record WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("student_name"));
				student.setUserId(rs.getInt("user_id"));
				student.setAddress(rs.getString("address"));
				student.setParentName(rs.getString("parents_name"));
				student.setPhone(rs.getString("phone"));
				student.setEmail(rs.getString("email"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public StudentModel getStudentByUserId(int userId) {
		StudentModel student = new StudentModel();
		try {
			String sql = "SELECT * FROM student_record WHERE user_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("student_name"));
				student.setUserId(rs.getInt("user_id"));
				student.setAddress(rs.getString("address"));
				student.setParentName(rs.getString("parents_name"));
				student.setPhone(rs.getString("phone"));
				student.setEmail(rs.getString("email"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return student;
	}
	
	public List<StudentModel> getStudentByConsultancy(int consultancyId) {
		
		List<StudentModel> lstStudent = new ArrayList<StudentModel>();
		
		try {
			String sql = "SELECT s.* FROM student_record s "
					+ " INNER JOIN student_consultant_record c on c.student_id = s.id where c.consultant_id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, consultancyId);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				StudentModel student = new StudentModel();
				student.setId(rs.getInt("id"));
				student.setName(rs.getString("student_name"));
				student.setUserId(rs.getInt("user_id"));
				student.setAddress(rs.getString("address"));
				student.setParentName(rs.getString("parents_name"));
				student.setPhone(rs.getString("phone"));
				student.setEmail(rs.getString("email"));
				lstStudent.add(student);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lstStudent;
	}
	
	
}
