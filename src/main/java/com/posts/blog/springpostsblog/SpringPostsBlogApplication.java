package com.posts.blog.springpostsblog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringPostsBlogApplication {
	
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(SpringPostsBlogApplication.class, args);
		//PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        //System.out.println(passwordEncoder.encode("mostafa"));
	}

}
