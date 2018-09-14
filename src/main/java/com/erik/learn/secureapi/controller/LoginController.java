package com.erik.learn.secureapi.controller;

import java.util.HashMap;
import java.util.Map;

import com.erik.learn.secureapi.helper.TokenGenerator;
import com.erik.learn.secureapi.model.User;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/login")
@AllArgsConstructor
public class LoginController {

	private final AuthenticationManager authenticationManager;

	@PostMapping("/authenticate")
	public ResponseEntity authenticate(@RequestBody User user) {

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		if (authentication.isAuthenticated()) {
			String token = TokenGenerator.generateToken(authentication.getPrincipal().toString());
			Map<String, String> map = new HashMap<>();
			map.put("token", token);
			return ResponseEntity.ok(map);
		}
		else {
			return new ResponseEntity(HttpStatus.BAD_REQUEST);
		}
	}

}
