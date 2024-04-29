package com.kosta.univ.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kosta.univ.dto.DepartmentDto;


// 안에 있는 메소드가 전부 view로 내려보내는 것이 아님.
@RestController 
public class DepartmentController {
	
	@PostMapping("/regDept")
	public ResponseEntity<String> regDepartment(@RequestBody DepartmentDto deptDto){
		System.out.println(deptDto);
		return new ResponseEntity<String>("데이터 정상 전송", HttpStatus.OK);
		
	}
}
