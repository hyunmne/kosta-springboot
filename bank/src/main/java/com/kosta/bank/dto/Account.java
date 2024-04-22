package com.kosta.bank.dto;

public class Account {
	private String id;
	private String name;
	private Integer balance;
	private String type;
	private String grade;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public Account(String id, String name, Integer balance, String type, String grade) {
		super();
		this.id = id;
		this.name = name;
		this.balance = balance;
		this.type = type;
		this.grade = grade;
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
