package com.example.security_basic;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

//로그인 진행 완료되면 시큐리티 session 만듬 (Security ContextHolder)
//오브젝트 => Authentication 객체
//Authentication안에 User정보가 있어야 한다.
//User 오브젝트 타입 => UserDetails 타입 객체여야 한다.


public class PrincipalDetails implements UserDetails, OAuth2User{

    private User user;//콤포지션

    private Map<String,Object> attributes;

    //일반 로그인
    public PrincipalDetails(User user){
        this.user=user;
    }

    //Oauth 로그인
    public PrincipalDetails(User user, Map<String,Object>attributes){
        this.user=user;
        this.attributes=attributes;

    }

    //해당 User의 권한을 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collect=new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
        return collect;
    }

    @Override
    public String getPassword() {
        return user.getPassword();    
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {

        //우리 사이트에서 1년동안 회원이 로그인을 안 하면 휴면 계정
        //현재시간 - 로그인 시간 > 1년 => return false;
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        // TODO Auto-generated method stub
        return attributes;
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
