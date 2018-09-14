package com.erik.learn.secureapi.helper;

import java.nio.charset.StandardCharsets;

import org.springframework.util.DigestUtils;

public class TokenGenerator {

	public static String generateToken(String principal) {

		return new String(DigestUtils.md5Digest(principal.getBytes()), StandardCharsets.UTF_8);
	}
}
