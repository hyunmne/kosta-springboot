package com.kosta.board.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardLike {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer num;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="memberId")
	private Member member;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="boardNum")
	private Board board;
	
	@Override
	public String toString() {
		return String.format("[%d, %$s, %d]", num, member.getId(), board.getNum());
	}

}
