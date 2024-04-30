package com.kosta.bank.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.bank.dto.MemberDto;
import com.kosta.bank.entity.Member;
import com.kosta.bank.repository.BankRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private BankRepository bankres;
	
	@Override
	public void join(MemberDto mem) throws Exception {
		Member member = bankres.findMemberById(mem.getId());
		if(member!=null) throw new Exception("아이디 중복 오류");
		bankres.insertMember(mem.toMember());
	}

	@Override
	public MemberDto login(String id, String password) throws Exception {
		Member mem = bankres.findMemberById(id);
		if (mem==null) throw new Exception("아이디 오류");
		if(!mem.getPassword().equals(password)) throw new Exception("비밀번호 오류");
		return mem.toMemberDto();
	}

	@Override
	public Boolean doubleMemberCheckId(String id) throws Exception {
		Member mem = bankres.findMemberById(id);
		return mem!=null;
	}

}
