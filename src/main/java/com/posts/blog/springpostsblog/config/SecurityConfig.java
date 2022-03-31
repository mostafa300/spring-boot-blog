package com.posts.blog.springpostsblog.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.posts.blog.springpostsblog.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
	
	@Autowired
    private CustomUserDetailsService userDetailsService;
	
	@Override
	//.antMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.authorizeRequests()
			// exept (will be login & registeration)
			//.antMatchers(HttpMethod.GET, "/api/**").permitAll()
			//.antMatchers(HttpMethod.GET, "/api/**").permitAll()
            .antMatchers("/api/auth/signin").permitAll()
            .antMatchers("/api/auth/signup").permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.httpBasic();
		
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
//  @Override
//  @Bean
//  protected UserDetailsService userDetailsService() {
//      UserDetails mostafa = User.builder().username("mostafa").password(passwordEncoder()
//              .encode("password")).roles("USER").build();
//      UserDetails admin = User.builder().username("admin").password(passwordEncoder()
//              .encode("admin")).roles("ADMIN").build();
//      return new InMemoryUserDetailsManager(mostafa, admin);
//  }
}
