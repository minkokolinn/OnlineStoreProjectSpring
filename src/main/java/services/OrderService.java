package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daos.OrderDAO;
import models.Order;

@Service("orderService")
public class OrderService {

	@Autowired
	OrderDAO orderDao;
	
	@Transactional
	public int addOrder(Order order) {
		return orderDao.addOrder(order);
	}
	
	@Transactional
	public List<Order> getAllOrder() {
		return orderDao.getAllOrder();
	}
	
	@Transactional
	public Order getOrderByID(int id) {
		return orderDao.getOrderByID(id);
	}
	
	@Transactional
	public boolean makeDeliver(int id) {
		return orderDao.makeDeliver(id);
	}
	
	@Transactional
	public boolean deleteOrder(int id) {
		return orderDao.deleteOrder(id);
	}
}
