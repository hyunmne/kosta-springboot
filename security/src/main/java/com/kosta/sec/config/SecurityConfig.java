package com.kosta.sec.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kosta.sec.config.oauth.PrincipalOauth2UserService;

@Configuration // Ioc bean 등록
@EnableWebSecurity //필터 체인 관리 시작 어노테이션 
@EnableGlobalMethodSecurity(prePostEnabled= true, securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	// 암호화
	@Bean
	public BCryptPasswordEncoder encodePw() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private PrincipalOauth2UserService principalOauth2UserService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable(); //cross stie request forgery attack (크로스사이트 요청 위조 공격)
		http.authorizeRequests() // 인증 요청
			.antMatchers("/user/**").authenticated() // 로그인 필수
//			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')") // 로그인&권한이 admin이나 manager여야 함.
//			.antMatchers("/manager/**").access("hasRole('ROLE_MANAGER')") // 로그인 & 권한이 manager
			.anyRequest().permitAll() // 나머지 요청들
			.and()
			.formLogin()
			.loginPage("/login")
			// "/loginProc" 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행
			// 결과적으로 컨트롤러에 따로 "/login"을 구현하지 않아도 괜찮다.
			// 이 로그인 과정에서 필요한 것은 PrincipalDetails를 만둘어주는 것이다.
			.loginProcessingUrl("/loginProc")
			.defaultSuccessUrl("/") // 로그인이 성공적으로 끝나면 리다이렉트할 url
//			.usernameParameter("id")
//			.passwordParameter("pass")
			.and()
			.oauth2Login()
			.loginPage("/login")
			.authorizationEndpoint()
			.baseUri("/oauth2/authorization")
			.and()
			.redirectionEndpoint()
			.baseUri("/oauth2/callback/*")
			.and()
			.userInfoEndpoint()
			.userService(principalOauth2UserService);
	}
	
	
}
