package com.posts.blog.springpostsblog.payload;

import lombok.Data;

@Data
public class SignUpDto {
	private String name;
    private String username;
    private String email;
    private String type ; 
    private String password;
    
    public String getName() {
		return this.name ;
	}
    public String getUsername() {
    	return this.username ;
    }
    public String getEmail() {
    	return this.email ; 
    }
    public String getType() {
    	return this.type;
    }
    public String getPassword() {
    	return this.password;
    }
}

