package com.example.myblog.dto;

import com.example.myblog.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

//test코드 작성용.

@NoArgsConstructor // 기본생성자-생성자 - 테스트코드용 생성자를 만들었기 때문.
@AllArgsConstructor // 모든생성자 - 테스트코드용 작성용.
@Getter //JSON에서 클래스로 바꿔줄때 //spring이 자동으로 json ->class 변경을 위해 getter 필요..
public class JoinRequestDto {
    private String username; // id는 고유 식별자
    @Size(min=4)
    private String password;
    @Size(min=4)
    private String rePassword;

    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*\\d).{3,}$")
    private String nickname;

    //dto를entity로 직접 만들어주기
    public Member toEntity() {
        return new Member(username, password, nickname); //생성자를 만들었으니 값만 채우면 돼.
    }
}
