package com.kosta.board.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {
	@Id
	private String id;
	@Column
	private String name;
	@Column
	private String nickname;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String address;
	
	@OneToMany(mappedBy="member", fetch=FetchType.LAZY) // 일대다 관계 (DB 관계 설정), 선택적
	private List<Board> boardList = new ArrayList<>();
}
