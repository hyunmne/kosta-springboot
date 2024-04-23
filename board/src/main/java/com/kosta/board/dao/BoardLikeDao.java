package com.kosta.board.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface BoardLikeDao {
	void insertBrdLike(@Param("memberId") String memberId,@Param("boardNum") Integer boardNum) throws Exception;
	void deleteBrdLike(@Param("memberId") String memberId,@Param("boardNum") Integer boardNum) throws Exception;
	Integer selectBrdLike(@Param("memberId") String memberId,@Param("boardNum") Integer boardNum) throws Exception;
}
