package com.consultancy.user.DAO;


import com.consultancy.consultant.model.ConsultancyModel;
import com.consultancy.user.model.User;
import com.consultancy.user.model.UserRole;
import com.consultancy.user.model.UserStatus;
import com.consultancy.util.DbConnection;
import com.consultancy.util.SaltedPBKDFHash;
import com.consultancy.util.SummaryModel;
import com.mysql.jdbc.Statement;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

	private Connection conn;
	String message = null;

	DbConnection dbCon = new DbConnection();
	public UserDAO() {
		
		conn = dbCon.getConnection();
	}

	public int addUser(User userBean) throws NoSuchAlgorithmException, InvalidKeySpecException {
		try {

			String sql = "INSERT INTO users(role,fullname,username,password,status,email,contact_no) "
					+ " VALUES (?, ?, ? ,?,?,?,?)";

			userBean.setPassword(SaltedPBKDFHash.createHash(userBean.getPassword()));
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);

			ps.setString(1, userBean.getRole().name());
			ps.setString(2, userBean.getFullName());
			ps.setString(3, userBean.getUserName());
			ps.setString(4, userBean.getPassword());
			ps.setString(5, userBean.getStatus().name());
			ps.setString(6, userBean.getEmail());
			ps.setString(7, userBean.getContactNo());
			ps.executeUpdate();
			int id  = 0;
			 ResultSet generatedKeys = ps.getGeneratedKeys();
	            if (generatedKeys.next()) {
	              id =  generatedKeys.getInt(1);
	            }
	            message = "Save Successfull";
			return id;
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void removeUser(int userId) {
		try {
			String sql = "DELETE FROM users WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ps.executeUpdate();
			message = "Delete Successfull";
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String editUser(User userBean) {
		try {

			String sql = "UPDATE users SET role=?, fullname=?, username=?,status =?,email =? ,contact_no =? "
					+ " WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userBean.getRole().name());
			ps.setString(2, userBean.getFullName());
			ps.setString(3, userBean.getUserName());
			ps.setString(4, userBean.getStatus().name());
			ps.setString(5, userBean.getEmail());
			ps.setString(6, userBean.getContactNo());
			ps.setInt(7, userBean.getUserId());
			ps.executeUpdate();
			message = "Update Successfull";
			return message;

		} catch (SQLException e) {
			e.printStackTrace();
			return message;
		}
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();
		try {
			String sql = "SELECT * FROM users";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User userBean = new User();
				userBean.setUserId(rs.getInt("id"));
				userBean.setRole(UserRole.valueOf(rs.getString("role")));
				userBean.setStatus(UserStatus.valueOf(rs.getString("status")));
				userBean.setFullName(rs.getString("fullname"));
				userBean.setUserName(rs.getString("username"));
				userBean.setPassword(rs.getString("password"));
				userBean.setEmail(rs.getString("email"));
				userBean.setContactNo(rs.getString("contact_no"));

				users.add(userBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return users;
	}
	
	
	 public User isValidLogin(String usename,String role, String password) throws NoSuchAlgorithmException, InvalidKeySpecException{
	        User user = getUserByUsernameAndRole(usename,role);
	        if (user == null)
	        {
	        	return null;
	        }
	        
	        if (user.getUserId() == 0 )
	        {
	        	return null;
	        }
	        
	        if (user == null || !SaltedPBKDFHash.validatePassword(password, user.getPassword())) {
	        	return null;
	        }
	        return user;
	    }

	public User getUserById(int userId) {
		User userBean = new User();
		try {
			String sql = "SELECT * FROM users WHERE id=?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				userBean.setUserId(rs.getInt("id"));
				userBean.setRole(UserRole.valueOf(rs.getString("Role")));
				userBean.setStatus(UserStatus.valueOf(rs.getString("Status")));
				userBean.setFullName(rs.getString("fullname"));
				userBean.setUserName(rs.getString("username"));
				userBean.setPassword(rs.getString("password"));
				userBean.setEmail(rs.getString("email"));
				userBean.setContactNo(rs.getString("contact_no"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return userBean;
	}
	
	public User getUserByUsernameAndRole(String username , String role) {
		User userBean = new User();
		try {
			String sql = "SELECT * FROM users WHERE username =? and role =?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, username);
			ps.setString(2, role);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				userBean.setUserId(rs.getInt("id"));
				userBean.setRole(UserRole.valueOf(rs.getString("Role")));
				userBean.setStatus(UserStatus.valueOf(rs.getString("Status")));
				userBean.setFullName(rs.getString("fullname"));
				userBean.setUserName(rs.getString("username"));
				userBean.setPassword(rs.getString("password"));
				userBean.setEmail(rs.getString("email"));
				userBean.setContactNo(rs.getString("contact_no"));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (userBean.getUserId() == 0)
			return null;
		
		return userBean;
	}
	
	public List<SummaryModel> loadSummary(User user)
	{
		List<SummaryModel> lstSum = new ArrayList<>();
		
		
		SummaryModel sumModel = new SummaryModel();
		
		sumModel = new SummaryModel();
		sumModel.setName("Total Country Listed");
		int cnt2 = dbCon.getCount("country","id","");
		sumModel.setCount(String.valueOf(cnt2));
		lstSum.add(sumModel);
		
		sumModel = new SummaryModel();
		sumModel.setName("Total Consultancy Listed");
		int cnt4 = dbCon.getCount("consultant","id","");
		sumModel.setCount(String.valueOf(cnt4));
		lstSum.add(sumModel);
		
		sumModel = new SummaryModel();
		sumModel.setName("Total University Listed");
		int cnt = dbCon.getCount("university","id","");
		sumModel.setCount(String.valueOf(cnt));
		lstSum.add(sumModel);
		
		sumModel = new SummaryModel();
		sumModel.setName("Total Student Listed");
		int cnt1 = dbCon.getCount("student_record","id"," ");
		sumModel.setCount(String.valueOf(cnt1));
		lstSum.add(sumModel);
		
		sumModel = new SummaryModel();
		sumModel.setName("Total User Listed");
		int cnt5 = dbCon.getCount("users","id"," ");
		sumModel.setCount(String.valueOf(cnt5));
		lstSum.add(sumModel);
		
		return lstSum;
	}

}
