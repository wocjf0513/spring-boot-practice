package com.example.security_basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    //구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수

    @Autowired
    private UserRepository userRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        
        OAuth2User oAuth2User=super.loadUser(userRequest);
        String username=oAuth2User.getAttribute("email");
        BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
        String password=bCryptPasswordEncoder.encode("겟인데어");
        String email=oAuth2User.getAttribute("email");
        String role="ROLE_USER";


        System.out.println(oAuth2User.getAttributes());

        //이미 만들어져있을 수 있으니
        User userEntity=userRepository.findByUsername(username);
        if(userEntity==null){
            userEntity=User.builder()
            .username(username)
            .password(password)
            .role(role)
            .email(email)
            .build();
            userRepository.save(userEntity);
        }
        
            return new PrincipalDetails(userEntity,oAuth2User.getAttributes());
        

        
        //sub(일렬 숫자), 이름, 프로필, 이메일, 국적 정보가 담김
        //username = google_sub password = sub emial= email role="ROLE_USER"
        //구글 로그인 버튼 클릭 -> 구글 로그인 -> code 리턴 (oAuth-Client라이브러리) -> AccessToken요청
        //userRequest 정보 -> 회원 프로필 받아야함(loadUser함수을 이용하면)
    }
    
    //Oauth에 대한 후처리를 당담한다.
}
