package com.kosta.univ.dto;

import java.sql.Date;

import org.modelmapper.ModelMapper;

import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfessorDto {
	private Integer profno;
	private String name;
	private String id;
	private String position;
	private Integer pay;
	private Date hiredate;
	private Integer bonus;
	private String email;
	private String hpage;
	private Integer deptno;
	private String deptName;
	
	public Professor toEntity() {
		ModelMapper mapper = new ModelMapper();
		Professor prof = mapper.map(this, Professor.class); // 이름 같은 것들 mapping 시켜줌.. 
		prof.setDepartment(Department.builder().deptno(deptno).build());
		return prof;
	}
}
