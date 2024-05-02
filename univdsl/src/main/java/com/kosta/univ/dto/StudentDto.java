package com.kosta.univ.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
	private Integer studno;
	private String name;
	private String id;
	private Integer grade;
	private String jumin;
	private Date birthday;
	private String tel;
	private Integer height;
	private Integer weight;
	private Integer deptno1;
	private Integer deptno2;
	private Integer profno;
}
