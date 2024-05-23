package com.kosta.sec.config.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

// 인가 : 로그인 처리가 되어어ㅑ만 하는 요청이 들어왔을 때 실행
public class JwtAuthrizationFilter extends BasicAuthenticationFilter {
	public JwtAuthrizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();
		
		// 로그인(인증)이 필요없는 요청은 그대로 진행
		if(!( uri.contains("/user/") || uri.contains("/admin/") || uri.contains("/manager/") ) ) {
			chain.doFilter(request, response);
			return;
		}
		
		// 토큰이 없거나 Bearer가 아니거나 
		String authentication = request.getHeader(JwtProperties.HEADER_STRING);
		if(authentication==null || authentication.startsWith(JwtProperties.TOKEN_PREFIX)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
			return;
		}
		
		// Bearer 제거 
		String token = authentication.replace(JwtProperties.TOKEN_PREFIX, "");
		
		try {
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
							 .build()
							 .verify(token)
							 .getClaim("subject")
							 .asString();
			System.out.println(username);
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 에러");
		}
	}
}
