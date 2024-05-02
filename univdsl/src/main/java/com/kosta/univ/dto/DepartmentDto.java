package com.kosta.univ.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepartmentDto {
	private Integer deptno;
	private String dname;
	private String part;
	private String build;
	
}
