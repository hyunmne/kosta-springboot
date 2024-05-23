package com.kosta.sec.config.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

// security 설정에서 loginProcessingUrl("/loginProc");
// loginProc 요청이 오면 자동으로 UserDetailService의 타입으로 IoC 되어있는 loadUserByUsername 함수가 호출된다.
@Service
public class PrincipalDetailSerivce implements UserDetailsService {

	@Autowired
	private UserRepository useres;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = useres.findByUsername(username);
		if (user!=null) return new PrincipalDetails(user);
		return null;
	}
}
