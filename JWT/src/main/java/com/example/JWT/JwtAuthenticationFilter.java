package com.example.JWT;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Base64.Encoder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;



//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있음
// login 요청해서 username, password 전송하면 (post)
//이 필터가 동작한다.
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //login 요청을 하면 로그인 시도를 위해 실행되는 함수 
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        //1.username 과 password를 받아서
        //2.정상인지 로그인 시도를 해본다. authenticationManger로 로그인 시도를 하면 loadbyusername이 자동 실행되고 
        //3.principal details를 세션에 담고 authentication객체를 리턴한다. token을 만들어서 응답해준다. (권한관리를 위해서 세션에 담는다. 권한관리 안할거면 세션에 안 담아도 된다.)

        System.out.println("로그인 시도중");

        try {
            ObjectMapper om=new ObjectMapper();

            User user=om.readValue(request.getInputStream(), User.class); //json을 user객체에 mapping해준다.

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
    
            //authenticationManager.authenticate 함수 실행시 
            //principaldetails service에 loadbyusername함수가 실행된다. 또 password를 database와 비교해 인증절차를 spring에서 해준다.
            Authentication authentication=authenticationManager.authenticate(usernamePasswordAuthenticationToken);
            
            return authentication;

        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //authentication 로그인 인증이 완료되고 난 후 실행된다. (여기서 jwt token을 만들어주면 된다.)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        // TODO Auto-generated method stub
        PrincipalDetails principalDetails=(PrincipalDetails) authResult.getPrincipal();

        // Encoder encoder=Base64.getEncoder();
        // String secret=encoder.encodeToString("cos".getBytes());

        
        //RSA 방식이 아닌 HASH 방식이다.
        String jwtToken=JWT.create()
        .withSubject("COS_TOKEN")
        .withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
        .withClaim("username", principalDetails.getUsername())
        .sign(Algorithm.HMAC512(JwtProperties.SECRET));


        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+jwtToken);

    }
    
}
