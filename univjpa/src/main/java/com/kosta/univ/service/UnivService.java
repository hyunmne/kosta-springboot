package com.kosta.univ.service;

import java.util.List;

import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;

public interface UnivService {

	// 뷰서 등록
	void saveDepartment(DepartmentDto deptDto) throws Exception;
	void saveStudent(StudentDto stdDto) throws Exception;
	
	// 학생
	// 학생 이름으로 학생 목록 조회
	List<Student> stdListByName(String studName) throws Exception;
	// 제 1전공으로 학생 목록 조회
	List<Student> stdListInDept1ByDeptName(String dept1) throws Exception;
	List<Student> stdListInDept1ByDeptNo(Integer dept1) throws Exception;
	// 제 2전공으로 학생 목록 조회
	List<Student> stdListInDeptByDeptName(String dept2) throws Exception;
	List<Student> stdListInDept2ByDeptNo(Integer dept2) throws Exception;
	// 학년으로 학생 목록 조회
	List<Student> stdListByGrade(Integer grade) throws Exception;
	// 담당 교수가 없는 학생 목록 조회
	List<Student> stdListByNoProf() throws Exception;
	// 학번으로 학생 조회
	Student stdByStudNo(Integer studNo) throws Exception;
	// 주민번호로 학생 조회
	Student stdByJumin(String jumin) throws Exception;
	
	
	// 교수
	// 교수 번호로 담당 학생 목록 조회
	List<Student> stdListByProfNo(Integer profNo) throws Exception;
	//교수번호로 교수 조회
	Professor profByProfNo(Integer profNo) throws Exception;
	// 교수 이름으로 교수 목록 조회
	List<Professor> profListByProfName(String profName) throws Exception;
	// 학과 번호로 교수목록 조회
	List<Professor> profListByDeptNo(Integer deptno) throws Exception;
	// 학과 이름으로 교수 목록 조회
	List<Professor> profListByDeptName(String deptName) throws Exception;
	// 직급으로 교수 목록 조회
	List<Professor> profListByPosition(String position) throws Exception;
	
	// 학과
	// 학과 번호로 학과 조회
	Department deptByDeptNo(Integer deptNo) throws Exception;
	// 학과 이름으르로 학과 조회
	Department deptByDeptName(String dpetName) throws Exception;
	// 학과 편집
	List<Department> deptListByPart(String part) throws Exception;
	// 위치하는 건물로 학과 목록 조회
	List<Department> deptListByBuild(String build) throws Exception;
	
	
}
