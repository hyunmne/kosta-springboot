package com.kosta.univ.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer deptno;
	@Column
	private String dname;
	@Column
	private Integer part;
	@Column
	private String build;
	
	@OneToMany(mappedBy="department1", fetch=FetchType.LAZY)
	private List<Student> stdList1 = new ArrayList<>();
	
	@OneToMany(mappedBy="department2", fetch=FetchType.LAZY)
	private List<Student> stdList2 = new ArrayList<>();
	
	@OneToMany(mappedBy="department", fetch=FetchType.LAZY)
	private List<Professor> profList = new ArrayList<>();
}
