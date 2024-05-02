package com.kosta.univ.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	Department findByDeptno(Integer deptno);
	Department findByDName(String dname);
}
