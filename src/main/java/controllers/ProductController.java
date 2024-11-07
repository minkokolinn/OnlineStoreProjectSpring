package controllers;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.servlet.jsp.PageContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import models.Category;
import models.Product;
import services.CategoryService;
import services.ProductService;

@Controller
@MultipartConfig
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	ServletContext context;

	@RequestMapping(value = "admin/product", method = RequestMethod.GET)
	public String gotoAdminProduct(Model model) {
		model.addAttribute("productlist", productService.getAllProducts());
		return "admin/product";
	}
	
	@RequestMapping(value = "product", method = RequestMethod.GET)
	public String gotoUserProduct(Model model, HttpServletRequest request) {
		if (request.getParameter("selectedCat") != null) {
			String[] catArr = request.getParameterValues("selectedCat");
			List<Integer> newArr = new ArrayList<>();
			for (String cat : catArr) {
				int id = Integer.parseInt(cat);
				newArr.add(id);
			}

			model.addAttribute("productlist", productService.getProductsFilter(newArr));
		} else {
		model.addAttribute("productlist", productService.getAllProducts());
		}
		return "product";
	}
	
	@RequestMapping(value = "productdetail/{piddetail}", method = RequestMethod.GET)
	public String gotoUserProductDetail(@PathVariable("piddetail") int piddetail, Model model) {
		Product p = productService.getProductByID(piddetail);
		if (p==null) {
			return "redirect:/product";
		}
		model.addAttribute("product", p);
		return "productdetail";
	}

	@RequestMapping(value = "admin/productadd", method = RequestMethod.GET)
	public String gotoAdminProductAdd(Model model) {
		model.addAttribute("categorieslist", categoryService.getAllCategories());
		return "admin/productadd";
	}

	@RequestMapping(value = "admin/productadd", method = RequestMethod.POST)
	public String adminProductAdd(@RequestParam("name") String name, @RequestParam("brand") String brand,
			@RequestParam("model") String model, @RequestParam("description") String description,
			@RequestParam("price") int price, @RequestParam("quantity") int quantity,
			@RequestParam("image") CommonsMultipartFile image, @RequestParam("category") int category,
			HttpServletRequest request, ModelMap modelmap) throws IOException, ServletException {
		Category selectedCategory = categoryService.getCategoryByID(category);

		String realfilepath = "/media/msi/Secondary/JavaEE/Workspace/ServletJSPWorkspaceTom9/SpringOnlineStore/src/main/webapp/resources/productImg/"
				+ image.getOriginalFilename();
		String filetype = image.getContentType();
		String fileName = image.getOriginalFilename();
		System.out.println(fileName);
		System.out.println(filetype);
		if (name.equals("") || brand.equals("") || model.equals("") || description.equals("") || price == 0
				|| quantity == 0 || selectedCategory == null) {
			modelmap.addAttribute("error", "Required fields are missing!!!");
			return "admin/productadd";
		} else {
			if (fileName.equals("")) {
				Product product = new Product();
				product.setName(name);
				product.setBrand(brand);
				product.setModel(model);
				product.setDescription(description);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(selectedCategory);
				product.setImage(fileName);

				if (productService.addProduct(product)) {
					modelmap.addAttribute("success", "Successfully inserted a product...");
					modelmap.addAttribute("categorieslist", categoryService.getAllCategories());
					return "admin/productadd";
				} else {
					modelmap.addAttribute("error", "Failed in inserting product!!!");
					modelmap.addAttribute("categorieslist", categoryService.getAllCategories());
					return "admin/productadd";
				}
			} else {
				if (filetype.equals("image/jpeg") || filetype.equals("image/jpg") || filetype.equals("image/png")
						|| filetype.equals("image/webp") || filetype.equals("image/gif")) {

					Product product = new Product();
					product.setName(name);
					product.setBrand(brand);
					product.setModel(model);
					product.setDescription(description);
					product.setPrice(price);
					product.setQuantity(quantity);
					product.setCategory(selectedCategory);
					product.setImage(fileName);

					if (productService.addProduct(product)) {
						byte barr[] = image.getBytes();

						BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(realfilepath));
						bout.write(barr);
						bout.flush();
						bout.close();
						modelmap.addAttribute("success", "Successfully inserted a product...");
						modelmap.addAttribute("categorieslist", categoryService.getAllCategories());
						return "admin/productadd";
					} else {
						modelmap.addAttribute("error", "Failed in inserting product!!!");
						modelmap.addAttribute("categorieslist", categoryService.getAllCategories());
						return "admin/productadd";
					}
				} else {
					modelmap.addAttribute("error", "Invalid File Type");
					modelmap.addAttribute("categorieslist", categoryService.getAllCategories());
					return "admin/productadd";
				}
			}
		}
	}

	@RequestMapping(value = "admin/productupdate/{pidtoupd}", method = RequestMethod.GET)
	public String gotoProductUpdate(@PathVariable("pidtoupd") int pidtoupd, Model model) {
		model.addAttribute("selectedproduct", productService.getProductByID(pidtoupd));
		return "admin/productupdate";
	}

	@RequestMapping(value = "admin/productupdate", method = RequestMethod.POST)
	public String updateProduct(@RequestParam("hiddenpid") int hiddenpid, @RequestParam("name") String name,
			@RequestParam("brand") String brand, @RequestParam("model") String model,
			@RequestParam("description") String description, @RequestParam("price") int price,
			@RequestParam("quantity") int quantity, ModelMap modelMap) {

		
		Product updatedProduct = productService.getProductByID(hiddenpid);
		updatedProduct.setId(hiddenpid);
		updatedProduct.setName(name);
		updatedProduct.setBrand(brand);
		updatedProduct.setModel(model);
		updatedProduct.setDescription(description);
		updatedProduct.setPrice(price);
		updatedProduct.setQuantity(quantity);
		
		if (productService.updateProduct(updatedProduct)) {
			return "redirect:/admin/product";
		}else {
			modelMap.addAttribute("error","Update Product Failed!!!");
			return "admin/productupdate/"+hiddenpid;
		}
	}
	
	@RequestMapping(value = "admin/productdelete", method = RequestMethod.POST)
	public String deleteProduct(@RequestParam("pidtodel") int pidtodel, ModelMap modelMap) {
		if (productService.deleteProduct(pidtodel)) {
			modelMap.addAttribute("success","Product Deleted Successfully");
			modelMap.addAttribute("productlist", productService.getAllProducts());
			return "admin/product";
		}else {
			modelMap.addAttribute("error","Failed in product delete...");
			modelMap.addAttribute("productlist", productService.getAllProducts());
			return "admin/product";
		}
	}
	
	@RequestMapping(value = "admin/productdelete", method = RequestMethod.GET)
	public String deleteProductGET() {
		return "redirect:/admin/product";
	}

}
