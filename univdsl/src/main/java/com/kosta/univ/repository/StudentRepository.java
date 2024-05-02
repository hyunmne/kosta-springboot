package com.kosta.univ.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
   List<Student> findByName(String name);
   List<Student> findBydeptno1(Integer deptno1);
   List<Student> findByGrade(Integer grade);
   List<Student> findByDeptno1OrDeptno2(Integer deptno1, Integer deptno2);
   List<Student> findByProfno(Integer profno);
}