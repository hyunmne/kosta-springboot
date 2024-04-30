package com.kosta.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.board.entity.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
}
