package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daos.CategoryDAO;
import models.Category;

@Service("categoryService")
public class CategoryService {
	
	@Autowired
	CategoryDAO categoryDao;
	
	@Transactional
	public boolean addCategory(Category category) {
		return categoryDao.addCategory(category);
	}
	
	@Transactional
	public List<Category> getAllCategories() {
		return categoryDao.getAllCategories();
	}
	
	@Transactional
	public boolean deleteCategory(int id) {
		return categoryDao.deleteCategory(id);
	}
	
	@Transactional
	public Category getCategoryByID(int id) {
		return categoryDao.getCategoryByID(id);
	}
}
