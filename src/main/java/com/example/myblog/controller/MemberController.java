package com.example.myblog.controller;


import com.example.myblog.member.dto.JoinRequestDto;
import com.example.myblog.member.dto.LoginRequestDto;
import com.example.myblog.member.service.MemberService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@Controller  //JSON
public class MemberController {

    //순환참조를 잡아내지 못해서 추천안함
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    //[GET, POST] 서버 리소스에 변화를 주냐 안주냐
    //회원가입
    @PostMapping("/user/signup")
    public String join(JoinRequestDto dto){
        memberService.join(dto);
        return "redirect:/user/loginView";
    }


//    //로그인
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequestDto dto){
//        return dto.toString();
//    }
    // 회원 로그인 페이지
    @GetMapping("/user/loginView")
    public String login() {
        return "login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup() {
        return "signup";
    }

//    // 회원 가입 요청 처리
//    @PostMapping("/user/signup")
//    public String registerUser(SignupRequestDto requestDto) {
//        userService.registerUser(requestDto);
//        return "redirect:/user/loginView";
//    }
}
