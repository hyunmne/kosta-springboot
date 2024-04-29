package com.kosta.univ.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UnivServiceImpl implements UnivService {

	private final DepartmentRepository departmentRepository; 

	private final ProfessorRepository professorRepository;

	private final StudentRepository studentRepository;

	@Override  //학생 이름으로 학생목록 조회
	public List<Student> stdListByName(String studName) throws Exception {
		return studentRepository.findByName(studName);
	}

	@Override  //제1전공으로 학생목록 조회
	public List<Student> stdListInDept1ByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			return odept.get().getStdList1();
		}
		return null;
	}

	@Override
	public List<Student> stdListInDept1ByDeptNo(Integer deptNo) throws Exception {
		return studentRepository.findByDepartment1Deptno(deptNo);
	}

	@Override  //제2전공으로 학생목록 조회
	public List<Student> stdListInDeptByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			return odept.get().getStdList2();
		}
		return null;
	}

	@Override
	public List<Student> stdListInDept2ByDeptNo(Integer deptNo) throws Exception {
		return studentRepository.findByDepartment2Deptno(deptNo);
	}

	@Override  //학년으로 학생목록 조회
	public List<Student> stdListByGrade(Integer grade) throws Exception {
		return studentRepository.findByGrade(grade);
	}

	@Override  //담당교수가 없는 학생목록 조회
	public List<Student> stdListByNoProf() throws Exception {
		return studentRepository.findByProfessorIsNull();
	}

	@Override  //학번으로 학생 조회
	public Student stdByStudNo(Integer studno) throws Exception {
		Optional<Student> ostud = studentRepository.findById(studno);
		if(ostud.isPresent()) return ostud.get();
		return null;
	}

	@Override  //주민번호로 학생 조회
	public Student stdByJumin(String jumin) throws Exception {
		Optional<Student> ostud = studentRepository.findByJumin(jumin);
		if(ostud.isPresent()) return ostud.get();
		return null;
	}

	@Override // 교수 번호로 학생 목록 조회
	public List<Student> stdListByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprof = professorRepository.findById(profNo);
		if(oprof.isPresent()) {
			return oprof.get().getStdList();
		}
		return null;
	}

	@Override
	public Professor profByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprof = professorRepository.findById(profNo);
		if(oprof.isPresent()) return oprof.get();
		return null;
	}

	@Override
	public List<Professor> profListByProfName(String profName) throws Exception {
		return professorRepository.findByName(profName);
	}

	@Override
	public List<Professor> profListByDeptNo(Integer deptno) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptno);
		if(odept.isPresent()) {
			return odept.get().getProfList();
		}
		return null;
	}

	@Override
	public List<Professor> profListByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			return odept.get().getProfList();
		}
		return null;
	}

	@Override
	public List<Professor> profListByPosition(String position) throws Exception {
		return professorRepository.findByPosition(position);
	}

	@Override
	public Department deptByDeptNo(Integer deptNo) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptNo);
		if(odept.isPresent()) return odept.get();
		return null;
	}

	@Override
	public Department deptByDeptName(String dpetName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(dpetName);
		if(odept.isPresent()) return odept.get();
		return null;
	}

	@Override
	public List<Department> deptListByPart(String part) throws Exception {
		return departmentRepository.findByPart(part);
	}

	@Override
	public List<Department> deptListByBuild(String build) throws Exception {
		return departmentRepository.findByBuild(build);
	}

	@Override
	public void saveDepartment(DepartmentDto deptDto) throws Exception {
		System.out.println(deptDto);
		Department dept = deptByDeptNo(deptDto.getDeptno());
		if (dept!=null) throw new Exception("등록된 학과번호입니다.");
		departmentRepository.save(deptDto.toEntity());
	}
	
	@Override
	public void saveStudent(StudentDto stdDto) throws Exception {
		Student std = stdByStudNo(stdDto.getStudno());
		if (std!=null) throw new Exception("등록된 학번입니다.");
		studentRepository.save(stdDto.toEntity());
	}

}