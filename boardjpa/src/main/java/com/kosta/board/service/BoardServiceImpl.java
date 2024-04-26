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
import com.kosta.board.entity.BoardLike;
import com.kosta.board.entity.Member;
import com.kosta.board.repository.BoardLikeRepository;
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
	private final BoardLikeRepository brdLikeRepository;
	
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
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 10, Sort.by(Sort.Direction.DESC, "num"));
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
		Board board = obrd.get();
		board.setViewCount(board.getViewCount());
		boardRepository.save(board);
		return board.toBoardDto();
	}

	@Override
	   public void boardModify(BoardDto boardDto, MultipartFile file) throws Exception {
	      //파일 수정의 경우
	      //1. 기존파일이 있으면서 파일을 변경할 경우
	      //2. 기존파일이 없으면서 파일을 추가하는 경우
	      //3. 기존파일이 있는데 변경하지 않는 경우
	      
	      BoardDto beforeBoardDto = boardDetail(boardDto.getNum());
	      
	      if(file != null && !file.isEmpty()) {  //변경할 파일이 있는 경우
	         //새 파일 업로드 & 새 파일정보 삽입
	         BFile bFile = BFile.builder()
	                        .name(file.getOriginalFilename())
	                        .directory(uploadPath)
	                        .size(file.getSize())
	                        .contenttype(file.getContentType())
	                        .build();
	         fileRepository.save(bFile);
	         File upFile = new File(uploadPath, bFile.getNum()+"");
	         file.transferTo(upFile);
	         boardDto.setFileNum(bFile.getNum());
	      } else {  //파일을 변경하지 않는 경우 (기존 file정보)
	         boardDto.setFileNum(beforeBoardDto.getFileNum());
	      }
	      
	      boardRepository.save(boardDto.toBoard());
	      
	      //1의 경우에만 삭제
	      //파일을 수정하고 && 기존 업로드된 파일이 있을 경우 기존 업로드한 파일을 테이블에서 데이터 삭제하고 파일 삭제
	      if(file != null && !file.isEmpty() &&  //새로운 파일로 변경하는 조건 
	               beforeBoardDto.getFileNum() != null) {  //기존에 파일이 있는 조건
	         fileRepository.deleteById(beforeBoardDto.getFileNum());
	         File beforeFile = new File(uploadPath, beforeBoardDto.getFileNum()+"");
	         beforeFile.delete();
	      }
	   }
	
	@Override
	public Boolean checkBoardLike(String memberId, Integer boardNum) throws Exception {
		Optional<BoardLike> obrdLike = brdLikeRepository.findByMember_IdAndBoard_Num(memberId, boardNum);
		
		if(obrdLike.isPresent()) { // 기존에 좋아요를 선택했을 경우 좋아요 삭제
			brdLikeRepository.deleteById(obrdLike.get().getNum());
			return false;
		} else { // 기존에 좋아요를 선택하지 않았을 경우 좋아요 삽입
			brdLikeRepository.save(BoardLike.builder()
									.member(Member.builder().id(memberId).build())
									.board(Board.builder().num(boardNum).build())
									.build());
			return true;
		}
	}

	@Override
	public Boolean isSelectedBrdLike(String memberId, Integer boardNum) throws Exception {
		Optional<BoardLike> obrdLike = brdLikeRepository.findByMember_IdAndBoard_Num(memberId, boardNum);
		return obrdLike.isPresent();
	}
}
