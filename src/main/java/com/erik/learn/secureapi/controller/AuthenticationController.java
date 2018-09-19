package com.erik.learn.secureapi.controller;

import java.util.HashMap;
import java.util.Map;

import com.erik.learn.secureapi.helper.TokenHelper;
import com.erik.learn.secureapi.model.User;
import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authenticate")
@AllArgsConstructor
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	@PostMapping
	public ResponseEntity authenticate(@RequestBody User user) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		org.springframework.security.core.userdetails.User authenticatedUser = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		String token = TokenHelper.generateToken(authenticatedUser.getUsername(), authenticatedUser.getAuthorities().iterator().next().getAuthority());
		Map<String, String> map = new HashMap<>();
		map.put("token", token);
		return ResponseEntity.ok(map);
	}

}
