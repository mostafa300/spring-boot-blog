package com.posts.blog.springpostsblog.payload;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDto {

	private String usernameOrEmail;
    private String password;
    
    public String getUsernameOrEmail() {
    	return this.usernameOrEmail;
    }
    public String getPassword () {
    	return this.password ;
    }
}
