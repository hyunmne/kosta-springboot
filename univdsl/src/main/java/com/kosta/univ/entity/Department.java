package com.kosta.univ.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Department {
	@Id
	private Integer deptno;
	@Column
	private String dname;
	@Column
	private String part;
	@Column
	private String build;
}
