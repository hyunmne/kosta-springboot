package com.kosta.bank;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.bank.entity.Account;
import com.kosta.bank.entity.Member;
import com.kosta.bank.repository.BankRepository;

@SpringBootTest
class BankdslApplicationTests {

//	@Test
	void contextLoads() {
	}

	@Autowired
	private BankRepository bankres;
	
	@Test
	void accountInfoById() {
		Account acc = bankres.findAccountById("1001");
		System.out.println(acc);
	}
	
	@Test
	void memberInfoById() {
		Member mem = bankres.findMemberById("ruru");
		System.out.println(mem);
	}
}
