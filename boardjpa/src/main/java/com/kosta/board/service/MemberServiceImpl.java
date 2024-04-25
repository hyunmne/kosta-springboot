package com.kosta.board.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memres;
	
	// 이름이 같은 것들을 바꿔줌 
	private ModelMapper modelmapper = new ModelMapper();
	
	@Override
	public void join(MemberDto memdto) throws Exception {	
		// 앞에 있는 파라미터를 뒤에 있는 타입으로 바꿔줌
		// 동일한 이름을 가진 파라미터들 한정해서... ????????????? 
		Member member = modelmapper.map(memdto, Member.class);
		memres.save(member);
	}

	@Override
	public MemberDto login(String id, String password) throws Exception {
		Optional<Member> omem = memres.findById(id);
		if (omem.isEmpty()) throw new Exception("아이디 오류");
		Member mem = omem.get();
		if(!mem.getPassword().equals(password.trim())) throw new Exception("비밀번호 오류");
		return	modelmapper.map(mem, MemberDto.class);
	}

	@Override
	public Boolean checkDoubleId(String id) throws Exception {
		Optional<Member> omem = memres.findById(id);
		if (omem.isPresent()) return true; // 존재하면 
		return false;
	}

	@Override
	public Boolean checkDoubleNickName(String nickname) throws Exception {
		Optional<Member> omem = memres.findByNickname(nickname);
		if (omem.isPresent()) return true;
 		return false;
	}

}
