package it.objectmethod.ecommerce.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.Order;
import it.objectmethod.ecommerce.entity.OrderRow;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.OrderRepository;
import it.objectmethod.ecommerce.repository.OrderRowRepository;

@RestController
@RequestMapping("/api")
public class OrderController {
	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private OrderRowRepository rowRepo;

	@GetMapping("/neworder")
	public ResponseEntity<Order> insertOrder(@RequestParam("userId") Integer userId) throws ParseException {

		ResponseEntity<Order> response = null;
		Cart cart = cartRepo.findByUserUserId(userId);
		Order order = new Order();

		// creazione numero ordine
		String code = "";
		String cartId = Integer.toString(cart.getCartId());
		if (cartId.length() == 3) {
			code = "A000" + cartId;
		} else if (cartId.length() == 4) {
			code = "A00" + cartId;
		}

		// creazione data
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String date = format.format(new Date());

		order.setOrderNum(code);
		order.setUser(cart.getUser());
		order.setOrderDate(date);

		List<CartDetail> cartList = cart.getCartList();
		List<OrderRow> rowList = new ArrayList<OrderRow>();
		order = orderRepo.save(order);

		for (CartDetail ele : cartList) {
			OrderRow row = new OrderRow();
			row.setItem(ele.getItem());
			row.setQuantity(ele.getQuantity());
			row.setOrder(order);
			rowList.add(row);
			row = rowRepo.save(row);
		}

		order.setOrderRow(rowList);
		cartRepo.delete(cart);

		return response;
	}
}
