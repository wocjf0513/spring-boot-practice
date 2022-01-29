package com.example.JWT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private CorsFilter corsFilter;

    @Autowired
    private UserRepository userRepository;


    @Override
    protected void configure(HttpSecurity http) throws Exception {



        http
            .addFilterBefore(new MyFilter(), SecurityContextPersistenceFilter.class);
            //jwt 필터가 security전에 걸려야 하므로
        http
            .csrf().disable()  
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .addFilter(corsFilter)  // @corsorigin 은 인증 된 요청은 처리가 안된다.
            .formLogin().disable()
            .httpBasic().disable()
            .addFilter(new JwtAuthenticationFilter(authenticationManager())) //매개변수로 AuthenticationManager를 던져줘야한다. 근데 이거는 WebSecurityConfigurerAdapter가 가지고 있다.
            .addFilter(new JwtAuthorizationFilter(authenticationManager(),userRepository))
            .authorizeRequests()
                .antMatchers("/api/v1/user/**").access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/manager/**").access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/api/v1/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();

                
    }
}
