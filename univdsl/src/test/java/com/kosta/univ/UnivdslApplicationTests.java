package com.kosta.univ;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kosta.univ.dto.StudentDto;
import com.kosta.univ.service.UnivService;

@SpringBootTest
class UnivdslApplicationTests {

	@Autowired
	private UnivService uniservice;
	
	@Test
	void getStdListByName() {
		try {
			List<StudentDto> stdDto = uniservice.getStdListByName("서재수");
			System.out.println(stdDto);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	void getStdListByDeptNo1Or2() {
		try {
			List<StudentDto> stdDto = uniservice.getStdListByDeptNo1Or2(100);
			System.out.println(stdDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void getStdListByDName1Or2() {
		try {
			List<StudentDto> stdDto = uniservice.getStdListByDName1Or2("컴퓨터공학부", "전자공학과");
			System.out.println(stdDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void getStdListByDName1Or2_2() {
		try {
			List<StudentDto> stdDto = uniservice.getStdListByDName1Or2("컴퓨터공학부");
			System.out.println(stdDto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	void getProfByProfNoWithDeptNo() {
		try {
			Map<String, Object> map = uniservice.getProfByProfNoWithDeptNo(101);
			System.out.println(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
