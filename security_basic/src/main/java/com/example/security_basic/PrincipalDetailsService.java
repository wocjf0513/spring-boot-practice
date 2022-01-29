package com.example.security_basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login");
// login 요청이 오면 자동으로 UserDetailService 타입으로 IoC되어 있는 loadUserByUsername함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    //input의 username과 똑같은 변수명이어야 한다.
    // 시큐리티 session(내부Authentication(내부 UserDetails))
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    
       User userEntity = userRepository.findByUsername(username);
       System.out.println(username);
       if(userEntity!=null){
           return new PrincipalDetails(userEntity);
       }
        return null;
    }
    
}
