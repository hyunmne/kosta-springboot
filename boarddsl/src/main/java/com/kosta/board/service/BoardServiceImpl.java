package com.kosta.board.service;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kosta.board.dto.BoardDto;
import com.kosta.board.entity.BFile;
import com.kosta.board.entity.Board;
import com.kosta.board.entity.BoardLike;
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
		Optional<Board> obrd = brdres.findById(num);
		if (obrd.isEmpty()) throw new Exception("글번호오류");
		brddslres.setViewCnt(num, obrd.get().getViewCount()+1); // 조회수 올리기 
		return obrd.get().toBoardDto();
	}

	@Override
	public void boardModify(BoardDto boardDto, MultipartFile file) throws Exception {
		// 1. 기존 파일 : O, 변경 파일 : O | boardDto.setNum(new File)
		// 2. 기존 파일 : O, 변경 파일 : X | boardDto.setNum(before File), 기존 파일 삭제
		// 3. 기존 파일 : X, 변경 파일 : O | boardDto.setNum(new File)
		// 4. 기존 파일 : X, 변경 파일 : X | boardDto.setNum()
		Board beforeBrd = brdres.findById(boardDto.getNum()).get();

		if(file != null && !file.isEmpty()) {  //변경할 파일이 있는 경우 (1,3)

			if (beforeBrd.getFileNum()!=null) { // 1
				fileres.deleteById(beforeBrd.getFileNum());
				File beforeFile = new File(uploadPath, beforeBrd.getFileNum()+"");
				beforeFile.delete();
			}

			//새 파일 업로드 & 새 파일정보 삽입
			BFile bFile = BFile.builder()
					.name(file.getOriginalFilename())
					.directory(uploadPath)
					.size(file.getSize())
					.contenttype(file.getContentType())
					.build();
			fileres.save(bFile); // file 테이블에 파일 정보 삽입
			File upFile = new File(uploadPath, bFile.getNum()+"");
			file.transferTo(upFile); // file upload
			boardDto.setFileNum(bFile.getNum());
		} else { // 2, 4
			if(beforeBrd.getFileNum()!=null) { // 2
				boardDto.setFileNum(beforeBrd.getNum());
			}
		}

		brdres.save(boardDto.toBoard());

	}

	// 좋아요 상태 변경
	@Override
	public Boolean isSelectedBrdLike(String memberId, Integer boardNum) throws Exception {
		BoardLike brdLike = brddslres.findBoardLike(memberId, boardNum);
		return brdLike!=null;
	}

	@Override
	public Boolean checkBoardLike(String memberId, Integer boardNum) throws Exception {
		BoardLike brdLike = brddslres.findBoardLike(memberId, boardNum);
		
		if(brdLike==null) {
			brdlikeres.save(BoardLike.builder()
									 .memberId(memberId)
				                     .boardNum(boardNum)
				                     .build());
			return true;
		} else {
			brdlikeres.deleteById(brdLike.getNum());
			return false;
		}
	}

}
