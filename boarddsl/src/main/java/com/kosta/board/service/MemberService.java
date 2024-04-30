package com.kosta.board.service;

import com.kosta.board.dto.MemberDto;
import com.kosta.board.entity.Member;

public interface MemberService {
	void join(MemberDto mem) throws Exception;
	MemberDto login(String id, String password) throws Exception; 
	Boolean checkDoubleId(String id) throws Exception;
	Boolean checkDoubleNickName(String nickName) throws Exception;
}
