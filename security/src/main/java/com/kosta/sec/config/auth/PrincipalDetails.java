package com.kosta.sec.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kosta.sec.entity.User;

import lombok.Data;


// security가  /loginProc 주소를 낚아채서 로그인을 진행시킨다. 
// 로그인 진행이 완료가 되면 security session을 만들어준다. (Security ContextHolder)
// security sessionm에 들어가는 타입은 Authentication 타입의 객체여야 한다.
// Authentication안에 User 정보를 넣어야 한다.
// 그 User object 타입은 UserDetails 타입이어야 한다.
// 즉, (Security Context Holder( new Authentication ( new UserDetails ( new User ) ) )

@Data
public class PrincipalDetails implements UserDetails {

	public User user;
	
	public PrincipalDetails(User user) {
		super();
		this.user=user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collect = new ArrayList<>();
		collect.add(()->user.getRoles());
		return null;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// 우리 사이트에서 1년 동안 로그인을 안 하면 휴면 계쩡으로 변환하기로 했다면
		// 현재 시간 - 마지막 로그인 시간을 계산하여 1년 초과하면 return false;
		return true;
	}

}
