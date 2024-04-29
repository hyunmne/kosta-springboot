package com.kosta.univ;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;

@SpringBootTest
class UnivjpaApplicationTests {
	
	@Autowired
	private DepartmentRepository deptRepository;
	
	@Autowired
	private ProfessorRepository profRepository;
	
	@Autowired
	private StudentRepository studRepository;

	@Test
	void insertDepartment() {
		Department dept = Department.builder()
							.deptno(100)
							.dname("컴퓨터공학부")
							.part("공과대학")
							.build("호서대베처타워")
							.build();
		deptRepository.save(dept);
	}
	
	@Test
	void insertProfessor() {
		Professor prof = Professor.builder()
							.profno(1001)
							.name("조인형")
							.id("captain")
							.position("정교수")
							.pay(550)
							.hiredate(Date.valueOf("1990-06-23"))
							.bonus(100)
							.department(Department.builder().deptno(100).build())
							.email("captain@abc.com")
							.hpage("http://www.abc.com")
							.build();
		
		profRepository.save(prof);
	}
	
	@Test
	void insertStudent() {
		Student stud = Student.builder()
						.studno(9411)
						.name("서재수")
						.id("pooh94")
						.grade(4)
						.jumin("7502241128467")
						.birthday(Date.valueOf("1995-02-24"))
						.tel("051)426-1700")
						.height(172)
						.weight(64)
						.department1(Department.builder().deptno(100).build())
						.department2(Department.builder().deptno(201).build())
						.professor(Professor.builder().profno(1001).build())
						.build();
		studRepository.save(stud);
	}
	
	// 제 1 전공이 컴퓨터공학부인 학생을 조회하시오.
	@Test
	void selectDeptByDname() {
		Optional<Department> odept = deptRepository.findByDname("컴퓨터공학부");
		if(odept.isPresent()) {
			System.out.println(odept.get());
			System.out.println(odept.get().getStdList1());
		}
	}
	
	// 제 2 전공이 전자공학과인 학생을 조회하시오.
	@Test
	void selectDeptByDname2() {
		Optional<Department> odept2 = deptRepository.findByDname("전자공학과");
		if(odept2.isPresent()) {
			System.out.println(odept2.get());
			System.out.println(odept2.get().getStdList2());
		}
	}
	
	// 조인형 교수 정보 & 이 교수를 담당교수로 하는 학생 목록 조회
	@Test
	void selectStdListByProfName() {
		Optional<Professor> oprof = profRepository.findByName("조인형");
		if(oprof.isPresent()) {
			System.out.println(oprof.get());
			System.out.println(oprof.get().getStdList());
		}
	}
	

}
