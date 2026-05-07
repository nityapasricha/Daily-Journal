package org.techm.samples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.techm.samples.entity.User;
import org.techm.samples.exception.UserNotFoundException;
import org.techm.samples.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repo;

//	Creating a new user

	public User create(User user) {
		return repo.save(user);
	}

//	fetching the user data by username for login
	public User getUserByUsername(String username) {
	    return repo.findByUsername(username)
	        .orElseThrow(() -> new UserNotFoundException("User with username '" + username + "' not found"));
	}


	// checking if email exist or not
	public boolean checkEmail(String email) {
		return repo.existsByEmail(email);
	}

	// delete user
	public void delete(User user) {
		repo.delete(user);
	}
	
	//checking if username exist
	
	public boolean checkUsername(String username) {
		return repo.existsByUsername(username);
	}

}
