package com.kosta.board.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BoardDslRepository {
	@Autowired
	private JPAQueryFactory jpqQueryFactory;
}
