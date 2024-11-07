package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import models.CartRow;
import models.Order;
import models.OrderDetail;
import models.Product;
import services.AccountService;
import services.OrderDetailService;
import services.OrderService;
import services.ProductService;
import utility.Utility;

@Controller
public class OrderController {

	@Autowired
	ProductService productService;

	@Autowired
	AccountService accountService;

	@Autowired
	OrderService orderService;

	@Autowired
	OrderDetailService orderdetailService;

	@RequestMapping(value = "productdetail", method = RequestMethod.POST)
	public String addToCart(@RequestParam("pidtocart") int pidtocart, @RequestParam("cartQuantity") int cartQuantity,
			ModelMap modelMap, HttpServletRequest request) {

		if (Utility.checkUserAuth(request) == 0) {
			modelMap.addAttribute("error", "Please Login first to add this item!!!");
			Product p = productService.getProductByID(pidtocart);
			if (p == null) {
				return "redirect:/product";
			}
			modelMap.addAttribute("product", p);
			return "productdetail";
		} else {
			Product cartProduct = productService.getProductByID(pidtocart);
			int cartSubTotal = cartProduct.getPrice() * cartQuantity;
			CartRow cartRow = new CartRow();
			cartRow.setProduct(cartProduct);
			cartRow.setCart_quantity(cartQuantity);
			cartRow.setCart_subtotal(cartSubTotal);

			HttpSession session = request.getSession();

			if (session.getAttribute("cart") == null) {
				List<CartRow> cart = new ArrayList<CartRow>();
				cart.add(cartRow);
				session.setAttribute("cart", cart);
			} else {
				List<CartRow> cart = (List<CartRow>) session.getAttribute("cart");
				cart.add(cartRow);
				session.setAttribute("cart", cart);
			}

			return "redirect:/productdetail/" + pidtocart;
		}
	}

	@RequestMapping(value = "cart", method = RequestMethod.GET)
	public String gotoCart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (Utility.checkUserAuth(request) == 0) {
			return "redirect:/";
		} else if (session.getAttribute("cart") == null) {
			return "redirect:/product";
		} else {
			return "cart";
		}
	}

	@RequestMapping(value = "clearsessioncart", method = RequestMethod.GET)
	public String clearsessioncart(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/cart";
	}

	@RequestMapping(value = "checkout", method = RequestMethod.GET)
	public String gotoCheckout(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		if (request.getParameter("shippingMethod") == null || request.getParameter("hiddenTotal") == null
				|| request.getParameter("shippingMethod").equals("") || request.getParameter("hiddenTotal").equals("")
				|| session.getAttribute("cart") == null || Utility.checkUserAuth(request) == 0) {
			return "redirect:/cart";
		} else {
			model.addAttribute("loginuser", accountService.getUser(Utility.checkUserAuth(request)));
			return "checkout";
		}
	}

	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public String checkout(HttpServletRequest request, ModelMap modelMap) {

		String contactPhone = request.getParameter("contactPhone");
		String contactAddress = request.getParameter("contactAddress");
		String bankInfo = request.getParameter("bankInfo");
		String cardNo = request.getParameter("cardNo");
		int total = Integer.parseInt(request.getParameter("hiddenTotal"));
		String deliveryMethod = request.getParameter("shippingMethod");
		String paymentType = request.getParameter("paymentType");

		Order o = new Order();
		String generatedOrderNumber = Utility.generateOrderNumber();
		o.setOrdernumber(generatedOrderNumber);
		Date dateobj = null;
		try {
			dateobj = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		o.setOrderdate(dateobj);
		o.setContactphone(contactPhone);
		o.setContactaddress(contactAddress);
		o.setTotal(total);
		o.setMerchant(bankInfo);
		o.setCardno(cardNo);
		o.setDeliverytype(deliveryMethod);
		o.setIsdelivered(false);
		o.setPaymenttype(paymentType);
		o.setUser(accountService.getUser(Utility.checkUserAuth(request)));

		int createdId = orderService.addOrder(o);
		if (createdId == 0) {
			modelMap.addAttribute("error", "Failed in order stage");
			System.out.println("Failed in order stage");
			return "redirect:/product";
		} else {
			HttpSession session = request.getSession();
			List<CartRow> cartRows = (List<CartRow>) session.getAttribute("cart");
			for (CartRow row : cartRows) {
				OrderDetail od = new OrderDetail();
				od.setQuantity(row.getCart_quantity());
				od.setPrice(row.getCart_subtotal());
				od.setOrder(orderService.getOrderByID(createdId));
				od.setProduct(row.getProduct());
				if (orderdetailService.addOrderDetail(od)) {
					System.out.println("success");
				} else {
					System.out.println("fail");
				}
			}
			HttpSession session1 = request.getSession();
			session1.invalidate();
			modelMap.addAttribute("success", "Ordered successfully... Your order number is " + generatedOrderNumber);
			System.out.println("Order Success");
			return "success";
		}

	}

	@RequestMapping(value = "admin/order", method = RequestMethod.GET)
	public String gotoAdminOrderList(Model model) {
		model.addAttribute("orderlist", orderService.getAllOrder());
		return "admin/order";
	}

	@RequestMapping(value = "admin/ordermakedeliver/{orderid}")
	public String makeDeliver(@PathVariable("orderid") int orderid, ModelMap modelMap) {
		if (orderService.getOrderByID(orderid)==null) {
			return "redirect:/admin/order";
		}
		if (orderService.makeDeliver(orderid)) {
			Set<OrderDetail> orderDetails = orderService.getOrderByID(orderid).getOrderDetails();
			for (OrderDetail od : orderDetails) {
				if (productService.updateQuantityForOrder(od.getProduct().getId(), od.getQuantity())) {
					System.out.println("Updated quantity");
				} else {
					System.out.println("Failed to update product quantity");
				}
			}
			System.out.println("success");
			return "redirect:/admin/order";
		} else {
			System.out.println("deliver failed");
			return "redirect:/admin/order";
		}
	}
	
	@RequestMapping(value = "admin/orderdelete", method = RequestMethod.GET)
	public String deleteOrder() {
		return "redirect:/admin/order";
	}
	
	@RequestMapping(value = "admin/orderdelete", method = RequestMethod.POST)
	public String deleteOrder(@RequestParam("oidtodel") int oidtodel, ModelMap modelMap) {
		if (orderService.deleteOrder(oidtodel)) {
			modelMap.addAttribute("success","Order Deleted...");
			modelMap.addAttribute("orderlist", orderService.getAllOrder());
			return "admin/order";
		}else {
			modelMap.addAttribute("error","Delete Failed!!!");
			modelMap.addAttribute("orderlist", orderService.getAllOrder());
			return "admin/order";
		}
	}
	
	@RequestMapping(value = "admin/orderdetail/{orderid}", method = RequestMethod.GET)
	public String adminOrderDetail(@PathVariable("orderid") int orderid, Model model) {
		Order order = orderService.getOrderByID(orderid);
		if (order==null) {
			return "redirect:/admin/order";
		}else {
			model.addAttribute("order",order);
			return "admin/orderdetail";
		}
	}

}
