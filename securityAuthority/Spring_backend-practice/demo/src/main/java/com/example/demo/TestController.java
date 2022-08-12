package com.example.demo;

import com.example.demo.DTO.Post;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //이 파일은 controller다. 
public class TestController {

    @GetMapping("/post") //get 방식으로 post url 요청이 들어올 시 함수가 작용된다.
    public String test(Model model) {
        Post post1 = new Post(1, "lee", "book1");
        Post post2 = new Post(2, "choi", "book2");
        Post post3 = new Post(3, "kim", "book3");
        List<Post> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        list.add(post3);
        model.addAttribute("list", list);
        return "test"; // WEB-INF\jsp\test.jsp를 보낸다.
    }
    @GetMapping("/staff")
    public String getStaff(Model model){
        return "staff";
    }
    @GetMapping("/admin")
    public String getAdmin(Model model){
        return "admin";
    }
    @GetMapping("/customlogin")
    public String getLogin(Model model){
        return "customlogin";
    }
    @GetMapping("/accessDenied")
    public String getDenied(Model model){
        return "accessDenied";
    }
    @GetMapping("/")
    public String getIndex(Model model){
        return "index";
    }
}