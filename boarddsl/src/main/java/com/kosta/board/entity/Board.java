package com.kosta.board.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import com.kosta.board.dto.BoardDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@DynamicInsert  // column default랑 같이 써줘야 함
public class Board {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increments
	private Integer num;
	
	@Column
	private String subject;
	@Column
	private String content;
	@Column
	private Integer fileNum;
	
	@Column
	@ColumnDefault("0") // default 값 지정
	private Integer viewCount;
	
	@Column
	@ColumnDefault("0")
	private Integer likeCount;
	
	@Column
	@CreationTimestamp // = curdate()
	private Date writeDate;
	
	@Column
	@UpdateTimestamp // 수정 날짜? 
	private Date modifyDate;
	
	@Column
	private String writer;
	
	@OneToMany(mappedBy="board",  fetch = FetchType.LAZY)
	private List<BoardLike> boardLikeList = new ArrayList<>();
	
	public BoardDto toBoardDto() {
		return BoardDto.builder()
				.num(num)
				.subject(subject)
				.content(content)
				.fileNum(fileNum)
				.viewCount(viewCount)
				.likeCount(likeCount)
				.writeDate(writeDate)
				.modifyDate(modifyDate)
				.writer(writer)
				.build();
				
	}
}
