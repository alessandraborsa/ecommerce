package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import it.objectmethod.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository <Order, Integer> {

}
