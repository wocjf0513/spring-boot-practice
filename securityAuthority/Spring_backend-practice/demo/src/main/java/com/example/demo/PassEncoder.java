package com.example.demo;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PassEncoder {
    public PasswordEncoder passwordEncoder;
    
	public String encode(String password) {
		passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder(); //bcrypt형식의 encode를 passencoder변수에 생성시킨다.
		String encPassword = passwordEncoder.encode(password); //비밀번호가 들어오면 이를 bcrypt형식으로 변환시켜 반환한다.
        return encPassword;
	}
}
