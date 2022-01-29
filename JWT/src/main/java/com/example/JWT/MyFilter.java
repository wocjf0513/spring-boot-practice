package com.example.JWT;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyFilter implements Filter{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
                //id,pw가 정상적으로 들어오면 토큰을 만들어주고 그걸 응답으로 준다.
                //요청할때마다 header에 Authorization에 value값으로 토큰을 가지고 오면
                //검증을 통해 요청을 처리한다.
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse res=(HttpServletResponse) response;

        
        chain.doFilter(req, res);
    }



    
}
