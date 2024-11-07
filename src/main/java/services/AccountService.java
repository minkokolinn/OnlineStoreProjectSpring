package services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daos.AccountDAO;
import models.User;

@Service("accountService")
public class AccountService {
	
	@Autowired
	AccountDAO accountDao;
	
	@Transactional
	public boolean addAccount(User user) {
		return accountDao.addAccount(user);
	}
	
	@Transactional
	public List<User> getAllUsers(){
		return accountDao.getAllUsers();
	}
	
	@Transactional
	public boolean deleteAccount(int id) {
		return accountDao.deleteAccount(id);
	}
	
	@Transactional
	public boolean updateAccount(User user) {
		return accountDao.updateAccount(user);
	}
	
	@Transactional
	public User getUser(int id) {
		return accountDao.getUser(id);
	}
	
	@Transactional
	public String doAdminLogin(String email, String password, HttpServletResponse response) {
		return accountDao.doAdminLogin(email, password, response);
	}
	
	@Transactional
	public String doUserLogin(String email, String password, HttpServletResponse response) {
		return accountDao.doUserLogin(email, password, response);
	}
	
	@Transactional
	public void logoutAdmin(HttpServletRequest request,HttpServletResponse response) {
		accountDao.logoutAdmin(request, response);
	}
	
	@Transactional
	public void logoutUser(HttpServletRequest request,HttpServletResponse response) {
		accountDao.logoutUser(request, response);
	}
	
	@Transactional
	public void logoutAll(HttpServletRequest request,HttpServletResponse response) {
		accountDao.logoutAll(request, response);
	}
}
