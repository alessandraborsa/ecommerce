package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import it.objectmethod.ecommerce.entity.CartDetail;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer>{

}
