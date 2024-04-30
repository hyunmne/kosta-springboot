package com.kosta.bank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.kosta.bank.dto.AccountDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Account {
	@Id  //primary key
	private String id;
	@Column 
	private String name;
	@Column 
	private Integer balance;
	@Column 
	private String type;
	@Column 
	private String grade;
	
	public AccountDto toAccountDto() {
		return new AccountDto(id,name,balance,type,grade);
	}
	
	public void deposit(int money) throws Exception {
		if(money < 0) throw new Exception("입금액 오류"); 
		balance += money;
	}
	public void withdraw(int money) throws Exception {
		if(balance < money) throw new Exception("잔액 부족 오류");
		balance -= money;
	}
	
}
