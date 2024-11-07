package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import daos.OrderDetailDAO;
import models.OrderDetail;

@Service("orderdetailService")
public class OrderDetailService {
	
	@Autowired
	OrderDetailDAO orderdetailDao;
	
	@Transactional
	public boolean addOrderDetail(OrderDetail od) {
		return orderdetailDao.addOrderDetail(od);
	}
	
}
