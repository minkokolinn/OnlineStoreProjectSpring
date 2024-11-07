package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CategoryService;
import services.ProductService;

@Controller
public class UtilityController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPortal() {
		return "admin/login";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		System.out.println("home started");
		model.addAttribute("catlist",categoryService.getAllCategories());
		model.addAttribute("prolist",productService.getRecommended3());
		return "index";
	}
	
	
}
