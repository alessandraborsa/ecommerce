package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import it.objectmethod.ecommerce.entity.OrderRow;

public interface OrderRowRepository extends JpaRepository<OrderRow, Integer>{

}
