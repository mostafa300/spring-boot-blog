package com.posts.blog.springpostsblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.posts.blog.springpostsblog.model.Role;
import com.posts.blog.springpostsblog.model.User;
import com.posts.blog.springpostsblog.payload.LoginDto;
import com.posts.blog.springpostsblog.payload.SignUpDto;
import com.posts.blog.springpostsblog.repository.RoleRepository;

import com.posts.blog.springpostsblog.repository.UserRepository;

import java.util.Collections;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private RoleRepository roleRepository ;
	
	@Autowired
	private PasswordEncoder PasswordEncoder ; 
	
    // Login
	@PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return new ResponseEntity<>("User Login done",HttpStatus.OK);
    }
	
	// SingUp
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
		// check if this is user is exist or not 
		if(userRepository.existsByEmail(signUpDto.getEmail())) {
			return new ResponseEntity<>("This email already signup", HttpStatus.BAD_REQUEST);
		}
		if(userRepository.existsByEmail(signUpDto.getName())) {
			return new ResponseEntity<>("This name already signup", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User();
		user.setName(signUpDto.getName());
		user.setUsername(signUpDto.getUsername());
		user.setEmail(signUpDto.getEmail());
		user.setPassword(PasswordEncoder.encode(signUpDto.getPassword()));
		
		Role roles = roleRepository.findByName(signUpDto.getType()).get();
		user.setRoles(Collections.singleton(roles));
		
		userRepository.save(user);
		return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
	}
	
}
