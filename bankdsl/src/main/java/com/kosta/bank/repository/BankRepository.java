package com.kosta.bank.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kosta.bank.entity.Account;
import com.kosta.bank.entity.Member;
import com.kosta.bank.entity.QAccount;
import com.kosta.bank.entity.QMember;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BankRepository {
	
	@Autowired
	private JPAQueryFactory jpaQueryFactory;
	
	@Autowired
	private AccountRepository accres;
	
	@Autowired
	private MemberRepository memres;
	
	// 계좌 개설
	public void insertAccount(Account acc) {
		accres.save(acc);
	}
	
	// 계좌조회
	public Account findAccountById(String id) {
		QAccount account = QAccount.account;
		// sql 쿼리.. 
		return jpaQueryFactory.select(account)
							  .from(account)
							  .where(account.id.eq(id))
							  .fetchOne();
	}
	
	// 입출금
	@Transactional
	public void updateBalance(String id, Integer balance) {
		QAccount acc = QAccount.account;
		jpaQueryFactory.update(acc)
					   .set(acc.balance, balance)
					   .where(acc.id.eq(id))
					   .execute();
	}

	// 전체 계좌 조회
	public List<Account> findAllAccount(){
		QAccount acc = QAccount.account;
		return jpaQueryFactory.selectFrom(acc) // select와 from이 같으면 한 번에 쓸 수 있음
							  .fetch(); // 결과값이 한개가 아니라 여러개면 fetch 사용
	}
	
	// 계좌 이체
	public void transfer(String sid, String rid, Integer smoney, Integer rmoney) {
		QAccount sacc = new QAccount("acc1");
		QAccount racc = new QAccount("acc2");
		
		jpaQueryFactory.update(sacc)
		               .set(sacc.balance, smoney)
		               .where(sacc.id.eq(sid))
		               .execute();
		               
		jpaQueryFactory.update(sacc)
		               .set(racc.balance, rmoney)
		               .where(racc.id.eq(rid))
		               .execute();
	}
	
	// 회원가입
	public void insertMember(Member mem) {
		memres.save(mem);
	}
	
	// 회원조회 (로그인)
	public Member findMemberById(String id) {
		QMember member = QMember.member;
		return jpaQueryFactory.select(member)
							  .from(member)
						   	  .where(member.id.eq(id))
							  .fetchOne();
	}
	
	
	
}
