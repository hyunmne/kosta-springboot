package com.kosta.bank.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosta.bank.dto.Account;

@Mapper
@Repository
public interface AccountDao {
	void insertAccount(Account acc) throws Exception;
	Account selectAccount(String id) throws Exception;
	void updateAccountBalance(@Param("id")String id,@Param("balance") Integer balance) throws Exception;
	List<Account> selectAccList() throws Exception;
}
