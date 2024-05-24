package com.kosta.sec.config.jwt;

import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtToken {
	public String makeAccessToken(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료시간
//				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료시간
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 알고리즘 선택 후 서명
	}
	
	public String makeRefreshToken(String username) {
		return JWT.create()
				.withSubject(username)
				.withIssuedAt(new Date(System.currentTimeMillis()))
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.REFRESH_EXPIRATION_TIME)) // 만료시간
				.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 알고리즘 선택 후 서명
	}

}
