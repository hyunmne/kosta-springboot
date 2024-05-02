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
public class ProfessorDto {
	private Integer profno;
	private String name;
	private String id;
	private String email;
	private Date hiredate;
	private String position;
	private Integer pay;
	private Integer bonus;
	private String hpage;
	private Integer deptno;
}
