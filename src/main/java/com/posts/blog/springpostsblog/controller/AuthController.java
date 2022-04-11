package com.posts.blog.springpostsblog.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.posts.blog.springpostsblog.model.Role;
import com.posts.blog.springpostsblog.model.User;
import com.posts.blog.springpostsblog.payload.JWTAuthResponse;
import com.posts.blog.springpostsblog.payload.LoginDto;
import com.posts.blog.springpostsblog.payload.PostDto;
import com.posts.blog.springpostsblog.payload.SignUpDto;
import com.posts.blog.springpostsblog.repository.RoleRepository;

import com.posts.blog.springpostsblog.repository.UserRepository;
import com.posts.blog.springpostsblog.security.JwtTokenProvider;

import java.util.Collections;
import java.util.List;

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
	
	@Autowired
    private JwtTokenProvider tokenProvider;
	
    // Login
	@PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
        //return new ResponseEntity<>("User Login done",HttpStatus.OK);
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
	
	@GetMapping("/gethello")
	public String getHello() {
		return "Hello Mostafa" ;
	}
 
	@GetMapping("/consume")
	public String consume(){
		String url = "http://localhost:8080/api/auth/gethello";
		RestTemplate restTemplate = new RestTemplate();
		String result = restTemplate.getForObject(url, String.class);
		return result ;
	}
	
}
