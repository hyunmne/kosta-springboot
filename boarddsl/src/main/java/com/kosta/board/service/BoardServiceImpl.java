package com.kosta.board.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

	@Override
	public void boardWrite(BoardDto boardDto, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BoardDto> boardListByPage(PageInfo pageInfo, String type, String word) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BoardDto boardDetail(Integer num) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void boardModify(BoardDto boardDto, MultipartFile file) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Boolean isSelectedBrdLike(String memberId, Integer boardNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean checkBoardLike(String memberId, Integer boardNum) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
