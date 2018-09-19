package com.erik.learn.secureapi.helper;


import java.util.Collections;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.joda.time.LocalDateTime;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class TokenHelper {

	public static final String TOKEN_HEADER_NAME = "x-auth-token";
	public static String generateToken(String username, String role) {

		Algorithm algorithm = Algorithm.HMAC256("someseriouslylongpasswordthatyoucanneverguest");
		return JWT.create()
				.withIssuer("auth0")
				.withExpiresAt(LocalDateTime.now().plusHours(6).toDate())
				.withClaim("username", username)
				.withClaim("role", role)
				.sign(algorithm);
	}

	public static User decodeToken(String token) {

		DecodedJWT decode = JWT.decode(token);
		return new User(
				decode.getClaim("username").asString(),
				"",
				true,
				true,
				true,
				true,
				Collections.singleton(new SimpleGrantedAuthority(decode.getClaim("username").asString())));
	}
}
