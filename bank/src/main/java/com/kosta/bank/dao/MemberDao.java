package com.kosta.bank.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.kosta.bank.dto.Member;

@Mapper
@Repository
public interface MemberDao {
	void insertMember(Member mem) throws Exception;
	Member selectMember(@Param("id") String id) throws Exception;
}
