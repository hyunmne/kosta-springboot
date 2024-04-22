package com.kosta.bank.controller;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.dto.Account;
import com.kosta.bank.service.AccountService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor // 생성자 자동생성하고 초기화 = @autowired
public class AccountController {

	private final AccountService accService;

	@GetMapping("/makeAccount")
	public String makeAccount() {
		return "makeAccount";
	}
	
	@PostMapping("/makeAccount")
	public ModelAndView makeAccount(Account acc) {
		ModelAndView mav = new ModelAndView();
		try {
			accService.makeAccount(acc);
			mav.addObject("acc", accService.accountInfo(acc.getId()));
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		
		return mav;
	}
	
	@ResponseBody
	@PostMapping("/accountDoubleId")
	public String accountDoubleIdCheck(String id) {
		try {
			Boolean check = accService.checkAccountDoubleId(id);
			return String.valueOf(check);
		} catch(Exception e) {
			e.printStackTrace();
			return "false";
		}
	}
	
	@GetMapping("/accountInfo")
	public String accountInfo() {
		return "accountInfoForm";
	}
	
	@PostMapping("/accountInfo")
	public ModelAndView accountInfo(String id) {
		ModelAndView mav = new ModelAndView();
		try {
			Account acc = accService.accountInfo(id);
			mav.addObject("acc", acc);
			mav.setViewName("accountInfo");
		} catch (Exception e) {
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@GetMapping("/allAccountInfo")
	public String allAccountInfo(Model model) {
		try {
			List<Account> accs = accService.accList();
			model.addAttribute("accs", accs);
			return "allAccountInfo";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("message", e.getMessage());
			return "error";
		}
	}
	
	@GetMapping("/deposit")
	public String Deposit() {
		return "deposit";
	}
	
	@GetMapping("/withdraw")
	public String Withdraw() {
		return "withdraw";
	}
	
	@GetMapping("/transfer")
	public String Transfer() {
		return "transfer";
	}
	
	@PostMapping("/deposit")
	public ModelAndView Deposit(@Param("id")String id, Integer money) {
		ModelAndView mav = new ModelAndView();
		
		try {
			accService.deposit(id, money);
			Account acc = accService.accountInfo(id);
			mav.addObject("acc", acc);
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("message", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@PostMapping("/withdraw")
	public ModelAndView Withdraw(@Param("id")String id, Integer money) {
		ModelAndView mav = new ModelAndView();
		
		try {
			accService.withdraw(id, money);
			Account acc = accService.accountInfo(id);
			mav.addObject("acc", acc);
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("message", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@PostMapping("/transfer")
	public ModelAndView Transfer(String sid, String rid, Integer money) {
		ModelAndView mav = new ModelAndView();
		
		try {
			accService.transfer(sid, rid, money);
			Account acc = accService.accountInfo(sid);
			mav.addObject("acc", acc); // 데이터..
			mav.setViewName("accountInfo"); // 이동페이지,.. ?
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}

		return mav;
	}
	
}
