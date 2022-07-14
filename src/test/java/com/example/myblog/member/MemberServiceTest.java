package com.example.myblog.member;


import com.example.myblog.member.dto.JoinRequestDto;
import com.example.myblog.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.xml.bind.ValidationEvent;
import javax.validation.Validator;
import java.util.Set;

@Transactional
@SpringBootTest
 //JPA -> 테스트할때 마킹
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    //validation test
    private static Validator validator;

    @BeforeEach
    public void setup() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }


    @DisplayName("닉네임 3자리 미만인가?")
    @Test
    void nicknameLessThenThreeLetter() {
// 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");

// 닉네임 3자리 미만
        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
                new JoinRequestDto("user2", "1111", "1111", "us"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
                new JoinRequestDto("user3", "1111", "1111", "us"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호가 4자 미만")
    @Test
    void passwordLessThenFourLetter() {
        Set<ConstraintViolation<JoinRequestDto>> result1 = validator.validate(
                new JoinRequestDto("user4", "111", "111", "user"));

        Set<ConstraintViolation<JoinRequestDto>> result2 = validator.validate(
                new JoinRequestDto("user5", "111", "111", "user"));

        Assertions.assertThat(result1).size().isGreaterThan(0);
        Assertions.assertThat(result2).size().isGreaterThan(0);
    }

    @DisplayName("비밀번호에 닉네임이 포함되는 경우")
    @Test
    void containNickname() {
// 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user1");
// 비밀번호에 닉네임이 포함된 경우
        JoinRequestDto request6 = new JoinRequestDto("user6", "user212", "user12", "user2");
        JoinRequestDto request7 = new JoinRequestDto("user7", "user312", "user12", "user3");

// Exception Test
        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request6));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request7));
    }

    @DisplayName("회원가입 테스트")
    @Test
    void wrongPasswordCheckPassword() {
// 성공
        JoinRequestDto request1 = new JoinRequestDto("user1", "1111", "1111", "user");

// 비밀번호가 다름
        JoinRequestDto request8 = new JoinRequestDto("user8", "1111", "1112", "user");
        JoinRequestDto request9 = new JoinRequestDto("user9", "1111", "1112", "user");

        Assertions.assertThatNoException().isThrownBy(() -> memberService.join(request1));

// Exception Test
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request8));
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> memberService.join(request9));
    }
}
