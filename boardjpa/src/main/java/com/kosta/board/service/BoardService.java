package com.kosta.board.service;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.util.PageInfo;

public interface BoardService {
	void boardWrite(BoardDto boardDto, MultipartFile file) throws Exception;
	List<BoardDto> boardListByPage(PageInfo pageInfo, String type, String word) throws Exception;
	BoardDto boardDetail(Integer num) throws Exception;
	void boardModify(BoardDto boardDto, MultipartFile file) throws Exception;
}
