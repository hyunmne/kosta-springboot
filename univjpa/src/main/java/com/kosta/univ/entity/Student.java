package com.kosta.univ.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import com.kosta.univ.dto.StudentDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {
	@Id
	private Integer studno;
	
	@Column
	private String name;
	@Column
	private String id;
	@Column
	private Integer grade;
	@Column
	private String jumin;
	@Column
	private Date birthday;
	@Column
	private String tel;
	@Column
	private Integer height;
	@Column
	private Integer weight;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="profno")
	private Professor professor;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="deptno1")
	private Department department1;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="deptno2")
	private Department department2;

	@Override
	public String toString() {
		return "Student [studno=" + studno + ", name=" + name + ", id=" + id + ", grade=" + grade + ", jumin=" + jumin
				+ ", birthday=" + birthday + ", tel=" + tel + ", height=" + height + ", weight=" + weight + "]";
	}
	
	public StudentDto toDto() {
		StudentDto studDto = StudentDto.builder()
				.studno(studno)
				.name(name)
				.id(id)
				.grade(grade)
				.jumin(jumin)
				.birthday(birthday)
				.tel(tel)
				.height(height)
				.weight(weight)
				.build();

		if(professor!=null) {
			studDto.setProfno(professor.getProfno());
			studDto.setProfName(professor.getName());
		}
		if(department1!=null) {
			studDto.setDeptno1(department1.getDeptno());
			studDto.setDept1Name(department1.getDname());
		}
		if(department2!=null) {
			studDto.setDeptno2(department2.getDeptno());
			studDto.setDept2Name(department2.getDname());
		}
		
		return studDto;
	}

	
}
