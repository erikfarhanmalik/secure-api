package com.erik.learn.secureapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.inMemoryAuthentication()
				.passwordEncoder(passwordEncoder())
				.withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN")
				.and()
				.withUser("user").password(passwordEncoder().encode("user")).roles("USER");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		TokenAuthenticationFilter tokenFilter = new TokenAuthenticationFilter();
		http.addFilterBefore(tokenFilter, BasicAuthenticationFilter.class);
		http.authorizeRequests()
				.antMatchers(POST, "/api/authenticate").permitAll()
				.antMatchers(POST, "/api/person").permitAll()
				.anyRequest().authenticated()
				.and().csrf().disable();
	}

	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {

		return this.authenticationManager();
	}

	private PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}
}
