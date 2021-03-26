package it.objectmethod.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.objectmethod.ecommerce.entity.User;

@Repository

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query(value = "select u from User u where u.userName = ?1 and u.password = ?2")
	public User findByUserAndPassword(String name, String pass);
}
