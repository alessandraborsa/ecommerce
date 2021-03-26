package it.objectmethod.ecommerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.ecommerce.entity.User;
import it.objectmethod.ecommerce.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepo;

	@PostMapping("/userlogin")
	public User UserLogin(@RequestParam("userName") String userName,
			@RequestParam("password") String password) {

		User userLogged = null;
		userLogged = userRepo.findByUserAndPassword(userName, password);
		
		return userLogged;
	}
}
