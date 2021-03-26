package it.objectmethod.ecommerce.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.entity.Cart;
import it.objectmethod.ecommerce.entity.CartDetail;
import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repository.ItemRepository;
import it.objectmethod.ecommerce.repository.CartRepository;
import it.objectmethod.ecommerce.repository.UserRepository;

@RestController
//@RequestMapping("/api/padre")
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

		// vedere se esiste il prodotto
		if (it.isPresent()) {

			Item item = it.get();

			// se la quantità è minore o uguale alla disponibilità
			if (quantity <= item.getAvailability()) {

				Cart cart = null;
				cart = cartRepo.findByUserUserId(userId);

				// se non esiste il carrello
				if (cart == null) {
					cart = new Cart();
					cart.setUser(user);
					cart.setCartList(new ArrayList<CartDetail>());
				}

				CartDetail detail = new CartDetail();
				detail.setItem(item);

				// se il carrello non è nuovo
				if (cart.getCartList().size() > 0) {
					// ciclo la lista per vedere se l'item c'è già
					for (int i = 0; i < cart.getCartList().size(); i++) {

						if (item == cart.getCartList().get(i).getItem()) {
							cart.getCartList().get(i).setQuantity(quantity + cart.getCartList().get(i).getQuantity());

						} else { // se l'item non è presente

							detail.setQuantity(quantity);
							List<CartDetail> list = cart.getCartList();
							list.add(detail);
							cart.setCartList(list);
						}

						item.setAvailability(item.getAvailability() - quantity);
						item = itemRepo.save(item);
						cart = cartRepo.save(cart);
					}
				// se il carrello è nuovo
				} else {

					detail.setQuantity(quantity);
					item.setAvailability(item.getAvailability() - quantity);
					item = itemRepo.save(item);
					List<CartDetail> list = cart.getCartList();

					list.add(detail);
					cart.setCartList(list);
					cart = cartRepo.save(cart);
				}

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
