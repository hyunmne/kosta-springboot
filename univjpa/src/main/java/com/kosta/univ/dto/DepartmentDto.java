package com.kosta.univ.dto;

import lombok.Setter;
import lombok.ToString;

import com.kosta.univ.entity.Department;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {
	private Integer deptno;
	private String dname;
	private String part;
	private String build;
	
	public Department toEntity() {
		return Department.builder()
					.deptno(deptno)
					.dname(dname)
					.part(part)
					.build(build)
					.build();
	}
}
