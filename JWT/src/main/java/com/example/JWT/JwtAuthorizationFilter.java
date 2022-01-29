package com.example.JWT;

import java.io.IOException;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//시큐리티가 filter를 가지고 잇는데 그 필터 중에 BasicAuthenticationFilter라는 것이 있다.
//권한이나 인증이 필요한 특정 주소를 요청했을 때, 위 필터를 타게 된다.
// 권한이나 인증이 필요한 주소가 아니면 이 필터를 안 탄다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository=userRepository;
    }
    

    //인증이나 권한이 필요한 주소요청이 있을 때 해당 필터를 타게 된다.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // TODO Auto-generated method stub
        //super.doFilterInternal(request, response, chain); 응답이 두번이 된다. 지워야한다.

        String jwtHeader=request.getHeader("Authorization");

        //header가 있는지
        if(jwtHeader==null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }
        //JWT 토큰을 검증을 해서 정상적인 사용자인지 확인
        String jwtToken=request.getHeader(JwtProperties.HEADER_STRING).replace(JwtProperties.TOKEN_PREFIX,"");
        // Encoder encoder=Base64.getEncoder();
 

        String username=JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build().verify(jwtToken).getClaim("username").asString();

        //서명이 정상적으로 됨
        if(username!=null){
            User userEntity=userRepository.findByUsername(username);
            if(userEntity!=null){
                //서명 인증이 되었으니 허가
                PrincipalDetails principalDetails=new PrincipalDetails(userEntity);
                Authentication authentication=new UsernamePasswordAuthenticationToken(null,null,principalDetails.getAuthorities());

                //security 세션 공간
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        chain.doFilter(request, response);
    }
    
}
