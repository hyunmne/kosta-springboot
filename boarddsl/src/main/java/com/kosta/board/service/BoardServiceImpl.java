package com.kosta.board.service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.entity.BFile;
import com.kosta.board.entity.Board;
import com.kosta.board.repository.BoardDslRepository;
import com.kosta.board.repository.BoardLikeRepository;
import com.kosta.board.repository.BoardRepository;
import com.kosta.board.repository.FileRepository;
import com.kosta.board.repository.MemberRepository;
import com.kosta.board.util.PageInfo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {
	
	@Value("${upload.path}")
	private String uploadPath;
	
	private final FileRepository fileres;
	private final BoardDslRepository brddslres;
	private final BoardLikeRepository brdlikeres;
	private final MemberRepository memres;
	private final BoardRepository brdres;
	
	@Override
	public void boardWrite(BoardDto boardDto, MultipartFile file) throws Exception {
		if(file!=null && !file.isEmpty()) {
			BFile bFile = BFile.builder()
							.name(file.getOriginalFilename())
							.directory(uploadPath)
							.size(file.getSize())
							.contenttype(file.getContentType())
							.build();
			
			fileres.save(bFile); // DB에 file 저장
			
			File upFile = new File(uploadPath, bFile.getNum()+"");
			file.transferTo(upFile); // 폴더에 파일 업로드 
			boardDto.setFileNum(bFile.getNum());
		}
		
		brdres.save(boardDto.toBoard()); // board table에 글 삽입 
	}

	@Override
	public List<BoardDto> boardListByPage(PageInfo pageInfo, String type, String word) throws Exception {
		PageRequest pageRequest = PageRequest.of(pageInfo.getCurPage()-1, 10); // repository -> offset, limit
		
		List<Board> brdList = null; // 페이징작업
		Long allCount = 0L;
		
		if(word==null || word.trim().equals("")) {
			brdList = brddslres.findBrdListByPaging(pageRequest); // 페이징작업
			allCount = brddslres.findbrdcnt();
		} else {
			brdList = brddslres.searchBrdListByPaging(pageRequest, word, type);
			allCount = brddslres.searchBrdCnt(type, word);
		}
		
		int allPage = (int)(Math.ceil(allCount.doubleValue()/pageRequest.getPageSize()));
		int startPage = (pageInfo.getCurPage()-1)/10*10+1;
		int endPage = Math.min(startPage+10-1, allPage);
		
		pageInfo.setAllPage(allPage);
		pageInfo.setStartPage(startPage);
		pageInfo.setEndPage(endPage);
		
		return brdList.stream().map(Board::toBoardDto).collect(Collectors.toList());
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
