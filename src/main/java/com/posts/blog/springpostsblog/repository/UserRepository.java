package com.posts.blog.springpostsblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.posts.blog.springpostsblog.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// Query by jpa 
	
	Optional<User> findByEmail(String email);
	Optional<User> findByUsernameOrEmail(String username, String email);
	Optional<User> findByUsername(String username);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	
}
