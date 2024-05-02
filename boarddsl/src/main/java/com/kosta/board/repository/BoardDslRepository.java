package com.kosta.board.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.BoardLike;
import com.kosta.board.entity.QBoard;
import com.kosta.board.entity.QBoardLike;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BoardDslRepository {
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private BoardRepository brdres;
	
	// 글 작성
	public void insertBoard(Board brd) {
		brdres.save(brd);
	}
	
	// 상세조회
	public List<Board> findBrdListByPaging(PageRequest pageRequest) throws Exception {
		QBoard brd = QBoard.board;
		return jpaQueryFactory.selectFrom(brd)
				              .orderBy(brd.num.desc()) // 정렬순서
				              .offset(pageRequest.getOffset()) // ~부터
				              .limit(pageRequest.getPageSize()) // ~개
							  .fetch();
	}
	
	public Long findbrdcnt() throws Exception {
		QBoard brd = QBoard.board;
		return jpaQueryFactory.select(brd.count())
							  .from(brd)
							  .fetchOne();
	}
	
	public List<Board> searchBrdListByPaging(PageRequest page, String word, String type) throws Exception {
		QBoard brd = QBoard.board;
		if (type.equals("subject")) {
			return jpaQueryFactory.selectFrom(brd)
					.where(brd.subject.contains(word))
					.orderBy(brd.num.desc()) // 정렬순서
					.offset(page.getOffset()) // ~부터
					.limit(page.getPageSize()) // ~개
					.fetch();
		} else if (type.equals("content")) {
			return jpaQueryFactory.selectFrom(brd)
					.where(brd.content.contains(word))
					.orderBy(brd.num.desc()) // 정렬순서
					.offset(page.getOffset()) // ~부터
					.limit(page.getPageSize()) // ~개
					.fetch();
		} else if(type.equals("writer")) {
			return jpaQueryFactory.selectFrom(brd)
					.where(brd.writer.eq(word))
					.orderBy(brd.num.desc()) // 정렬순서
					.offset(page.getOffset()) // ~부터
					.limit(page.getPageSize()) // ~개
					.fetch();
			
		} else return null;
	}
	
	public Long searchBrdCnt(String type, String word) {
		QBoard brd = QBoard.board;
		if (type.equals("subject")) {
			return jpaQueryFactory.select(brd.count())
								  .where(brd.subject.contains(word))
								  .from(brd)
								  .fetchOne();
		} else if (type.equals("content")) {
			return jpaQueryFactory.select(brd.count())
						     	  .where(brd.content.contains(word))
						     	  .from(brd)
						     	  .fetchOne();
		} else if(type.equals("writer")) {
			return jpaQueryFactory.select(brd.count())
								  .where(brd.writer.contains(word))
					              .from(brd)
					              .fetchOne();
			
		} else return 0L;
	}

	// ㅈ회수
	@Transactional
	public void setViewCnt(Integer boardNum, Integer viewCount) {
		QBoard brd = QBoard.board;
		jpaQueryFactory.update(brd)
					   .set(brd.viewCount, viewCount)
					   .where(brd.num.eq(boardNum)) // eq = 같을 때 ? 
					   .execute();
		
	}
	
	public BoardLike findBoardLike(String id, Integer num) {
		QBoardLike brdLike = QBoardLike.boardLike;
		return jpaQueryFactory.selectFrom(brdLike)
							  .where(brdLike.memberId.eq(id).and(brdLike.boardNum.eq(num)))
							  .fetchOne();
	}
	
	
}
