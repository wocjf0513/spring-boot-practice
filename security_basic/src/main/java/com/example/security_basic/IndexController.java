package com.example.security_basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;


//login form -> login (login processing page) session(Authentication (UserDetails) 생성 ->
@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    

    //AuthenicationPrincipal annotaion을 통해 user정보를 얻을 수 있다.
    @GetMapping("test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal PrincipalDetails user){
        PrincipalDetails principalDetails=(PrincipalDetails) authentication.getPrincipal();
        
        return principalDetails.getUsername()+user.getUsername();
        //authentication을 통해 현재 로그인되어있는 정보를 가져올 수 있다.
    }

    @GetMapping("test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication,  @AuthenticationPrincipal OAuth2User user){
        OAuth2User oAuth2User=(OAuth2User) authentication.getPrincipal();
        return oAuth2User.getName();
        //authentication을 통해 현재 로그인되어있는 정보를 가져올 수 있다.
    }
    
    @GetMapping({"/",""})
    public String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal PrincipalDetails principalDetails){
        return principalDetails.getName();
    }
    
    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }
    
    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }
    
    @GetMapping("/loginForm")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String join(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(User user){
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword=user.getPassword();
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String encPassword=bCryptPasswordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }
    
    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }
}
