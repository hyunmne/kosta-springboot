package com.kosta.board.dao;

import com.kosta.board.dto.Member;

public interface MemberDao {
	void insertMember(Member mem) throws Exception;
	Member selectMember(String id) throws Exception;
}
