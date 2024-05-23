package com.kosta.sec.config.jwt;

public interface JwtProperties {
	String SECRET = "코스타"; // 서버의 고유 비밀키
	int ACCESS_EXPIRATION_TIME = 60000*60*2;  // 2시간
	int REFRESH_EXPIRATION_TIME = 60000*60*24; // 24시간
	String TOKEN_PREFIX = "Bearer ";
	String HEADER_STRING = "Aurhorization";
}
