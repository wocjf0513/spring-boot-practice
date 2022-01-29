package com.example.security_basic;

import org.springframework.data.jpa.repository.JpaRepository;

//jpa를 상속받기 때문에 ioc자동 생성
public interface UserRepository extends JpaRepository<User,Integer> {

    //findBy규칙 -> Username 문법
    //select * from user where username=?
    public User findByUsername(String username);
    
}
