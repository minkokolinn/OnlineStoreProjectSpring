package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daos.ProductDAO;
import models.Product;

@Service("productService")
public class ProductService {

	@Autowired
	ProductDAO productDao;
	
	@Transactional
	public boolean addProduct(Product product) {
		return productDao.addProduct(product);
	}
	
	@Transactional
	public boolean deleteProduct(int id) {
		return productDao.deleteProduct(id);
	}
	
	@Transactional
	public List<Product> getAllProducts(){
		return productDao.getAllProducts();
	}
	
	@Transactional
	public Product getProductByID(int id) {
		return productDao.getProductByID(id);
	}
	
	@Transactional
	public boolean updateProduct(Product product) {
		return productDao.updateProduct(product);
	}
	
	@Transactional
	public List<Product> getRecommended3(){
		return productDao.getRecommended3();
	}
	
	@Transactional
	public boolean updateQuantityForOrder(int pid, int decreasedQty) {
		return productDao.updateQuantityForOrder(pid, decreasedQty);
	}
	
	@Transactional
	public List<Product> getProductsFilter(List<Integer> catIdArr){
		return productDao.getProductsFilter(catIdArr);
	}
}
