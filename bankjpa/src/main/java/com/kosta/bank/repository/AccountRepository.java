package com.kosta.bank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.bank.entity.Account;

// 참조할 entity와 entity primary key의 타입!!
public interface AccountRepository extends JpaRepository<Account, String> {

}
