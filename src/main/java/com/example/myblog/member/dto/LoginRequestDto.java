package com.example.myblog.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor // 기본생성자-생성자 - 테스트코드용 생성자를 만들었기 때문.
@AllArgsConstructor // 모든생성자 - 테스트코드용 작성용.
@Getter //JSON에서 클래스로 바꿔줄때 //spring이 자동으로 json ->class 변경을 위해 getter 필요..
public class LoginRequestDto {
        private String username; // id는 고유 식별자
        private String password;
        private String rePassword;
        private String nickname;

    }
