package com.kosta.sec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.sec.config.auth.PrincipalDetails;
import com.kosta.sec.entity.User;
import com.kosta.sec.repository.UserRepository;

@Controller
public class IndexController {
	
	@Autowired
	private UserRepository useres;
	
	private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
	
	@GetMapping({"","/"})
	@ResponseBody
	public String index() {
		return "인덱스입니다";
	}
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
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
	
	@GetMapping("/user")
	@ResponseBody
	public String user(@AuthenticationPrincipal PrincipalDetails principal) {
		System.out.println(principal.getUser());
		return "유저입니다.";
	}
	
	@Secured("ROLE_MANAGER") // 권한이 매니저인 객체만 호출할 수 있다.. (특정 권한만 호출 할 수 있다)
	@GetMapping("/manager")
	@ResponseBody
	public String manager() {
		return "매니저입니다.입니다.";
	}
	
	@PreAuthorize("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
	@GetMapping("/admin")
	@ResponseBody
	public String admin() {
		return "관리자입니다.";
	}
	
	
	
}
