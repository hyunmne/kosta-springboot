package com.kosta.sec.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

// 인가 : 로그인 처리가 되어어ㅑ만 하는 요청이 들어왔을 때 실행
public class JwtAuthrizationFilter extends BasicAuthenticationFilter {
	@Autowired
	private UserRepository useres;
	
	private JwtToken jwtToken = new JwtToken();

	public JwtAuthrizationFilter(AuthenticationManager authenticationManager, UserRepository useres) {
		super(authenticationManager);
		this.useres = useres;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String uri = request.getRequestURI();

		// 로그인(인증)이 필요없는 요청은 그대로 진행
		if(!( uri.contains("/user") || uri.contains("/admin") || uri.contains("/manager") ) ) {
			chain.doFilter(request, response);
			return;
		}

		String authentication = request.getHeader(JwtProperties.HEADER_STRING);
		if(authentication==null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
			return;
		}

		ObjectMapper objmapper = new ObjectMapper();
		Map<String, String> token = objmapper.readValue(authentication, Map.class);
		System.out.println(token);

		String access_token = token.get("access_token");
		if(!access_token.startsWith(JwtProperties.TOKEN_PREFIX)) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
			return;
		}

		access_token = access_token.replace(JwtProperties.TOKEN_PREFIX, "");
		try { // access_token 타당성 체크
			String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
					.build()
					.verify(access_token)
					.getClaim("sub")
					.asString();
			System.out.println("token"+username);
			if(username==null || username.equals("")) throw new Exception(); // 사용자가 없을 때 
			
			// Database에서 사용자를 검색하여 UserDetails 타입으로 변환, 
			// 다시 Authentication 타입으로 변환하여 security sessio(SecurityContextHolder)에 넣는다.
			User user = useres.findByUsername(username);
			if(user == null) throw new Exception(); // 사용자가 없을 때 
			
			PrincipalDetails principalDetails = new PrincipalDetails(user);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
					(principalDetails, null, principalDetails.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
			chain.doFilter(request, response);
			return;
		} catch(JWTVerificationException ve) {
			ve.printStackTrace();
			try {
				String refresh_token = token.get("refresh_token");
				if(!refresh_token.startsWith(JwtProperties.TOKEN_PREFIX)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
					return;
				}
//				refresh_token = refresh_token.replace(JwtProperties.TOKEN_PREFIX,"");
				
				String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
									 .build()
									 .verify(refresh_token)
									 .getClaim("sub")
									 .asString();
				
				User user = useres.findByUsername(username);
				if(user==null) throw new Exception(); // 사용자가 없을 때 
				
				// 토큰을 다시 만들어 보낸다.
				String reAccess_token = jwtToken.makeAccessToken(username);
				String reRefresh_token = jwtToken.makeRefreshToken(username);
				Map<String, String> map = new HashMap<>();
				map.put("access_token", JwtProperties.TOKEN_PREFIX+reAccess_token);
				map.put("refresh_token", JwtProperties.TOKEN_PREFIX+reRefresh_token);
				
				String reToken = objmapper.writeValueAsString(map); // map->jsonString
				response.addHeader(JwtProperties.HEADER_STRING, reToken);
				response.setContentType("application/json; charset=utf-8");
				
			} catch (Exception e2) {
				e2.printStackTrace();
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
			}
		} catch(Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 에러");
		}

		

		// 토큰이 없거나 Bearer가 아니거나 
		/*
				String authentication = request.getHeader(JwtProperties.HEADER_STRING);
				System.out.println("AA");
				System.out.println(authentication);
				if(authentication==null || !authentication.startsWith(JwtProperties.TOKEN_PREFIX)) {
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "로그인 필요");
					return;
				}

				// Bearer 제거 
				String token = authentication.replace(JwtProperties.TOKEN_PREFIX, "");
				
				try {
					String username = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET))
									 .build()
									 .verify(token)
									 .getClaim("sub")
									 .asString();
					System.out.println("token"+username);
					// Database에서 사용자를 검색하여 UserDetails 타입으로 변환, 
					// 다시 Authentication 타입으로 변환하여 security sessio(SecurityContextHolder)에 넣는다.
					User user = useres.findByUsername(username);
					PrincipalDetails principalDetails = new PrincipalDetails(user);
					UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken
															(principalDetails, null, principalDetails.getAuthorities());
					 SecurityContextHolder.getContext().setAuthentication(auth);
					 chain.doFilter(request, response);
				} catch(Exception e) {
					e.printStackTrace();
					response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "토큰 에러");
				}
				*/
	}
}
