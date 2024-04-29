package com.kosta.univ.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.univ.dto.DepartmentDto;
import com.kosta.univ.dto.ProfessorDto;
import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.service.UnivService;


// 안에 있는 메소드가 전부 view로 내려보내는 것이 아님.
@RestController 
public class UnivController {
	
	@Autowired
	private UnivService univService;
	
	@PostMapping("/regDept")
	public ResponseEntity<String> regDepartment(@RequestBody DepartmentDto deptDto){
		try { // json data
			System.out.println(deptDto);
			univService.saveDepartment(deptDto);
			return new ResponseEntity<String>("부서 정상 등록", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/regStud")
	public ResponseEntity<String> regStudent(StudentDto stdDto){ // form-data
		try {
			univService.saveStudent(stdDto);
			return new ResponseEntity<String>("학생 정상 등록", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/regProf")
	public ResponseEntity<String> regProfessor(@RequestBody ProfessorDto profDto){
		try {
			univService.saveProfessor(profDto);
			return new ResponseEntity<String> ("교수 정상 등록", HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String> (e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInfo") // 애는 왜 리스트일까? 
	public ResponseEntity<List<StudentDto>> studentInfo(@RequestParam("name") String name ){
		try {
			List<StudentDto> stdDtoList  = univService.stdListByName(name);
			return new ResponseEntity<List<StudentDto>>(stdDtoList, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInfoByNo")
	public ResponseEntity<StudentDto> studentInfoByNo(@RequestParam("studno") Integer studno){
		try {
			StudentDto std = univService.stdByStudNo(studno);
			return new ResponseEntity<StudentDto>(std, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<StudentDto> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/deptInfoByName") // 학과명으로 학과 조회
	public ResponseEntity<DepartmentDto> deptInfoByName(@RequestParam("dname") String dname){
		try {
			DepartmentDto deptDto = univService.deptByDeptName(dname);
			return new ResponseEntity<DepartmentDto>(deptDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<DepartmentDto> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/deptInfoByNo") // 학과 번호로 학과 조회
	public ResponseEntity<DepartmentDto> deptInfoByNo(@RequestParam("deptno") Integer deptno) {
		try {
			DepartmentDto deptDto = univService.deptByDeptNo(deptno);
			return new ResponseEntity<DepartmentDto>(deptDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<DepartmentDto> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/deptInfoByPart") // 파트로 학과 조회
	public ResponseEntity<List<DepartmentDto>> deptInfoByPart(@RequestParam("part") String part) {
		try {
			List<DepartmentDto> deptDtoList = univService.deptListByPart(part);
			return new ResponseEntity<List<DepartmentDto>>(deptDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DepartmentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/deptInfoByBuild") // 건물로 학과 조회
	public ResponseEntity<List<DepartmentDto>> deptInfoByBuild(@RequestParam("build") String build){
		try {
			List<DepartmentDto> deptDtoList = univService.deptListByBuild(build);
			return new ResponseEntity<List<DepartmentDto>>(deptDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<DepartmentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInDeptNo") // 학과번호에 소속된 학생 조회(제1전공)
	public ResponseEntity<List<StudentDto>> studInDeptNo(@RequestParam("deptno1") Integer deptno1){
		try {
			List<StudentDto> stdDtoList = univService.stdListInDept1ByDeptNo(deptno1);
			return new ResponseEntity<List<StudentDto>>(stdDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInDeptName") // 학과명에 소속된 학생 조회 (제1전공)
	public ResponseEntity<List<StudentDto>> studInDeptName(@RequestParam("dname") String dname){
		try {
			List<StudentDto> stdDtoList = univService.stdListInDept1ByDeptName(dname);
			return new ResponseEntity<List<StudentDto>>(stdDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInDeptNo2") // 학과번호에 소속된 학생 조회(부전공)
	public ResponseEntity<List<StudentDto>> studInDeptNo2(@RequestParam("deptno2") Integer deptno2){
		try {
			List<StudentDto> stdDtoList = univService.stdListInDept2ByDeptNo(deptno2);
			return new ResponseEntity<List<StudentDto>>(stdDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/studInDeptName2") // 학과명에 소속된 학생 조회(부전공)
	public ResponseEntity<List<StudentDto>> studInDeptName2(@RequestParam("dname") String dname){
		try {
			List<StudentDto> stdDtoList = univService.stdListInDeptByDeptName(dname);
			return new ResponseEntity<List<StudentDto>>(stdDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	// 잘 안 됨 
	@GetMapping("/studInProfNo") // 교수번호에 소속된 학생 조회
	public ResponseEntity<List<StudentDto>> studInProfNo(@RequestParam("profno") Integer profno){
		try {
			List<StudentDto> stdDtoList = univService.stdListByProfNo(profno);
			System.out.println(stdDtoList);
			return new ResponseEntity<List<StudentDto>> (stdDtoList,HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
//	@GetMapping("/studInProfName") // 교수이름에 소속된 학생 조회
//	public ResponseEntity<List<StudentDto>> studInProfName(@RequestParam("name") String profName){
//		try {
//			List<StudentDto> stdDtoList = univService.stdListByNoProf()
//		} catch(Exception e) {
//			e.printStackTrace();
//			return new ResponseEntity<List<StudentDto>> (HttpStatus.BAD_REQUEST);
//		}
//	}
	
	@GetMapping("/profInfoByNo") // 교수번호로 교수 조회
	public ResponseEntity<ProfessorDto> profInfoByNo(@RequestParam("profno") Integer profno){
		try {
			ProfessorDto profDto = univService.profByProfNo(profno);
			return new ResponseEntity<ProfessorDto>(profDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<ProfessorDto> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/profInfoByName") // 교수명으로 교수 조회
	public ResponseEntity<List<ProfessorDto>> profInfoByName(@RequestParam("name") String name){
		try {
			List<ProfessorDto> profDto = univService.profListByProfName(name);
			return new ResponseEntity<List<ProfessorDto>>(profDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ProfessorDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/profInDeptNo") // 학과 번호에 소속된 교수 조회
	public ResponseEntity<List<ProfessorDto>> profInDeptno(@RequestParam("deptno") Integer deptno){
		try {
			List<ProfessorDto> profDto = univService.profListByDeptNo(deptno);
			return new ResponseEntity<List<ProfessorDto>>(profDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ProfessorDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping("/profInDeptName") // 학과 이름에 소속된 교수 조회
	public ResponseEntity<List<ProfessorDto>> profInDeptName(@RequestParam("name") String name){
		try {
			List<ProfessorDto> profDto = univService.profListByDeptName(name);
			return new ResponseEntity<List<ProfessorDto>>(profDto, HttpStatus.OK);
		} catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<List<ProfessorDto>> (HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
}
