package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import models.User;
import services.AccountService;
import utility.Utility;

@Controller
public class AuthController {
	
	@Autowired
	AccountService accountService;
	
	@RequestMapping(value = "admin/login", method = RequestMethod.GET)
	public String operateAdminLoginGET(HttpServletRequest request) {
		if (Utility.checkAdminAuth(request)==0) {
			return "admin/login";
		}else {
			return "redirect:dashboard";
		}
	}
	
	@RequestMapping(value = "admin/login", method = RequestMethod.POST)
	public String operateAdminLogin(@RequestParam("emailInput") String emailInput, @RequestParam("passwordInput") String passwordInput, HttpServletResponse response, ModelMap model) {
		
		if (emailInput.equals("") || passwordInput.equals("")) {
			model.addAttribute("error", "Some required fields are empty! Please fill in all fields!!!");
			return "admin/login";
		}else {
			String result = accountService.doAdminLogin(emailInput, passwordInput, response);
			if (result.equals("fail email")) {
				model.addAttribute("error", "Incorrect admin email !! This account does not exist!");
				return "admin/login";
			}else if(result.equals("fail password")) {
				model.addAttribute("error", "Incorrect admin password !!");
				return "admin/login";
			}else if(result.equals("fail admin")) {
				model.addAttribute("error", "You are not permitted to login as admin. Access Denied !!");
				return "admin/login";
			}else if(result.equals("success")) {
				return "redirect:dashboard";
			}else {
				model.addAttribute("error", "Invalid !!");
				return "admin/login";
			}
		}
	}
	
	@RequestMapping(value = "admin/logout", method = RequestMethod.POST)
	public String logoutAdmin(HttpServletRequest request, HttpServletResponse response) {
		accountService.logoutAdmin(request, response);
		return "redirect:/admin/login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String gotoUserLogin() {
		return "login";
	}
	
	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String userLogin(@RequestParam("emailInput") String emailInput, @RequestParam("passwordInput") String passwordInput, HttpServletResponse response, ModelMap model) {
		if (emailInput.equals("") || passwordInput.equals("")) {
			model.addAttribute("error", "Some required fields are empty! Please fill in all fields!!!");
			return "login";
		}else {
			String result = accountService.doUserLogin(emailInput, passwordInput, response);
			if (result.equals("fail email")) {
				model.addAttribute("error", "Incorrect admin email !! This account does not exist!");
				return "login";
			}else if(result.equals("fail password")) {
				model.addAttribute("error", "Incorrect admin password !!");
				return "login";
			}else if(result.equals("success")) {
				return "redirect:/";
			}else {
				model.addAttribute("error", "Invalid !!");
				return "login";
			}
		}
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.POST)
	public String userLogout(HttpServletRequest request, HttpServletResponse response) {
		accountService.logoutUser(request, response);
		return "redirect:/";
	}
	
	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String gotoUserRegister() {
		return "register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String userRegister(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("confirmpassword") String confirmpassword,
			@RequestParam("nrc") String nrc, @RequestParam("phone") String phone, @RequestParam("dob") String dob,
			@RequestParam("address") String address, ModelMap model) {
		if (name.equals("") || email.equals("") || password.equals("") || confirmpassword.equals("")) {
			model.addAttribute("error", "Requried fields are empty!!!");
			model.addAttribute("userslist", accountService.getAllUsers());
			return "register";
		} else {
			if (password.equals(confirmpassword)) {
				User user = new User();
				user.setName(name);
				user.setEmail(email);
				user.setPassword(Utility.encryptPasswordMD5(confirmpassword));
				user.setNrc(nrc);
				user.setPhone(phone);
				if (dob.equals("")) {
					dob = "";
				} else {
					try {
						Date dateobj = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
						user.setDob(dateobj);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				user.setAddress(address);
				user.setIsadmin(false);

				if (accountService.addAccount(user)) {
					model.addAttribute("success", "Successfully registered an account....");					
					return "register";
				} else {
					model.addAttribute("error", "Error in creating account!!!");
					return "register";
				}

			} else {
				model.addAttribute("error", "Password and Confirm Password must the the same!");
				return "register";
			}
		}
	}
	
}
