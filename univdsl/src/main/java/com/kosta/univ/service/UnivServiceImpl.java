package com.kosta.univ.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;
import com.kosta.univ.repository.UnivDslRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnivServiceImpl implements UnivService {

	private final DepartmentRepository deptres;
	private final ProfessorRepository profres;
	private final StudentRepository stdres;
	private final UnivDslRepository univres;
	
	@Override
	public void enterStudent(StudentDto std) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student getStdByNo(Integer stdno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getStdByNoWithDName(Integer stdno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getStdByNoWithProfName(Integer stdno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getStdByNoWithDNameProfName(Integer stdno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByName(String name) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDeptno(Integer deptno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDName(String dname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByGrade(Integer grade) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByGradeAndDName(Integer grade, String dname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDeptNo1Or2(Integer deptno1, Integer deptno2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDeptNo1Or2(Integer deptno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDName1Or2(String dname1, String dname2) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByDName1Or2(String dname) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentDto> getStdListByProfNo(Integer profno) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
