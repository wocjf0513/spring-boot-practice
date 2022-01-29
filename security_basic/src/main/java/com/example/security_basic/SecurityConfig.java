package com.example.security_basic;


import org.springframework.beans.factory.annotation.Autowired;

//구글 로그인 완료된 뒤 후처리 필요. 
//1.코드받기(인증함), 2.엑세스토큰(권한생김), 
//3.토큰을 통해 사용자프로필 정보를 얻음 
//4-1.정보를 토대로 회원가입 자동 진행하기도 함.
//4-2.(이메일,번호,이름,아이디) 쇼핑물 -> 추가적인 집주소도 필요하면 추가적인 회원가입 창
            
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled =true) //secured 어노테이션 활성화 , postAuthorize, preAuthorize 어노테이션 활성화
@EnableWebSecurity //스프링 시큐리티 필더가 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    


    @Bean 
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
    @Autowired
    private PrincipalOauth2UserService principalOauth2UserService;

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable();
            
        http
            .authorizeRequests()
            .antMatchers("/user/**").authenticated()
            .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll()
            .and()
            .formLogin()
            .loginPage("/loginForm")
            .loginProcessingUrl("/login") //login주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인 진행
            .defaultSuccessUrl("/")
            .and()
            .oauth2Login()
            .loginPage("/loginForm")
            .userInfoEndpoint()
            .userService(principalOauth2UserService);
            
            //구글 로그인 시엔 코드X, 엑세스 토큰과 사용자 프로필 정보를 받음.
            
        }
    
}
