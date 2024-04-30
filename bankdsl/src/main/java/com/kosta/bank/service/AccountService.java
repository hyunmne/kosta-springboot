package com.kosta.bank.service;

import java.util.List;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;

public interface AccountService {
	void makeAccount(AccountDto acc) throws Exception;
	AccountDto accountInfo(String id) throws Exception;
	void deposit(String id, Integer money) throws Exception;
	void withdraw(String id, Integer money) throws Exception;
	List<AccountDto> accList() throws Exception;
	Boolean checkAccountDoubleId(String id) throws Exception;
	void transfer(String sid, String rid, Integer money) throws Exception;
}
