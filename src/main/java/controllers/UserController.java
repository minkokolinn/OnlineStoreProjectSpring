package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import models.User;
import services.AccountService;
import utility.Utility;

@Controller
public class UserController {

	@Autowired
	AccountService accountService;

	@RequestMapping(value = "admin/dashboard", method = RequestMethod.GET)
	public String adminDashboard() {
		return "admin/index";
	}

	@RequestMapping(value = "admin/account", method = RequestMethod.GET)
	public String adminAccount(Model model) {
		model.addAttribute("userslist", accountService.getAllUsers());
		return "admin/account";
	}

	@RequestMapping(value = "admin/accountdelete", method = RequestMethod.POST)
	public String adminAccountDelete(@RequestParam("useridtodel") int useridtodel, ModelMap model) {
		if (accountService.deleteAccount(useridtodel)) {
			model.addAttribute("success", "Deleted...");
			model.addAttribute("userslist", accountService.getAllUsers());
			return "admin/account";
		} else {
			model.addAttribute("error", "Failed to delete...");
			model.addAttribute("userslist", accountService.getAllUsers());
			return "admin/account";
		}
	}

	@RequestMapping(value = "admin/accountdelete", method = RequestMethod.GET)
	public String adminAccountDeleteGET(Model model) {
		return "redirect:account";
	}

	@RequestMapping(value = "admin/accountupdateform/{accidtoupd}", method = RequestMethod.GET)
	public String adminAccountUpdateForm(@PathVariable("accidtoupd") int accidtoupd, ModelMap model) {
		model.addAttribute("usertoupdate", accountService.getUser(accidtoupd));
		return "admin/accountupdateform";
	}
	
	@RequestMapping(value = "admin/accountupdate", method = RequestMethod.GET)
	public String adminAccountUpdateGET() {
		return "redirect:account";
	}

	@RequestMapping(value = "admin/accountupdate", method = RequestMethod.POST)
	public String adminAccountUpdate(@RequestParam("hiddenid") int hiddenid, @RequestParam("name") String name,
			@RequestParam("email") String email, @RequestParam("nrc") String nrc, @RequestParam("phone") String phone,
			@RequestParam("dob") String dob, @RequestParam("address") String address,
			@RequestParam(value = "isadmin", required = false) String isadmin, ModelMap model) {

		User user = accountService.getUser(hiddenid);
		user.setId(hiddenid);
		user.setName(name);
		user.setEmail(email);
		user.setNrc(nrc);
		user.setPhone(phone);
		if (dob.equals("")) {
			dob="";
		} else {
			try {
				Date dateobj = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
				user.setDob(dateobj);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		user.setAddress(address);
		if (isadmin==null) {
			user.setIsadmin(false);
		}else {
			user.setIsadmin(true);
		}		
		if (accountService.updateAccount(user)) {
			model.addAttribute("success","Updated Successfully");
			model.addAttribute("userslist", accountService.getAllUsers());
			return "admin/account";
		}else {
			model.addAttribute("error","Failed to delete");
			return "admin/accountupdateform";
		}
	}

	@RequestMapping(value = "admin/account", method = RequestMethod.POST)
	public String adminAccountAdd(@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("password") String password, @RequestParam("confirmpassword") String confirmpassword,
			@RequestParam("nrc") String nrc, @RequestParam("phone") String phone, @RequestParam("dob") String dob,
			@RequestParam("address") String address, @RequestParam(value = "isadmin", required = false) String isadmin,
			ModelMap model) {

		if (name.equals("") || email.equals("") || password.equals("") || confirmpassword.equals("")) {
			model.addAttribute("error", "Requried fields are empty!!!");
			model.addAttribute("userslist", accountService.getAllUsers());
			return "admin/account";
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
				if (isadmin == null) {
					user.setIsadmin(false);
				} else {
					user.setIsadmin(true);
				}

				if (accountService.addAccount(user)) {
					model.addAttribute("success", "Successfully registered an account....");
					model.addAttribute("userslist", accountService.getAllUsers());
					return "admin/account";
				} else {
					model.addAttribute("error", "Error in creating account!!!");
					model.addAttribute("userslist", accountService.getAllUsers());
					return "admin/account";
				}

			} else {
				model.addAttribute("error", "Password and Confirm Password must the the same!");
				model.addAttribute("userslist", accountService.getAllUsers());
				return "admin/account";
			}
		}
	}
}
