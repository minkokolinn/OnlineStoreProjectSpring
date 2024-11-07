package daos;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import models.Order;

@Repository
public class OrderDAO {

	@Autowired
	SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public int addOrder(Order order) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			session.persist(order);
			return order.getId();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	public List<Order> getAllOrder() {
		Session session = this.sessionFactory.getCurrentSession();
		List<Order> orderList = session.createQuery("from Order").list();
		return orderList;
	}
	
	
	public Order getOrderByID(int id) {
		Session session = this.sessionFactory.getCurrentSession();
		Order user = (Order) session.get(Order.class, new Integer(id));
		return user;
	}
	
	public boolean makeDeliver(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Order order = (Order) session.load(Order.class, new Integer(id));
			order.setIsdelivered(true);
			session.update(order);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean deleteOrder(int id) {
		try {
			Session session = this.sessionFactory.getCurrentSession();
			Order order = (Order) session.load(Order.class, new Integer(id));
			if (order != null) {
				session.delete(order);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
}
