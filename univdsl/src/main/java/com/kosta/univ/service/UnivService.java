package com.kosta.univ.service;

import java.util.List;
import java.util.Map;

import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.ProfessorDto;
import com.kosta.univ.dto.StudentDto;

public interface UnivService {
	// 학생 =================================================
	// 학생 입학
	void enterStudent(StudentDto std) throws Exception;
	// 학번오르 학생정보 조회
	StudentDto getStdByNo(Integer stdno) throws Exception;
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
	
	
	
	// 교수 =================================================
	// 교수 입사
	void enterProfessor(ProfessorDto prof) throws Exception;
	// 교수번호로 교수정보 조회
	ProfessorDto getProfByProfNo(Integer profno) throws Exception;
	// 교수명으로 교수정보 조회
	List<ProfessorDto> getProfByProfName(String name) throws Exception;
	// 교수번호로 교수정보 조회(학과명포함)
	Map<String,Object> getProfByProfNoWithDeptNo(Integer profno) throws Exception;
	// 학생의 담당교수 조회
	ProfessorDto getProfByStudno(Integer studno) throws Exception;
	// 특정학과 교수정보 조회(학과번호로)
	List<ProfessorDto> getProfListByDeptno(Integer deptno) throws Exception; 
	// 특정학과 교수정보 조회(학과명으로)
	List<ProfessorDto> getProfListByDName(String dname) throws Exception; 
	
	
	
	// 학과 =================================================
	// 학과신설
	void foundDepartment(DepartmentDto dept) throws Exception;
	// 학과번호로 학과 조회
	DepartmentDto getDeptByDeptno(Integer deptno) throws Exception;
	// 학과명으로 학과 조회
	DepartmentDto getDeptByDName(String dname) throws Exception;
	// 학번으로 학과 조회
	DepartmentDto getDeptByStudno(Integer studno) throws Exception;
	// 건물로 학과 조회
	List<DepartmentDto> getDeptByBuild(String build) throws Exception;
}
