package com.kosta.board.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
	// 변수_그안의속성
	Optional<BoardLike> findByMember_IdAndBoard_Num(String id, Integer num);
}
