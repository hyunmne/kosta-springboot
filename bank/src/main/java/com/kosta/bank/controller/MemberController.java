package com.kosta.bank.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.bank.dto.Member;
import com.kosta.bank.service.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memService;
	
	private HttpSession session;
	
	@GetMapping("/join")
	public String Join() {
		return "join";
	}
	
	@PostMapping("/join")
	public String Join(Member mem, Model model) {
		try {
			memService.join(mem);
			model.addAttribute("mem", mem);
			return "login";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "error";
		}
	}
	
	@ResponseBody
	@PostMapping("/memberDoubleId")
	public String MemberDoubleId(String id) {
		try {
			Boolean check = memService.doubleMemberCheckId(id);
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	@GetMapping("/login")
	public String Login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String Login(String id, String password, String autoLogin, Model model, HttpServletRequest request, HttpServletResponse response) {
		try {

			// 로그인 성공시 cookie check 저저장
			Cookie autoLoginCookie = null;
			Cookie idCookie = null;
			Cookie pwCookie = null;
			if(autoLogin!=null) {
				autoLoginCookie = new Cookie("autoLogin", autoLogin);
				idCookie = new Cookie("id", id);
				pwCookie = new Cookie("password", password);
			} else {
				autoLoginCookie = new Cookie("autoLogin", "false");
				idCookie = new Cookie("id", "");
				pwCookie = new Cookie("password", "");
			}
			response.addCookie(autoLoginCookie);
			response.addCookie(idCookie);
			response.addCookie(pwCookie);

			memService.login(id, password);
			session.setAttribute("user", id);
			return "makeAccount";
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}

	
}
