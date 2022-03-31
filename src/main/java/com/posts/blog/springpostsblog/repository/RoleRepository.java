package com.posts.blog.springpostsblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.posts.blog.springpostsblog.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(String name);
}
