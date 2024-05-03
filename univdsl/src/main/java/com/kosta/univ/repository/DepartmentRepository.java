package com.kosta.univ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer>{
	Department findByDeptno(Integer deptno);
	Department findByDname(String dname);
	List<Department> findByBuild(String build);
}
