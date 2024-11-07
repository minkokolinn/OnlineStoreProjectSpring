package daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import models.Category;

@Repository
public class CategoryDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public boolean addCategory(Category category) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(category);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public List<Category> getAllCategories() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Category> categoryList = session.createQuery("from Category").list();
		return categoryList;
	}
	
	public boolean deleteCategory(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Category category = (Category) session.load(Category.class, new Integer(id));
			if (category != null) {
				session.delete(category);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public Category getCategoryByID(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Category category = (Category) session.get(Category.class, new Integer(id));
//		System.out.println(category);
		return category;
	}

}
