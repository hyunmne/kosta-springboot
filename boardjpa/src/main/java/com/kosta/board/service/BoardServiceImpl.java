package com.kosta.board.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.entity.BFile;
import com.kosta.board.entity.Board;
import com.kosta.board.repository.BoardRepository;
import com.kosta.board.repository.FileRepository;
import com.kosta.board.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	@Value("${upload.path}")
	private String uploadPath;
	
	private final FileRepository fileRepository;
	private final BoardRepository boardRepository;
	
	@Override
	public void boardWrite(BoardDto boardDto, MultipartFile file) throws Exception {
		if(file!=null && !file.isEmpty()) {
			BFile bFile = BFile.builder()
							.name(file.getOriginalFilename())
							.directory(uploadPath)
							.size(file.getSize())
							.contenttype(file.getContentType())
							.build();
			
			fileRepository.save(bFile); // DB에 file 저장
			
			File upFile = new File(uploadPath, bFile.getNum()+"");
			file.transferTo(upFile); // 폴더에 파일 업로드 
			boardDto.setFileNum(bFile.getNum());
		}
		
		Board board = boardDto.toBoard();
		boardRepository.save(board); // board table에 글 삽입 
	}

	@Override
	public List<BoardDto> boardListByPage(PageInfo pageInfo, String type, String word) throws Exception {
		// 페이징 연산 필요 없이 알어서 계산해준 걸 객체에 넣음.. 네?
		// 몇번째페이지 ?  , 한 페이지의 갯수, 정렬 방향
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 10, Sort.by(Sort.Direction.ASC, "num"));
		Page<Board> pages = null;

		if (word==null || word.trim().equals("")) { // 목록 조회
			pages = boardRepository.findAll(pageRequest);
		} else { // 검색
			if (type.equals("subject")) {
				pages = boardRepository.findBySubjectContains(word, pageRequest);
			} else if (type.equals("content")) {
				pages = boardRepository.findByContentContains(word, pageRequest);
			} else if (type.equals("writer")) {
				pages = boardRepository.findByMember_Id(word, pageRequest);
			}
		}
		pageInfo.setAllPage(pages.getTotalPages()); // 전체 페이지의 수
		
		int startPage = (pageInfo.getCurPage()-1)/10*10+1;
		int endPage = Math.min(startPage+10-1, pageInfo.getAllPage());
		
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (Board brd : pages.getContent()) {
			boardDtoList.add(brd.toBoardDto()); // 반복문으로 객체에 데이터 넣어주기..
		}
		
		return boardDtoList;
	}

	@Override
	public BoardDto boardDetail(Integer num) throws Exception {
		Optional<Board> obrd = boardRepository.findById(num);
		if(obrd.isEmpty()) throw new Exception("글번호 오류");
		return obrd.get().toBoardDto();
	}

	@Override
	public void boardModify(BoardDto boardDto, MultipartFile file) throws Exception {
		BoardDto beforeBoard = boardDetail(boardDto.getNum());
		
		if(file!=null && !file.isEmpty()) {
			BFile bFile = BFile.builder()
							.name(file.getOriginalFilename())
							.directory(uploadPath)
							.size(file.getSize())
							.contenttype(file.getContentType())
							.build();
			
			fileRepository.save(bFile); // DB에 file 저장
			
			File upFile = new File(uploadPath, bFile.getNum()+"");
			file.transferTo(upFile); // 폴더에 파일 업로드 
			boardDto.setFileNum(bFile.getNum());
		} else {
			boardDto.setFileNum(beforeBoard.getFileNum());
		}
		
		Board board = boardDto.toBoard();
		board.setNum(boardDto.getNum());
		boardRepository.save(board); // board table에 글 삽입 
		
		if (beforeBoard != null) {
			fileRepository.deleteById(beforeBoard.getFileNum());
			File beforeFile = new File(uploadPath, beforeBoard.getFileNum()+"");
			beforeFile.delete();
		}
	}
}
