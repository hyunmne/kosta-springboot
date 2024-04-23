package com.kosta.board.dao;

import java.util.List;

import com.kosta.board.dto.BFile;
import com.kosta.board.dto.Board;

public interface BoardDao {
	List<Board> selectBrdList(Integer row) throws Exception;
	Integer selectBrdCnt() throws Exception;	
	Board selectBoard(Integer num) throws Exception;
	void insertBoard(Board board) throws Exception;
	void insertFile(BFile file) throws Exception;
	void updateBoard(Board board) throws Exception;
	void updateBrdViewCnt(Integer num) throws Exception;
	void deleteFile(Integer num) throws Exception;
	
	List<Board> selectBrdSearchList(Integer row, String type, String word) throws Exception;
	Integer selectBrdSearchCnt(String type, String word) throws Exception;	
}
