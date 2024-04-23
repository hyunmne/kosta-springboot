package com.kosta.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kosta.board.dao.MemberDao;
import com.kosta.board.dto.Member;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Autowired
	private MemberDao memDao;

//	public void setMemDao(MemberDao memDao) {
//		this.memDao = memDao;
//	}

	@Override
	public void join(Member mem) throws Exception {
		if (memDao.selectMember(mem.getId())!=null) {
			throw new Exception("아이디 중복 오류");
		}
		memDao.insertMember(mem);
	}
	
	@Override
	public Member login(String id, String password) throws Exception {
		System.out.println(id);
		System.out.println(password);
		Member mem = memDao.selectMember(id); // id로 db에서 member테이블에서 찾은 값을 mem에 넣어준다.
		if (mem == null) throw new Exception("아이디가 틀립니다."); // mem이 비어있으면 회원정보 존재 ㄴㄴ 
		if (!mem.getPassword().equals(password)) throw new Exception("비밀번호가 틀렸습니다.");
		
		return mem;
	}

	@Override
	public Boolean doubleMemberCheckId(String id) throws Exception {
		Member mem = memDao.selectMember(id);
		return mem!=null;
	}

}
