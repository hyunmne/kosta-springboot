package com.kosta.board.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memres;
	
	@Override
	public void join(MemberDto mem) throws Exception {
		Optional<Member> omem = memres.findById(mem.getId());
		if(omem.isPresent()) throw new Exception("아이디 중복 오류");
		memres.save(mem.toEntity());
	}

	@Override
	public MemberDto login(String id, String password) throws Exception {
		Optional<Member> omem = memres.findById(id);
		if(omem.isEmpty()) throw new Exception("아이디 오류");
		
		Member member = omem.get();
		if(!member.getPassword().equals(password.trim())) {
			throw new Exception ("비밀번호 오류");
		}
		return member.toDto();
	}

	@Override
	public Boolean checkDoubleId(String id) throws Exception {
		Optional<Member> omem = memres.findById(id);
		return omem.isPresent();
	}

	@Override
	public Boolean checkDoubleNickName(String nickName) throws Exception {
		Optional<Member> omem = memres.findByNickname(nickName);
		return omem.isPresent();
	}

}
