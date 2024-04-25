package com.kosta.board.dto;

import java.sql.Date;

import com.kosta.board.entity.Board;
import com.kosta.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class BoardDto {
	private Integer num;
	private String subject;
	private String content;
	private Integer fileNum;
	private Integer viewCount;
	private Integer likeCount;
	private Date writeDate;
	private Date modifyDate;
	private String writer;
	private String nickname;
	
	
	// 객체 생성 : 원하는  파라미터만 넣을 수 있음..
	// boardDto를 board로 바꿔주는 메소드 (왜바꾸지 굳
	public Board toBoard() {
		return Board.builder()
				.num(num)
				.subject(subject)
				.content(content)
				.fileNum(fileNum)
				.viewCount(viewCount)
				.likeCount(likeCount)
				.writeDate(writeDate)
				.modifyDate(modifyDate)
				.member(Member.builder().id(writer).build())
				.build();
	}
}
