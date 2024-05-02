package com.kosta.univ.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kosta.univ.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
   List<Student> findByName(String name);
   List<Student> findByDepartment1Deptno(Integer deptno1);
   List<Student> findByDepartment2Deptno(Integer deptno2);
   List<Student> findByGrade(Integer grade);
   List<Student> findByProfessorIsNull();
//   List<Student> findByProfessorProfno(Integer profNo);
//   List<Student> findByProfessorName(String profName);
   Optional<Student> findByJumin(String jumin);
}