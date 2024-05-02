package com.kosta.univ.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.univ.entity.Professor;
import com.kosta.univ.entity.QDepartment;
import com.kosta.univ.entity.QProfessor;
import com.kosta.univ.entity.QStudent;
import com.kosta.univ.entity.Student;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class UnivDslRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	public Tuple findStdWithDNameByStdno(Integer stdno) {
		QStudent qstd = QStudent.student;
		QDepartment qdept = QDepartment.department;
		
		return jpaQueryFactory.select(qstd, qdept.dname) 			// 학생 전체와 학과명만 가져옴
				              .from(qstd) 							// from student std
				              .join(qdept)							// join department dept	
				              .on(qstd.deptno1.eq(qdept.deptno))  	// on std.deptno1 = dept.deptno
				              .where(qstd.studno.eq(stdno))      	// where std.stdno = ${studno}
				              .fetchOne();
	}
	
	public Tuple findStdWithProfNameByStudno(Integer stdno) {
		QStudent qstd = QStudent.student;
		QProfessor qprof = QProfessor.professor;
		
		return jpaQueryFactory.select(qstd, qprof.name)
				              .from(qstd)
				              .join(qprof)
				              .on(qstd.profno.eq(qprof.profno))
				              .where(qstd.studno.eq(stdno))
				              .fetchOne();
	}
	
	public Tuple findStdWithDNameProfName(Integer stdno) {
		QStudent qstd = QStudent.student;
		QProfessor qprof = QProfessor.professor;
		QDepartment qdept = QDepartment.department;
		
		return jpaQueryFactory.select(qstd, qprof.name, qdept.dname)
				              .from(qstd)
				              .join(qdept)							
				              .on(qstd.deptno1.eq(qdept.deptno))
				              .join(qprof)
				              .on(qstd.profno.eq(qprof.profno))
				              .where(qstd.studno.eq(stdno))
				              .fetchOne();
	}
	
	public List<Student> findStdListByDName(String dname){
		QStudent qstd = QStudent.student;
		QDepartment qdept = QDepartment.department;
		
		return jpaQueryFactory.selectFrom(qstd)
				              .join(qdept)
				              .on(qstd.deptno1.eq(qdept.deptno))
				              .where(qdept.dname.eq(dname))
				              .fetch();
	}
	
	public List<Student> findStdListByGradeAndDName(Integer grade, String dname) {
		QStudent qstd = QStudent.student;
		QDepartment qdept = QDepartment.department;
		
		return jpaQueryFactory.selectFrom(qstd)
				              .join(qdept)							
				              .on(qstd.deptno1.eq(qdept.deptno))
				              .where(qdept.dname.eq(dname).and(qstd.grade.eq(grade)))
				              .fetch();
	}
	
	public List<Student> findByDeptno1OrDeptno2(Integer deptno) {
		QStudent qstd = QStudent.student;
		QDepartment qdept = QDepartment.department;
		
		return jpaQueryFactory.selectFrom(qstd)
				.where(qstd.deptno1.eq(deptno).or(qstd.deptno2.eq(deptno)))
				.fetch();
	}
	
	public List<Student> findStdByDName1Or2(String dname1, String dname2) {
		QStudent qstd = QStudent.student;
		QDepartment qdept1 = new QDepartment("dept1");
		QDepartment qdept2 = new QDepartment("dept2");
		
		return jpaQueryFactory.selectFrom(qstd)
				              .leftJoin(qdept1)							
				              .on(qstd.deptno1.eq(qdept1.deptno))
				              .leftJoin(qdept2)
				              .on(qstd.deptno2.eq(qdept2.deptno))
				              .where(qdept1.dname.eq(dname1).or(qdept2.dname.eq(dname2)))
				              .fetch();
	}
	
	public List<Student> findStdByDName1Or2(String dname) {
		QStudent qstd = QStudent.student;
		QDepartment qdept1 = new QDepartment("dept1");
		QDepartment qdept2 = new QDepartment("dept2");
		
		return jpaQueryFactory.selectFrom(qstd)
				.leftJoin(qdept1)							
				.on(qstd.deptno1.eq(qdept1.deptno))
				.leftJoin(qdept2)
				.on(qstd.deptno2.eq(qdept2.deptno))
				.where(qdept1.dname.eq(dname).or(qdept2.dname.eq(dname)))
				.fetch();
	}
	
	public Tuple findProfByProfnoWithDeptno(Integer profno) {
		QProfessor prof = QProfessor.professor;
		QDepartment dept = QDepartment.department;
		
		return jpaQueryFactory.select(prof, dept.dname)
				              .from(prof)
				              .join(dept)
				              .on(prof.profno.eq(dept.deptno))
				              .where(prof.profno.eq(profno))
				              .fetchOne();
	}
	
	public Professor findProfByStudno(Integer studno) {
		QProfessor prof = QProfessor.professor;
		QStudent stud = QStudent.student;
		
		return jpaQueryFactory.selectFrom(prof)
							  .join(stud)
							  .on(prof.profno.eq(stud.profno))
							  .where(stud.studno.eq(studno))
							  .fetchOne();
	}
	
	public List<Professor> findProfListByDName(String dname) {
		QProfessor prof = QProfessor.professor;
		QDepartment dept = QDepartment.department;
		
		return jpaQueryFactory.selectFrom(prof)
				              .join(dept)
				              .on(prof.deptno.eq(dept.deptno))
				              .where(dept.dname.eq(dname))
				              .fetch();
	}
	
//	public Department findDeptByStudno(Integer studno) {
//		QDepartment dept = QDepartment.department;
//		QProfessor prof = QProfessor.professor;
//		
//		return jpaQueryFactory.selectFrom(prof)
//				              .join(dept)
//	}

	
	
}
