package com.example.myblog.member.service;

import com.example.myblog.member.dto.JoinRequestDto;
import com.example.myblog.member.repository.MemberRepository;
import com.example.myblog.member.domain.Member;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder; //빈주입은 생성자만들어줘야해

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String join(JoinRequestDto dto) {
        String password = dto.getPassword();
        String rePassword = dto.getRePassword();
        String nickname = dto.getNickname();
        //String db = "";

        if (!password.equals(rePassword)) {   //atrl +alt + n
            throw new IllegalArgumentException("비밀번호와 비밀번호 재확인은 일치해야 합니다.");
        }
        if (password.contains(nickname)) {
            throw new IllegalArgumentException("비밀번호에 닉네임이 포함되어서는 안됩니다.");
        }


        if (memberRepository.findByNickname(nickname) != null) {
            throw new IllegalArgumentException("중복 닉네임 입니다.");
        }

        //dto를 entity로 변환해주기. :
        Member member = dto.toEntity(); //명시적으로 entity.
        member.setPassword(passwordEncoder.encode(member.getPassword())); //저장된 패스워드를 가져와서 인코딩된 패스워드로 저장할거야.
//        Member member = dto.toEntity();

        //Entity Insert
        memberRepository.save(member);
        return "성공";
    }
}
