package com.kosta.sec.config.jwt;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.kosta.sec.config.auth.PrincipalDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFIlter extends UsernamePasswordAuthenticationFilter { // 로그인 시 실행함
	
	public JwtAuthenticationFIlter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		PrincipalDetails principalDetails = (PrincipalDetails)authResult.getPrincipal();
		String accessToken = JWT.create()
								.withSubject(principalDetails.getUsername())
								.withIssuedAt(new Date(System.currentTimeMillis()))
								.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.ACCESS_EXPIRATION_TIME)) // 만료시간
								.withClaim("id", principalDetails.getUser().getId()) // 가변적
								.sign(Algorithm.HMAC512(JwtProperties.SECRET)); // 알고리즘 선택 후 서명

		System.out.println(accessToken);
		response.addHeader(JwtProperties.HEADER_STRING, JwtProperties.TOKEN_PREFIX+accessToken);
		response.setContentType("application/json; charset=utf-8");
	}
}
