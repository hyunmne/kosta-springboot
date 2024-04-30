package com.kosta.bank.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.bank.dto.MemberDto;
import com.kosta.bank.service.MemberService;

@Controller
public class MemberController {
	
	@Autowired
	private MemberService memservice;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("/join")
	public String join() {
		return "join";
	}
	
	@PostMapping("/join")
	public String join(MemberDto memdto, Model model) {
		try {
			memservice.join(memdto);
			return "login";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", "회원가입 실패");
			return "error";
		}
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/logout")
	public String Logout(Model model) {
		try {
			session.removeAttribute("user");
			return "login";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	@PostMapping("/login")
	public String login(Model model, @RequestParam("id") String id, @RequestParam("password") String password) {
		try {
			memservice.login(id, password);
			session.setAttribute("user", id);
			return "makeAccount";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	@ResponseBody
	@PostMapping("/memberDoubleId")
	public String memberDoubleId(String id) {
		try {
			Boolean check = memservice.doubleMemberCheckId(id);
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
}
