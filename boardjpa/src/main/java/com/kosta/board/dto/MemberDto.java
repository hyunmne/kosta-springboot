package com.kosta.board.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MemberDto {
	private String id;
	private String name;
	private String nickname;
	private String password;
	private String email;
	private String address;
}
