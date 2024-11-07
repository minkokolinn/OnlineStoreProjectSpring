package utility;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import models.Category;
import models.User;


public class Utility {
	private static Connection connection;
	private static Statement statement;

	public static String encryptPasswordMD5(String str) {
		MessageDigest m = null;
		try {
			m = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.update(str.getBytes());
		byte[] bytes = m.digest();
		
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return s.toString();
	}
	
	public static String generateOrderNumber() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MMdd");
		String prefixDate = formatter.format(new Date());
		
		double d = Math.random()*1000000000;
		String suffixRand = String.valueOf((int) d);
		return prefixDate+"-"+suffixRand;
	}
	
	public static int checkAdminAuth(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("adminLogin")) {
					return Integer.parseInt(cookie.getValue());
				}
			}
		}
		return 0;
	}

	public static int checkUserAuth(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userLogin")) {
					return Integer.parseInt(cookie.getValue());
				}
			}
		}
		return 0;
	}
	
	public static User getCurrentLoginedUser(int userid) throws SQLException {
		User u = new User();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/springonlinestore", "root", "admin");
		Statement st = connection.createStatement();
		String query = "select * from users where id="+userid;
		ResultSet rs = st.executeQuery(query);
		
		while(rs.next()){
			u.setName(rs.getString("name"));
			u.setEmail(rs.getString("email"));
			u.setIsadmin(rs.getBoolean("isadmin"));
		}
		return u;
	}
	
	public static List<Category> getAllCategories() throws SQLException {
		User u = new User();
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/springonlinestore", "root", "admin");
		Statement st = connection.createStatement();
		String query = "select * from categories";
		ResultSet rs = st.executeQuery(query);
		
		List<Category> categories = new ArrayList<>();
		while(rs.next()){
			Category category = new Category();
			category.setId(rs.getInt("id"));
			category.setCategory(rs.getString("category"));
			categories.add(category);
		}
		return categories;
	}
	
	
	
	public static void main(String[] args) {
		System.out.println("main started");
		int result = Utility.factorial(50000);
		System.out.println("method ended");
	}
	public static int factorial(int n) {
		if (n==1) {
			return 1;
		}else {
			return n*factorial(n-1);
		}
	}
}
