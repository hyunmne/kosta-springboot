package com.kosta.bank.service;

import com.kosta.bank.dto.MemberDto;
import com.kosta.bank.entity.Member;

public interface MemberService {
	void join(MemberDto mem) throws Exception;
	MemberDto login(String id, String password) throws Exception; 
	Boolean doubleMemberCheckId(String id) throws Exception;
}
