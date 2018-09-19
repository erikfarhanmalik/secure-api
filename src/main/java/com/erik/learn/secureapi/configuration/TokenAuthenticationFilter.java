package com.erik.learn.secureapi.configuration;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.erik.learn.secureapi.helper.TokenHelper;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.GenericFilterBean;

import static com.erik.learn.secureapi.helper.TokenHelper.TOKEN_HEADER_NAME;

public class TokenAuthenticationFilter extends GenericFilterBean {

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String accessToken = httpRequest.getHeader(TOKEN_HEADER_NAME);
		if (accessToken != null) {
			User user = TokenHelper.decodeToken(accessToken);
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}
