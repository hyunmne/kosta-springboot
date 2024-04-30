package com.kosta.bank.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;
import com.kosta.bank.repository.BankRepository;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private BankRepository bankres;
	
	@Override // 계좌 중복체크
	public Boolean checkAccountDoubleId(String id) throws Exception {
		Account acc = bankres.findAccountById(id);
		return acc!=null;
	}
	
	@Override
	public void makeAccount(AccountDto acc) throws Exception {
		Account eacc = bankres.findAccountById(acc.getId());
		if(eacc!=null) throw new Exception("계좌번호 중복 오류");
		bankres.insertAccount(acc.toAccount());
	}

	@Override
	public AccountDto accountInfo(String id) throws Exception {
		Account acc = bankres.findAccountById(id);
		if (acc==null) throw new Exception("계좌번호 오류");
		return acc.toAccountDto();
	}

	@Override
	public void deposit(String id, Integer money) throws Exception {
		Account acc = bankres.findAccountById(id);
		if(acc==null) throw new Exception("계좌번호오류");
		acc.deposit(money);
		bankres.updateBalance(id, acc.getBalance());
	}

	@Override
	public void withdraw(String id, Integer money) throws Exception {
		Account acc = bankres.findAccountById(id);
		if(acc==null) throw new Exception("계좌번호오류");
		acc.withdraw(money);
		bankres.updateBalance(id, acc.getBalance());
	}

	@Override
	public List<AccountDto> accList() throws Exception {
		List<Account> accList = bankres.findAllAccount();
		return accList.stream() // 데이터 하나씩
					  .map(Account::toAccountDto)
					  .collect(Collectors.toList());
	}

	@Override
	public void transfer(String sid, String rid, Integer money) throws Exception {
		Account sacc = bankres.findAccountById(sid);
		if (sacc==null) throw new Exception("보내는 계좌 오류");
		sacc.withdraw(money);
		
		Account racc = bankres.findAccountById(rid);
		if (racc==null) throw new Exception("받는 계좌 오류");
		racc.deposit(money);
		
		bankres.transfer(sid, rid, sacc.getBalance(), racc.getBalance());
	}

}
