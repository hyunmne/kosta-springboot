package com.kosta.bank.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kosta.bank.entity.Account;
import com.kosta.bank.service.AccountService;


@Controller
public class AccountController {
	
	@Autowired
	private AccountService accService;
	
	@GetMapping("/makeAccount")
	public String makeAccount() {
		return "makeAccount";
	}
	
	@PostMapping("/makeAccount")
	public String makeAccount(@ModelAttribute("acc") Account acc, Model model) {
		try {
			accService.makeAccount(acc);
			return "accountInfo";
		} catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("err", e.getMessage());
			return "error";
		}
	}
	
	@GetMapping("/accountInfo")
	public String accountInfo() {
		return "accountInfoForm";
	}
	
	@PostMapping("/accountInfo")
	public ModelAndView accountInfo(@RequestParam("id") String id) {
		ModelAndView mav = new ModelAndView();
		try {
			mav.addObject("acc", accService.accountInfo(id));
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@GetMapping("/deposit")
	public String deposit() {
		return "deposit";
	}
	
	@GetMapping("/withdraw")
	public String withdraw() {
		return "withdraw";
	}
	
	@GetMapping("/transfer")
	public String transfer() {
		return "transfer";
	}
	
	@PostMapping("/deposit")
	public ModelAndView deposit(@RequestParam("id") String id, @RequestParam("money") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			accService.deposit(id, money);
			mav.addObject("acc", accService.accountInfo(id));
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@PostMapping("/withdraw")
	public ModelAndView withdraw(@RequestParam("id") String id, @RequestParam("money") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			accService.withdraw(id, money);
			mav.addObject("acc", accService.accountInfo(id));
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@GetMapping("/allAccountInfo")
	public ModelAndView allAccountInfo() {
		ModelAndView mav = new ModelAndView();
		try {
			List<Account> accs = accService.accList();
			mav.addObject("accs", accs);
			mav.setViewName("allAccountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	@PostMapping("/transfer")
	public ModelAndView transfer(@RequestParam("sid") String sid, @RequestParam("rid") String rid, @RequestParam("money") Integer money) {
		ModelAndView mav = new ModelAndView();
		try {
			accService.transfer(sid, rid, money);
			mav.addObject("acc", accService.accountInfo(sid));
			mav.setViewName("accountInfo");
		} catch(Exception e) {
			e.printStackTrace();
			mav.addObject("err", e.getMessage());
			mav.setViewName("error");
		}
		return mav;
	}
	
	
}
