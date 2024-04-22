package com.kosta.bank.service;

import java.util.List;

import com.kosta.bank.dto.Account;

public interface AccountService {
	void makeAccount(Account acc) throws Exception;
	Account accountInfo(String id) throws Exception;
	void deposit(String id, Integer money) throws Exception;
	void withdraw(String id, Integer money) throws Exception;
	List<Account> accList() throws Exception;
	Boolean checkAccountDoubleId(String id) throws Exception;
	void transfer(String sid, String rid, Integer money) throws Exception;
}
