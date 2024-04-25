package com.kosta.board.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="FILE") // DB에 저장될 테이블 이름 설정
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BFile {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;
	@Column
	private String directory;
	@Column
	private String name;
	@Column
	private long size;
	@Column
	private String contenttype;
	
	@Column
	@CreationTimestamp
	private Date uploaddate;
}
