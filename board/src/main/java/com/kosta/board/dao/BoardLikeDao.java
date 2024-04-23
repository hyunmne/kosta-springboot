package com.kosta.board.dao;


public interface BoardLikeDao {
	void insertBrdLike(String memberId, Integer boardNum) throws Exception;
	void deleteBrdLike(String memberId, Integer boardNum) throws Exception;
	Integer selectBrdLike(String memberId, Integer boardNum) throws Exception;
}
