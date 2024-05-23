package com.kosta.sec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.CorsFilter;

import com.kosta.sec.config.oauth.PrincipalOauth2UserService;

@Configuration // Ioc bean 등록
@EnableWebSecurity //필터 체인 관리 시작 어노테이션 
@EnableGlobalMethodSecurity(prePostEnabled= true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private CorsFilter corsFilter;
	
	// 암호화
	@Bean
	public BCryptPasswordEncoder encodePw() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilter(corsFilter) // 다른 도메인 접근 허용
			.csrf().disable(); // csrf 공격 비활성화
		
		// login
		http.formLogin().disable() // 로그인 폼 비활성화
			.httpBasic().disable(); // httpBasicdms header에 username, password를 암호화하지 않은 상태로 주고받는다.
			
		// Oauth2 login
		http.oauth2Login()
			.authorizationEndpoint().baseUri("/oauth2/authorization")
			.and()
			.redirectionEndpoint().baseUri("/oauth2/callback/*")
			.and()
			.userInfoEndpoint().userService(principalOauth2UserService);
		
		http.authorizeRequests()
			.antMatchers("/user/**").authenticated() // 로그인 필수
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인&권한이 admin이나 manager여야 함.
			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')") // 로그인 & 권한이 manager
			.anyRequest().permitAll(); // 나머지 요청들
		
	}
	
	
}
