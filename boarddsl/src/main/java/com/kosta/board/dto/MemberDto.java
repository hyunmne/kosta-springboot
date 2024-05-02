package com.kosta.board.dto;


import com.kosta.board.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class MemberDto {
	private String id;
	private String name;
	private String nickname;
	private String password;
	private String email;
	private String address;
	
	public Member toEntity() {
		return Member.builder()
				     .id(id)
				     .name(name)
				     .nickname(nickname)
				     .password(password)
				     .email(email)
				     .address(address)
				     .build();
	}
}
