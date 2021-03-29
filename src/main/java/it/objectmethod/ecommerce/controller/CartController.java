package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.Order;
import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repository.ItemRepository;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.UserRepository;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartRepository cartRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private ItemRepository itemRepo;

	@GetMapping("/additems")
	public ResponseEntity<Cart> addCartItem(@RequestParam("quantity") Integer quantity,
			@RequestParam("itemId") Integer itemId, @RequestParam("userId") Integer userId) {

		ResponseEntity<Cart> response = null;
		User user = userRepo.findById(userId).get();
		Optional<Item> it = itemRepo.findById(itemId);
		boolean newItem = true;

		if (it.isPresent()) {

			Item item = it.get();

			if (quantity <= item.getAvailability()) {

				Cart cart = null;
				cart = cartRepo.findByUserUserId(userId);

				if (cart == null) {
					cart = new Cart();
					cart.setUser(user);
					cart.setCartList(new ArrayList<CartDetail>());

					CartDetail detail = new CartDetail();
					detail.setItem(item);
					detail.setQuantity(quantity);
					List<CartDetail> list = cart.getCartList();
					list.add(detail);
					cart.setCartList(list);

				} else {

					for (CartDetail ele : cart.getCartList()) {

						if (ele.getItem().getItemId() == item.getItemId()) {
							ele.setQuantity(quantity + ele.getQuantity());
							newItem = false;
						}
					}
					if (newItem) {

						CartDetail detail = new CartDetail();
						detail.setItem(item);
						detail.setQuantity(quantity);
						List<CartDetail> list = cart.getCartList();
						list.add(detail);
						cart.setCartList(list);
					}
				}

				item.setAvailability(item.getAvailability() - quantity);
				item = itemRepo.save(item);
				cart = cartRepo.save(cart);
				
				response = new ResponseEntity<Cart>(cart, HttpStatus.OK);
			

			} else {
				response = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
				return response;
			}

		} else {

			response = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
			return response;
		}

		return response;
	}

}
