package com.example.demo;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity //WebSecurity에 대한 설정 파일이다.
public class MySecurityConfig extends WebSecurityConfigurerAdapter{
    

    @Override //WebSecurityConfigurerAdapter에 대한 기본 설정을 Override하겠다.
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //csrf토근 사용을 하지 않는다. (csrf는 사이트 요청을 위조하는 공격인데 이에 대한 토근 사용 시 예방을 할 수 있다. )
        .authorizeRequests() //신뢰있는 요청에 대해 
        .regexMatchers(HttpMethod.GET, "/admin").hasRole("admin") //주소 admin을 가진 요청은 admin 권한을 가져야한다.
        .regexMatchers(HttpMethod.GET, "/staff").hasAnyRole("staff","admin")
        .and()
        .formLogin() //기본적인 form의 형태를 login으로 하되
        .loginPage("/customlogin") // login page는 customlogin 이 주소로 한다.
        .defaultSuccessUrl("/") //성공 시 home 주소로 redirection 된다.
        .loginProcessingUrl("/dologin") //login을 처리하는 주소는 dologin이다. 
        .usernameParameter("id") // id라는 parameter를 username으로 
        .passwordParameter("pass") //pass라는 parameter를 password로서 loginform에서 받아들인다.
        .failureUrl("/customlogin"); //실패 시 customlogin으로 redirection된다.

        http.logout()
        .logoutUrl("/logout")  //logout 요청 주소는 logout이고 
        .logoutSuccessUrl("/customlogin") //logout 성공시 customlogin으로 redirectio된다.
        .invalidateHttpSession(true); //invalid 요청 시 logout된다.
        
        http.exceptionHandling().accessDeniedPage("/accessDenied"); //접근 불가 시 accessDenied 주소 redirectio된다. 
    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        PassEncoder pe=new PassEncoder(); //비번 encode다.
        auth.inMemoryAuthentication() //자체 memory를 사용해 유저 정보 등록 (이 설정을 바꿔 database를 이용해 유저 서비스 구현할 수 있다.)
        .withUser("staff").password(pe.encode("1234")).roles("staff").and()
        .withUser("admin").password(pe.encode("1234")).roles("admin"); 
        //id: staff 비번:1234 유저는 staff 권한을 가지고 id: admin 비번:1234는 admin권한을 가진다.
    }


}
