package daos;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import models.User;
import utility.Utility;

@Repository
public class AccountDAO {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public boolean addAccount(User user) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public List<User> getAllUsers() {
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userList = session.createQuery("from User").list();
		return userList;
	}

	public boolean deleteAccount(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			User user = (User) session.load(User.class, new Integer(id));
			if (user != null) {
				session.delete(user);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public User getUser(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		User user = (User) session.get(User.class, new Integer(id));
		return user;
	}

	public boolean updateAccount(User user) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.update(user);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	Authentication

	public String doAdminLogin(String email, String password, HttpServletResponse response) {
		String result = "";
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userlist = session.createQuery("select u from User u where u.email='" + email + "'").list();

		if (userlist.size() == 1) {
			User u = userlist.get(0);
			if (u.getPassword().equals(Utility.encryptPasswordMD5(password))) {
				if (u.isIsadmin()) {
					Cookie adminLogin = new Cookie("adminLogin", String.valueOf(u.getId()));
					adminLogin.setMaxAge(60 * 60 * 24);
					response.addCookie(adminLogin);
					result = "success";
				} else {
					result = "fail admin";
				}
			} else {
				result = "fail password";
			}
		} else {
			result = "fail email";
		}
		return result;
	}

	public String doUserLogin(String email, String password, HttpServletResponse response) {
		String result = "";
		Session session = this.sessionFactory.getCurrentSession();
		List<User> userlist = session.createQuery("select u from User u where u.email='" + email + "'").list();
		if (userlist.size() == 1) {
			User u = userlist.get(0);
			if (u.getPassword().equals(Utility.encryptPasswordMD5(password))) {
				Cookie userLogin = new Cookie("userLogin", String.valueOf(u.getId()));
				userLogin.setMaxAge(60 * 60 * 24);
				response.addCookie(userLogin);
				result = "success";
			} else {
				result = "fail password";
			}
		} else {
			result = "fail email";
		}
		return result;
	}

	public void logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("adminLogin")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public void logoutUser(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("userLogin")) {
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	public void logoutAll(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			response.addCookie(cookie);
		}
	}
}
