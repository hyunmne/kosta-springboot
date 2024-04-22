package com.kosta.bank.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kosta.bank.dao.AccountDao;
import com.kosta.bank.dto.Account;

// 
@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accDao;

	@Override
	public void makeAccount(Account acc) throws Exception {
		if (accDao.selectAccount(acc.getId())!=null) {
			throw new Exception("계좌번호 중복 오류");
		};
		accDao.insertAccount(acc);
	}

	@Override
	public Account accountInfo(String id) throws Exception {
		Account acc = accDao.selectAccount(id);
		if (acc == null) throw new Exception("계좌번호 오류");
		return acc;
	}

	@Override
	public void deposit(String id, Integer money) throws Exception {
		Account acc = accDao.selectAccount(id);
		if(acc==null) throw new Exception("계좌번호 오류");
		acc.deposit(money);
		
		accDao.updateAccountBalance(id,acc.getBalance());
	}

	@Override
	public void withdraw(String id, Integer money) throws Exception {
		Account acc = accDao.selectAccount(id);
		if(acc==null) throw new Exception("계좌번호 오류");
		acc.withdraw(money);
		
		accDao.updateAccountBalance(id,acc.getBalance());
	}

	@Override
	public List<Account> accList() throws Exception {
		return accDao.selectAccList();
	}

	@Override
	public Boolean checkAccountDoubleId(String id) throws Exception {
		Account acc = accDao.selectAccount(id);
		return acc!=null;
	}

	@Transactional
	@Override
	public void transfer(String sid, String rid, Integer money) throws Exception {
		Account sacc = accDao.selectAccount(sid);
		if(sacc==null) throw new Exception("보내는 계좌번호 오류");
		sacc.withdraw(money);
		
		Account racc = accDao.selectAccount(rid);
		if(racc==null) throw new Exception("받는 계좌번호 오류");
		racc.deposit(money);
		
		accDao.updateAccountBalance(sid,sacc.getBalance());
		accDao.updateAccountBalance(rid,racc.getBalance());
	}

}
