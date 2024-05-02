package com.kosta.univ.service;

import java.util.List;
import java.util.Map;

import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.entity.Student;

public interface UnivService {
	// 학생 =================================================
	// 학생 입학
	void enterStudent(StudentDto std) throws Exception;
	// 학번오르 학생정보 조회
	Student getStdByNo(Integer stdno) throws Exception;
	// 학번으로 학생정보 조회(학과명 포함)
	Map<String,Object> getStdByNoWithDName(Integer stdno) throws Exception;
	// 학번으로 학생정보 조회(담당교수명 포함)
	Map<String,Object> getStdByNoWithProfName(Integer stdno) throws Exception;
	// 학번으로 학생정보 조회(학과명, 담당교수명 포함)
	Map<String,Object> getStdByNoWithDNameProfName(Integer stdno) throws Exception;
	
	// 학생이름으로 학생정보 조회
	List<StudentDto> getStdListByName(String name) throws Exception;
	// 특정학과 학생 조회(학과 번호로)
	List<StudentDto> getStdListByDeptno(Integer deptno) throws Exception;
	// 특정학과 학생 조회(학과명으로)
	List<StudentDto> getStdListByDName(String dname) throws Exception;
	
	// 특정학년 학생 조회
	List<StudentDto> getStdListByGrade(Integer grade) throws Exception;
	// 특정학년, 학과 학생 조회
	List<StudentDto> getStdListByGradeAndDName(Integer grade, String dname) throws Exception;
	
	// 주전공이 deptno1 이거나 부전공이 deptno2인 학생 조회
	List<StudentDto>  getStdListByDeptNo1Or2(Integer deptno1, Integer deptno2) throws Exception;
	// deptno1이나 dpetno2가 특정학과인 학생 조회(학과번호로)
	List<StudentDto>  getStdListByDeptNo1Or2(Integer deptno) throws Exception;
	// 주전공이 dname1 이거나 부전공이 dname2인 학생 조회
	List<StudentDto>  getStdListByDName1Or2(String dname1, String dname2 ) throws Exception;
	// deptno1이나 dpetno2가 특정학과인 학생 조회(학과명으로)
	List<StudentDto>  getStdListByDName1Or2(String dname) throws Exception;
	// 특정 교수가 담당하는 학생목록 조회
	List<StudentDto>  getStdListByProfNo(Integer profno) throws Exception;
	
}
