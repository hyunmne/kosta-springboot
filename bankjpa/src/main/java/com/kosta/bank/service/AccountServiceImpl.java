package com.kosta.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.AccountDto;
import com.kosta.bank.entity.Account;
import com.kosta.bank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accRepos;
	
	@Override
	public void makeAccount(AccountDto acc) throws Exception {
		Optional<Account> oacc = accRepos.findById(acc.getId());
		if(oacc.isPresent()) throw new Exception("계좌번호 중복오류");
		// 자동 insert
		accRepos.save(acc.toAccount());
	}

	@Override
	public AccountDto accountInfo(String id) throws Exception {
		Optional<Account> oacc = accRepos.findById(id); // selectAcc
		if(oacc.isEmpty()) throw new Exception("계좌번호 오류");
		return oacc.get().toAccountDto();
	}

	@Override
	public void deposit(String id, Integer money) throws Exception {
		Optional<Account> oacc = accRepos.findById(id);
		if(oacc.isEmpty()) throw new Exception("계좌 번호 오류");
		Account acc = oacc.get();
		acc.deposit(money);
		accRepos.save(acc);
	}

	@Override
	public void withdraw(String id, Integer money) throws Exception {
		Optional<Account> oacc = accRepos.findById(id);
		if(oacc.isEmpty()) throw new Exception("계좌 번호 오류");
		Account acc = oacc.get();
		acc.withdraw(money);
		accRepos.save(acc);
	}

	@Override
	public List<AccountDto> accList() throws Exception {
		List<AccountDto> list = new ArrayList<>();
		for (Account acc : accRepos.findAll()) {
			list.add(acc.toAccountDto());
		}
		return list; 
	}

	@Override
	public Boolean checkAccountDoubleId(String id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void transfer(String sid, String rid, Integer money) throws Exception {
		Optional<Account> soacc = accRepos.findById(sid);
		Optional<Account> roacc = accRepos.findById(rid);
		if(soacc.isEmpty()) throw new Exception("보내는 계좌 번호 오류");
		if(roacc.isEmpty()) throw new Exception("받는 계좌 번호 오류");
		Account sacc = soacc.get();
		Account racc = roacc.get();
		sacc.withdraw(money);
		racc.deposit(money);
		accRepos.save(sacc);
		accRepos.save(racc);
	}

}
