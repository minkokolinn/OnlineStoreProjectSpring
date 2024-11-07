package daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import models.Category;
import models.Product;
import services.CategoryService;

@Repository
public class ProductDAO {
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	CategoryService categoryService;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public boolean addProduct(Product product) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteProduct(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Product product = (Product) session.load(Product.class, new Integer(id));
			if (product != null) {
				session.delete(product);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public List<Product> getAllProducts() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Product> productList = session.createQuery("from Product").list();
		return productList;
	}

	public Product getProductByID(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Product product = (Product) session.get(Product.class, new Integer(id));
//			System.out.println(category);
		return product;
	}
	
	public boolean updateProduct(Product product) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.update(product);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Product> getRecommended3(){
		Session session = this.sessionFactory.getCurrentSession();		
		List<Product> list = session.createQuery("select p from Product p order by p.likecount desc").setMaxResults(3).list();
		return list;
	}
	
	public boolean updateQuantityForOrder(int pid, int decreasedQty) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Product updProduct = (Product)session.load(Product.class, new Integer(pid));
			updProduct.setQuantity(updProduct.getQuantity()-decreasedQty);
			session.update(updProduct);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Product> getProductsFilter(List<Integer> catIdArr){
		Session session = this.sessionFactory.getCurrentSession();
		List<Product> products = new ArrayList<>();
		
		for(int id:catIdArr) {
			Category cat = categoryService.getCategoryByID(id);
			Set<Product> eachProducts = cat.getProducts();
			for(Product p:eachProducts) {
				products.add(p);
			}
		}
		return products;
	}
	
}
