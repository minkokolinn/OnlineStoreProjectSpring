package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import models.Category;
import services.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	
	@RequestMapping(value = "admin/category", method = RequestMethod.GET)
	public String gotoCategory(Model model) {
		model.addAttribute("categorieslist", categoryService.getAllCategories());
		return "admin/category";
	}
	
	@RequestMapping(value = "admin/category", method = RequestMethod.POST)
	public String addCategory(@ModelAttribute("category") Category category, ModelMap model) {
		if (categoryService.addCategory(category)) {
			model.addAttribute("success", "Category Added...");
		}else {
			model.addAttribute("error","Error in creating category");
		}
		model.addAttribute("categorieslist", categoryService.getAllCategories());
		return "admin/category";
	}
	
	@RequestMapping(value = "admin/categorydelete", method = RequestMethod.POST)
	public String deleteCategoryAction(@RequestParam("catidtodel") int catidtodel, ModelMap model) {
		if (categoryService.deleteCategory(catidtodel)) {
			model.addAttribute("success","Deleted Category...");
		}else {
			model.addAttribute("error", "Error in deleting category");
		}
		model.addAttribute("categorieslist", categoryService.getAllCategories());
		return "admin/category";
	}
	
	@RequestMapping(value = "admin/categorydelete", method = RequestMethod.GET)
	public String deleteCategoryActionGET() {
		return "redirect:category";
	}
}

