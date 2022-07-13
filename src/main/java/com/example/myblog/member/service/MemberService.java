package com.example.myblog.member.service;

import com.example.myblog.member.dto.JoinRequestDto;
import com.example.myblog.member.repository.MemberRepository;
import com.example.myblog.member.domain.Member;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public String join(JoinRequestDto dto) {
        String password = dto.getPassword();
        String rePassword = dto.getRePassword();
        String nickname = dto.getNickname();
        //String db = "";

        if (!password.equals(rePassword)) {   //atrl +alt + n
            return "회원가입 실패";
        }
        if (password.contains(nickname)) {
            return "회원가입 실패";
        }


        if (memberRepository.findByNickname(nickname) != null) {
            return "중복되는 닉네임입니다.";
        }

        //dto를 entity로 변환해주기. :
        Member member = dto.toEntity(); //명시적으로 entity.
//        Member member = dto.toEntity();

        //Entity Insert
        memberRepository.save(member);
        return "성공";
    }
}
