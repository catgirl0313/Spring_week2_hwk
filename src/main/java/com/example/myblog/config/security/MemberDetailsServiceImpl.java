package com.example.myblog.config.security;


;
import com.example.myblog.member.domain.Member;
import com.example.myblog.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Repository 에서 가지고 온 Member의 Password 랑 입력한 Password가 같은지 비교. -엄밀히는. provider가 역할을 함
//Repository에서 가져온 Member -> ㅡ드ㅠㄷㄱㅇㄷㅅ먀니memeber details로 변경
@Service
public class MemberDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public MemberDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));

        return new MemberDetailsImpl(member);
    }
}