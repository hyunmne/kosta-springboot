package com.kosta.bank.dto;

import com.kosta.bank.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
	private String id;
	private String name;
	private Integer balance;
	private String type;
	private String grade;
	
	public Account toAccount() {
		return new Account(id,name,balance,type,grade);
	}
}
