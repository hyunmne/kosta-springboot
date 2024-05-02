package com.kosta.univ.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.ProfessorDto;
import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.entity.Department;
import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.Student;
import com.kosta.univ.repository.DepartmentRepository;
import com.kosta.univ.repository.ProfessorRepository;
import com.kosta.univ.repository.StudentRepository;
import com.kosta.univ.repository.UnivDslRepository;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnivServiceImpl implements UnivService {

	private final DepartmentRepository deptres;
	private final ProfessorRepository profres;
	private final StudentRepository stdres;
	private final UnivDslRepository univres;
	private final ObjectMapper objectMapper;
	private final ModelMapper modelMapper;
	
	@Override
	public void enterStudent(StudentDto std) throws Exception {
		Optional<Student> ostd = stdres.findById(std.getStudno());
		if(ostd.isPresent()) throw new Exception("학생번호 중복 오류");
		stdres.save(modelMapper.map(std, Student.class)); // StudentDto => Student
	}

	@Override
	public StudentDto getStdByNo(Integer stdno) throws Exception {
		Optional<Student> ostd = stdres.findById(stdno);
		if(ostd.isEmpty()) throw new Exception("학생번호 오류");
		return modelMapper.map(ostd.get(), StudentDto.class); // Student => StudentDto
	}

	@Override
	public Map<String, Object> getStdByNoWithDName(Integer stdno) throws Exception {
		Tuple tuple = univres.findStdWithDNameByStdno(stdno);
		Student std = tuple.get(0, Student.class);
		String dname = tuple.get(1, String.class); // tuple의 1번째
		
		Map<String, Object> map = objectMapper.convertValue(std, Map.class); // 값변환?
		// 속성을 키로 매핑을 값으로
		map.put("dname", dname);
		
		return map;
	}

	@Override
	public Map<String, Object> getStdByNoWithProfName(Integer stdno) throws Exception {
		Tuple tuple = univres.findStdWithProfNameByStudno(stdno);
		Student std = tuple.get(0, Student.class);
		String profName = tuple.get(1, String.class);
		
		Map<String,Object> map = objectMapper.convertValue(std, Map.class);
		map.put("name", profName);
		
		return map;
	}

	@Override
	public Map<String, Object> getStdByNoWithDNameProfName(Integer stdno) throws Exception {
		Tuple tuple = univres.findStdWithDNameProfName(stdno);
		Student std = tuple.get(0, Student.class);
		String profName = tuple.get(1, String.class);
		String dName = tuple.get(2, String.class);
		
		Map<String,Object> map = objectMapper.convertValue(std, Map.class);
		map.put("name", profName);
		map.put("dname", dName);
		
		return map;
	}

	@Override
	public List<StudentDto> getStdListByName(String name) throws Exception {
	    List<Student> stdList = stdres.findByName(name);
	    return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}


	@Override
	public List<StudentDto> getStdListByDeptno(Integer deptno) throws Exception {
		List<Student> stdList = stdres.findBydeptno1(deptno);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByDName(String dname) throws Exception {
		List<Student> stdList = univres.findStdListByDName(dname);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByGrade(Integer grade) throws Exception {
		List<Student> stdList = stdres.findByGrade(grade);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByGradeAndDName(Integer grade, String dname) throws Exception {
		List<Student> stdList = univres.findStdListByGradeAndDName(grade,dname);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByDeptNo1Or2(Integer deptno1, Integer deptno2) throws Exception {
		List<Student> stdList = stdres.findByDeptno1OrDeptno2(deptno1, deptno2);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByDeptNo1Or2(Integer deptno) throws Exception {
		List<Student> stdList = univres.findByDeptno1OrDeptno2(deptno);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByDName1Or2(String dname1, String dname2) throws Exception {
		List<Student> stdList = univres.findStdByDName1Or2(dname1, dname2);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByDName1Or2(String dname) throws Exception {
		List<Student> stdList = univres.findStdByDName1Or2(dname);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}

	@Override
	public List<StudentDto> getStdListByProfNo(Integer profno) throws Exception {
		List<Student> stdList = stdres.findByProfno(profno);
		return stdList.stream()
	    		.map(stud->modelMapper.map(stud, StudentDto.class))
	    		.collect(Collectors.toList());
	}
	
	@Override
	public void enterProfessor(ProfessorDto prof) throws Exception {
		Optional<Professor> oprof = profres.findById(prof.getProfno());
		if(oprof.isPresent()) throw new Exception("교수번호 중복 오류");
		stdres.save(modelMapper.map(oprof, Student.class)); // StudentDto => Student
	}
	
	@Override // 교수번호로 교수정보 조회
	public ProfessorDto getProfByProfNo(Integer profno) throws Exception {
		Optional<Professor> oprof = profres.findById(profno);
		if(oprof.isEmpty()) throw new Exception("학생번호 오류");
		return modelMapper.map(oprof.get(), ProfessorDto.class);
	}
	
	@Override // 교수명으로 교수정보 조회
	public List<ProfessorDto> getProfByProfName(String name) throws Exception {
		List<Professor> profList = profres.findByName(name);
		return profList.stream()
				.map(prof->modelMapper.map(prof, ProfessorDto.class))
				.collect(Collectors.toList());
	}
	
	@Override // 교수번호로 교수정보 조회(학과명포함)
	public Map<String,Object> getProfByProfNoWithDeptNo(Integer profno) throws Exception {
		Tuple tuple = univres.findProfByProfnoWithDeptno(profno);
		Professor prof = tuple.get(0, Professor.class);
		String dname = tuple.get(1, String.class);
		
		Map<String,Object> map = objectMapper.convertValue(prof, Map.class);
		map.put("dname", dname);
		
		return map;
	}
	
	@Override // 학생의 담당교수 조회
	public ProfessorDto getProfByStudno(Integer studno) throws Exception {
		Professor prof = univres.findProfByStudno(studno);
		return modelMapper.map(prof, ProfessorDto.class); 
	}
	
	@Override // 특정학과 교수정보 조회(학과번호로)
	public List<ProfessorDto> getProfListByDeptno(Integer deptno) throws Exception {
		List<Professor> profList = profres.findByDeptno(deptno);
		return profList.stream()
				.map(prof->modelMapper.map(prof, ProfessorDto.class))
				.collect(Collectors.toList());
	} 
	
	@Override // 특정학과 교수정보 조회(학과명으로)
	public List<ProfessorDto> getProfListByDName(String dname) throws Exception {
		List<Professor> profList = univres.findProfListByDName(dname);
		
		return profList.stream()
				.map(prof->modelMapper.map(prof, ProfessorDto.class))
				.collect(Collectors.toList());
	} 
	
	
	
	// 학과 =================================================
	@Override // 학과신설
	public void foundDepartment(DepartmentDto dept) throws Exception {
		Optional<Department> odept = deptres.findById(dept.getDeptno());
		if(odept.isPresent()) throw new Exception("학과번호 중복 오류");
		stdres.save(modelMapper.map(dept, Student.class)); // StudentDto => Student
	}
	
	@Override // 학과번호로 학과 조회
	public DepartmentDto getDeptByDeptno(Integer deptno) throws Exception {
		Department dept = deptres.findByDeptno(deptno);
		return modelMapper.map(dept, DepartmentDto.class);
	}
	
	@Override // 학과명으로 학과 조회
	public DepartmentDto getDeptByDName(String dname) throws Exception {
		Department dept = deptres.findByDName(dname);
		return modelMapper.map(dept, DepartmentDto.class);
	}
	
	@Override // 학번으로 학과 조회
	public DepartmentDto getDeptByStudno(Integer studno) throws Exception {
//		Department dept = univres.findDeptByStudno(studno);
		
		return null;
	}
	
	@Override // 건물로 학과 조회
	public List<DepartmentDto> getDeptByBuild(String build) throws Exception {
		return null;
	}
	

}
