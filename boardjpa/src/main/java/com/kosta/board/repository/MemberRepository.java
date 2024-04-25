package com.kosta.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
	
	// 컬럼명으로 생성
	Optional<Member> findByNickname(String nickname);
}
