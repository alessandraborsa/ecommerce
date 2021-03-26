package it.objectmethod.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

	@Query(value = "select i from Item i")
	public List<Item> showAllItems();
	
	@Query(value = "select i from Item i where (''=?1 or i.itemName=?1) and (''=?2 or i.itemCode=?2)")
	public List<Item> findItems(String name, String code);
}
