// package com.example.JWT;

// import org.springframework.boot.web.servlet.FilterRegistrationBean;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;


// //필터 거는 파일
// @Configuration
// public class FilterConfig {


    // 여기서 거는 filter는 security filter보다 늦게 동작한다.
    // security filter보다 먼저 동작하고 싶으면 spring security 필터 이전에 동작하게 걸어야한다.
    // @Bean
    // public FilterRegistrationBean<MyFilter> filter(){
    //     FilterRegistrationBean<MyFilter> bean=new FilterRegistrationBean<>(new MyFilter());
    //     bean.addUrlPatterns("/**");
    //     bean.setOrder(0);
    //     return bean;
    // }
    
// }
