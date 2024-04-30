package com.kosta.univ.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.kosta.univ.dto.ConvertMapper;
import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.ProfessorDto;
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
	public List<StudentDto> stdListByName(String studName) throws Exception {
		List<Student> studList = studentRepository.findByName(studName);
		return ConvertMapper.stdListToStdDtoList(studList);
	}

	@Override  //제1전공으로 학생목록 조회
	public List<StudentDto> stdListInDept1ByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			return ConvertMapper.stdListToStdDtoList(odept.get().getStdList1());
		}
		return null;
	}

	@Override
	public List<StudentDto> stdListInDept1ByDeptNo(Integer deptNo) throws Exception {
		List<Student> stdList = studentRepository.findByDepartment1Deptno(deptNo);
		return ConvertMapper.stdListToStdDtoList(stdList);
	}

	@Override  //제2전공으로 학생목록 조회
	public List<StudentDto> stdListInDept2ByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			List<Student> stdList = odept.get().getStdList2();
			return ConvertMapper.stdListToStdDtoList(stdList);
		}
		return null;
	}

	@Override
	public List<StudentDto> stdListInDept2ByDeptNo(Integer deptNo) throws Exception {
		List<Student> stdList = studentRepository.findByDepartment2Deptno(deptNo);
		return ConvertMapper.stdListToStdDtoList(stdList);
	}

	@Override  //학년으로 학생목록 조회
	public List<StudentDto> stdListByGrade(Integer grade) throws Exception {
		List<Student> stdList = studentRepository.findByGrade(grade);
		return ConvertMapper.stdListToStdDtoList(stdList);
	}

	@Override  //담당교수가 없는 학생목록 조회
	public List<StudentDto> stdListByNoProf() throws Exception {
		List<Student> stdList = studentRepository.findByProfessorIsNull();
		return ConvertMapper.stdListToStdDtoList(stdList);
	}

	@Override  //학번으로 학생 조회
	public StudentDto stdByStudNo(Integer studno) throws Exception {
		Optional<Student> ostud = studentRepository.findById(studno);
		if(ostud.isPresent()) return ostud.get().toDto();
		return null;
	}

	@Override  //주민번호로 학생 조회
	public StudentDto stdByJumin(String jumin) throws Exception {
		Optional<Student> ostud = studentRepository.findByJumin(jumin);
		if(ostud.isPresent()) return ostud.get().toDto();
		return null;
	}

	@Override // 교수 번호로 학생 목록 조회
	public List<StudentDto> stdListByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprof = professorRepository.findById(profNo);
		if(oprof.isPresent()) {
			List<Student> stdList = oprof.get().getStdList();
			return ConvertMapper.stdListToStdDtoList(stdList);
		}
		return null;
	}

	@Override
	public ProfessorDto profByProfNo(Integer profNo) throws Exception {
		Optional<Professor> oprof = professorRepository.findById(profNo);
		if(oprof.isPresent()) return oprof.get().toDto();
		return null;
	}

	@Override
	public List<ProfessorDto> profListByProfName(String profName) throws Exception {
		List<Professor> profList = professorRepository.findByName(profName);
		return ConvertMapper.profListToProfDtoList(profList);
	}

	@Override // 학과번호으로 교수 리스트 조회
	public List<ProfessorDto> profListByDeptNo(Integer deptno) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptno);
		if(odept.isPresent()) {
			List<Professor> profList = odept.get().getProfList();
			return ConvertMapper.profListToProfDtoList(profList);
		}
		return null;
	}

	@Override // 학과명으로 교수 목록 조회
	public List<ProfessorDto> profListByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			List<Professor> profList =odept.get().getProfList();
			return ConvertMapper.profListToProfDtoList(profList);
		}
		return null;
	}

	@Override
	public List<ProfessorDto> profListByPosition(String position) throws Exception {
		List<Professor> profList = professorRepository.findByPosition(position);
		return ConvertMapper.profListToProfDtoList(profList);
	}

	@Override
	public DepartmentDto deptByDeptNo(Integer deptNo) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptNo);
		if(odept.isPresent()) return odept.get().toDto();
		return null;
	}

	@Override
	public DepartmentDto deptByDeptName(String dpetName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(dpetName);
		if(odept.isPresent()) return odept.get().toDto();
		return null;
	}

	@Override
	public List<DepartmentDto> deptListByPart(String part) throws Exception {
		List<Department> deptList = departmentRepository.findByPart(part);
		return ConvertMapper.deptListToDeptDtoList(deptList);
	}

	@Override
	public List<DepartmentDto> deptListByBuild(String build) throws Exception {
		List<Department> deptList = departmentRepository.findByBuild(build);
		return ConvertMapper.deptListToDeptDtoList(deptList);
	}

	@Override
	public void saveDepartment(DepartmentDto deptDto) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptDto.getDeptno());
		if (odept!=null) throw new Exception("등록된 학과번호입니다.");
		departmentRepository.save(deptDto.toEntity());
	}
	
	@Override
	public void saveStudent(StudentDto stdDto) throws Exception {
		Optional<Student> ostd = studentRepository.findById(stdDto.getStudno());
		if (ostd!=null) throw new Exception("등록된 학번입니다.");
		studentRepository.save(stdDto.toEntity());
	}

	@Override
	public void saveProfessor(ProfessorDto profDto) throws Exception {
		Optional<Professor> oprof = professorRepository.findById(profDto.getProfno());
		if(oprof!=null)  throw new Exception("등록된 교수번호입니다.");
		professorRepository.save(profDto.toEntity());
	}

}