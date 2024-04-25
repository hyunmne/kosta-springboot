package com.kosta.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.service.MemberService;

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
			session.removeAttribute("nickname");
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
			MemberDto memDto = memservice.login(id, password);
			session.setAttribute("user", id);
			session.setAttribute("nickname", memDto.getNickname());
			return "redirect:boardList";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	@ResponseBody
	@PostMapping("/memberDoubleId")
	public String idDoubleCheck(@RequestParam("id") String id) {
		try {
			Boolean check = memservice.checkDoubleId(id);
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "none";
		}
	}
	
	@ResponseBody
	@PostMapping("/memberDoubleNickName")
	public String nickNameDoubleCheck(@RequestParam("nickname")String nickname) {
		try {
			Boolean check = memservice.checkDoubleNickName(nickname);
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "none";
		}
	}
}
