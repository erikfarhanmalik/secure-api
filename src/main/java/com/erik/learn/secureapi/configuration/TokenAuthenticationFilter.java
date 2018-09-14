package com.erik.learn.secureapi.configuration;

import java.io.IOException;
import java.util.Collections;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

public class TokenAuthenticationFilter extends GenericFilterBean {

	private final String TOKEN_HEADER_NAME = "x-auth-token";

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String accessToken = httpRequest.getHeader(TOKEN_HEADER_NAME);
		if (accessToken != null) {
			//get and check whether token is valid ( from DB or file wherever you are storing the token)
			//Populate SecurityContextHolder by fetching relevant information using token
			User user;
			if ("tokenadmin".equals(accessToken)) {
				user = new User(
						"admin",
						"admin",
						true,
						true,
						true,
						true,
						Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
			}
			else {
				user = new User(
						"user",
						"user",
						true,
						true,
						true,
						true,
						Collections.singleton(new SimpleGrantedAuthority("USER")));
			}
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
