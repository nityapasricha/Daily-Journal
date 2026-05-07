package org.techm.samples.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.techm.samples.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsername(String username);

	public boolean existsByEmail(String email);
	public boolean existsByUsername(String username);
	
	List<User> findByRole(String role);

}
