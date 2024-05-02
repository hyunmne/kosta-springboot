package com.kosta.univ.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Professor {
	@Id
	private Integer profno;
	@Column
	private String name;
	@Column
	private String id;
	@Column
	private String email;
	@Column
	private Date hiredate;
	@Column
	private String position;
	@Column
	private Integer pay;
	@Column
	private Integer bonus;
	@Column
	private String hpage;
	@Column
	private Integer deptno;
	
}
