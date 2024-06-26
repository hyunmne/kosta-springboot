package com.kosta.univ.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.kosta.univ.dto.DepartmentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	@Id
	private Integer deptno;
	@Column
	private String dname;
	@Column
	private String part;
	@Column
	private String build;
	
	@OneToMany(mappedBy="department1", fetch=FetchType.LAZY)
	private List<Student> stdList1 = new ArrayList<>();
	
	@OneToMany(mappedBy="department2", fetch=FetchType.LAZY)
	private List<Student> stdList2 = new ArrayList<>();
	
	@OneToMany(mappedBy="department", fetch=FetchType.LAZY)
	private List<Professor> profList = new ArrayList<>();

	@Override
	public String toString() {
		return "Department [deptno=" + deptno + ", dname=" + dname + ", part=" + part + ", build=" + build
				+ ", profList=" + profList + "]";
	}
	
	public DepartmentDto toDto() {
		return DepartmentDto.builder()
				.deptno(deptno)
				.dname(dname)
				.part(part)
				.build(build)
				.build();
	}
}
