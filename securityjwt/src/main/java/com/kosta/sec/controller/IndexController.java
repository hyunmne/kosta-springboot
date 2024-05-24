package com.kosta.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

@RestController
public class IndexController {

	@Autowired
	private UserRepository useres;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@GetMapping("/user")
	public ResponseEntity<User> user(Authentication authentication) {
		PrincipalDetails principalDetails = (PrincipalDetails)authentication.getPrincipal();
		User user = principalDetails.getUser();
		System.out.println(user);
		return new ResponseEntity<User> (user,HttpStatus.OK);
	}
	
	@GetMapping({",", "/"})
	public String index() {
		return "인덱스입니다";
	}
	
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "매니저입니다.";
	}
	
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "관리자입니다.";
	}
	
	@PostMapping("/joinProc")
	public String joinProc(User user) {
		System.out.println("회원가입진행 : " + user);
		String rawpw = user.getPassword();
		// 암호화
		String encodepw = bCryptPasswordEncoder.encode(rawpw);
		user.setPassword(encodepw);
		user.setRoles("ROLE_USER");
		useres.save(user);
		return "redirect:/";
	}
}
