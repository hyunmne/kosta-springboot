package com.kosta.sec.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;

@RestController
public class IndexController {

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
}
