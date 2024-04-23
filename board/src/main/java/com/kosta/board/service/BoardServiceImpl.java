package com.kosta.board.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dao.BoardDao;
import com.kosta.board.dao.BoardLikeDao;
import com.kosta.board.dto.BFile;
import com.kosta.board.dto.Board;
import com.kosta.board.util.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDao brdDao;
	
	@Autowired
	private BoardLikeDao brdLikeDao;

	@Override
	public List<Board> boardListByPage(PageInfo pageInfo, String type, String word) throws Exception {
		// 1. 페이지를 가져오고 없으면 페이지 번호를 1로 한다.
		Integer page = pageInfo.getCurPage();
		int row = (page-1)*10;
		List<Board> boardList = null;

		// 2. PageInfo 계산하여 설정하기
		// 전체 글 갯수를 double 타입으로 변환 
		// 소수를 반올림해주는 함수 Ceil 사용
		// 반올림한 소수를 int로 형변환 해 소숫점 없애기 
		int maxPage = 1; 
		if (type != null && word!=null) { // 검색
			maxPage = (int) Math.ceil((double)brdDao.selectBrdSearchCnt(type, word)/10); 
			boardList = brdDao.selectBrdSearchList(row, type, word);
		} else { // 전체 목록 조회
			maxPage = (int) Math.ceil((double)brdDao.selectBrdCnt()/10); 
			boardList = brdDao.selectBrdList(row);
		}
		
		int startPage = (page-1)/10*10+1;
		int endPage = startPage+10-1;
		if (endPage > maxPage) endPage = maxPage;

		pageInfo.setAllPage(maxPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);

		return boardList;
	}

	@Override
	public Board brdDetail(Integer num) throws Exception {
		brdDao.updateBrdViewCnt(num);
		return brdDao.selectBoard(num);
	}

	@Override
	public void boardWrite(Board board, MultipartFile file) throws Exception {
		// 1. 파일 업로드 
		// 1-1. 업로드 파일이 있을 경우
		if (file != null && !file.isEmpty()) {
			// 1-2. 업로드할 경로 설정 및 파일 최대 크기 설정
			String path = "C:/lhm/spring_upload/";

			// 1-3. 파일 정보를 BFile 객체에 담아 File 테이블에 삽입
			BFile bfile = new BFile();
			bfile.setDirectory(path);
			bfile.setName(file.getOriginalFilename());
			bfile.setSize(file.getSize());
			bfile.setContenttype(file.getContentType());
			brdDao.insertFile(bfile); // DB 저장 - 번호생성

			// 1-4. 경로와 파일 번호로 파일 객체를 생성
			File upFile = new File(path, bfile.getNum()+"");
			// 1-5. 브라우저에서 업로드한 파일의 데이터를 file 객체에 이동
			file.transferTo(upFile);
			board.setFilenum(bfile.getNum());
		}

		// 2. 게시물 테이블에 삽입
		brdDao.insertBoard(board);
	}

	@Override
	public void boardModify(Board board, MultipartFile file) throws Exception {
		Integer beforeFileNum = null;
		// 1. 업로드할 경로 설정 및 파일 최대 크기 설정
		String path = "C:/lhm/spring_upload/";

		if (file != null && !file.isEmpty()) {
			// 2. 기존 파일 정보와 파일 삭제
			beforeFileNum = brdDetail(board.getNum()).getFilenum();

			// 3. 새 파일 업로드 & 새 파일 정보 삽입
			BFile bfile = new BFile();
			bfile.setDirectory(path);
			bfile.setName(file.getOriginalFilename());
			bfile.setSize(file.getSize());
			bfile.setContenttype(file.getContentType());
			brdDao.insertFile(bfile); // DB 저장 - 번호생성

			// 1-4. 경로와 파일 번호로 파일 객체를 생성
			File upFile = new File(path, bfile.getNum()+"");
			// 1-5. 브라우저에서 업로드한 파일의 데이터를 file 객체에 이동
			file.transferTo(upFile);
			board.setFilenum(bfile.getNum());
		}

		brdDao.updateBoard(board);
		// 2-1. 파일 정보가 있을 때만 삭제해준다.
		if (beforeFileNum != null) {
			brdDao.deleteFile(beforeFileNum);
			File beforeFile = new File(path, beforeFileNum+"");
			beforeFile.delete();
		}
	}

	@Override
	public Boolean isSelectBoardLike(String memberId, Integer boardNum) throws Exception {
		Integer num = brdLikeDao.selectBrdLike(memberId, boardNum);
		return num!=null;
	}

	@Override
	public Boolean checkBoardLike(String memberId, Integer boardNum) throws Exception {
		Boolean isLike = isSelectBoardLike(memberId, boardNum);
		if (isLike) {
			brdLikeDao.deleteBrdLike(memberId, boardNum);
		} else {
			brdLikeDao.insertBrdLike(memberId, boardNum);
		}
		return !isLike;
	}

}
