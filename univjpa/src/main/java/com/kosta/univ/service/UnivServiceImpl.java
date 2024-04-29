package com.kosta.univ.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

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
		List<StudentDto> studDtoList = new ArrayList<>();
		List<Student> studList = studentRepository.findByName(studName);
		for (Student std : studList) {
			studDtoList.add(std.toDto());
		}
		return studDtoList;
	}

	@Override  //제1전공으로 학생목록 조회
	public List<StudentDto> stdListInDept1ByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		List<StudentDto> stdDtoList = null;
		if(odept.isPresent()) {
				stdDtoList = new ArrayList<>();
			for(Student std : odept.get().getStdList1()) {
				stdDtoList.add(std.toDto());
			}
		}
		return stdDtoList;
	}

	@Override
	public List<StudentDto> stdListInDept1ByDeptNo(Integer deptNo) throws Exception {
		List<StudentDto> stdDtoList = new ArrayList<StudentDto>();
		List<Student> stdList = studentRepository.findByDepartment1Deptno(deptNo);
		for (Student std : stdList) {
			stdDtoList.add(std.toDto());
		}
		return stdDtoList;
	}

	@Override  //제2전공으로 학생목록 조회
	public List<StudentDto> stdListInDeptByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			List<StudentDto> stdDtoList = new ArrayList<>();
			for (Student std : odept.get().getStdList2()) {
				stdDtoList.add(std.toDto());
			}
		}
		return null;
	}

	@Override
	public List<StudentDto> stdListInDept2ByDeptNo(Integer deptNo) throws Exception {
		List<StudentDto> stdDtoList = new ArrayList<StudentDto>();
		List<Student> stdList = studentRepository.findByDepartment2Deptno(deptNo);
		for (Student std : stdList) {
			stdDtoList.add(std.toDto());
		}
		return stdDtoList;
	}

	@Override  //학년으로 학생목록 조회
	public List<StudentDto> stdListByGrade(Integer grade) throws Exception {
		List<StudentDto> stdDtoList = new ArrayList<StudentDto>();
		List<Student> stdList = studentRepository.findByGrade(grade);
		for (Student std : stdList) {
			stdDtoList.add(std.toDto());
		}
		return stdDtoList;
	}

	@Override  //담당교수가 없는 학생목록 조회
	public List<StudentDto> stdListByNoProf() throws Exception {
		List<StudentDto> stdDtoList = new ArrayList<StudentDto>();
		List<Student> stdList = studentRepository.findByProfessorIsNull();
		for (Student std : stdList) {
			stdDtoList.add(std.toDto());
		}
		return stdDtoList;
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
			List<StudentDto> stdDtoList = new ArrayList<>();
			for (Student std : oprof.get().getStdList()) {
				stdDtoList.add(std.toDto());
			}
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
		List<ProfessorDto> profDtoList = new ArrayList<>();
		List<Professor> profList = professorRepository.findByName(profName);
		for(Professor prof : profList) {
			profDtoList.add(prof.toDto());
		}
		return profDtoList;
	}

	@Override // 학과번호으로 교수 리스트 조회
	public List<ProfessorDto> profListByDeptNo(Integer deptno) throws Exception {
		Optional<Department> odept = departmentRepository.findById(deptno);
		if(odept.isPresent()) {
			List<ProfessorDto> profDtoList = new ArrayList<>();
			for(Professor prof : odept.get().getProfList()) {
				profDtoList.add(prof.toDto());
			}
		}
		return null;
	}

	@Override // 학과명으로 교수 목록 조회
	public List<ProfessorDto> profListByDeptName(String deptName) throws Exception {
		Optional<Department> odept = departmentRepository.findByDname(deptName);
		if(odept.isPresent()) {
			List<ProfessorDto> profDtoList = new ArrayList<>();
			for(Professor prof : odept.get().getProfList()) {
				profDtoList.add(prof.toDto());
			}
		}
		return null;
	}

	@Override
	public List<ProfessorDto> profListByPosition(String position) throws Exception {
		List<ProfessorDto> profDtoList = new ArrayList<ProfessorDto>();
		List<Professor> profList = professorRepository.findByPosition(position);
		for(Professor prof : profList) {
			profDtoList.add(prof.toDto());
		}
		return profDtoList;
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
		List<DepartmentDto> deptDtoList = new ArrayList<>();
		List<Department> deptList = departmentRepository.findByPart(part);
		for(Department dept : deptList) {
			deptDtoList.add(dept.toDto());
		}
		return deptDtoList; 
	}

	@Override
	public List<DepartmentDto> deptListByBuild(String build) throws Exception {
		List<DepartmentDto> deptDtoList = new ArrayList<>();
		List<Department> deptList = departmentRepository.findByBuild(build);
		for(Department dept : deptList) {
			deptDtoList.add(dept.toDto());
		}
		return deptDtoList; 
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