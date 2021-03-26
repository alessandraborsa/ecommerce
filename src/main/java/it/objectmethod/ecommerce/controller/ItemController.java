package it.objectmethod.ecommerce.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import it.objectmethod.ecommerce.entity.Item;
import it.objectmethod.ecommerce.repository.ItemRepository;

@RestController
//@RequestMapping("/api/")

public class ItemController {

	@Autowired
	private ItemRepository itemRepo;

	@GetMapping("/articoli")
	public List<Item> showItems() {

		List<Item> items = itemRepo.showAllItems();

		return items;
	}

	@GetMapping("/find")
	public List<Item> findItemByNameOrCode(@RequestParam("itemName") String itemName,
			@RequestParam("itemCode") String itemCode) {

		List<Item> item = itemRepo.findItems(itemName, itemCode);

		return item;
	}
}
