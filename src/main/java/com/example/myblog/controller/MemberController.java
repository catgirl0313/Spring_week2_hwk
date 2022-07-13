package com.example.myblog.controller;


import com.example.myblog.dto.JoinRequestDto;
import com.example.myblog.dto.LoginRequestDto;
import com.example.myblog.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController  //JSON
public class MemberController {

    //순환참조를 잡아내지 못해서 추천안함
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //[GET, POST] 서버 리소스에 변화를 주냐 안주냐
    //회원가입
    @PostMapping("/join")
    public String join(@RequestBody JoinRequestDto dto){
        return memberService.join(dto);
    }


    //로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto dto){
        return dto.toString();
    }
}
