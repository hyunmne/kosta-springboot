package com.kosta.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{
	Page<Board> findBySubjectContains(String subject, PageRequest pageRequest);
	Page<Board> findByContentContains(String content, PageRequest pageRequest);
	// member 테이블의 id.. ? 
	Page<Board> findByMember_Id(String writer, PageRequest pageRequest);
}
