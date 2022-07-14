package com.example.myblog.config.security.provider;

import com.example.myblog.config.security.MemberDetailsImpl;
import com.example.myblog.config.security.jwt.JwtDecoder;
import com.example.myblog.config.security.jwt.JwtPreProcessingToken;
import com.example.myblog.member.domain.Member;
import com.example.myblog.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JWTAuthProvider implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    private final MemberRepository userRepository;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        String token = (String) authentication.getPrincipal();
        String username = jwtDecoder.decodeUsername(token);

        // TODO: API 사용시마다 매번 User DB 조회 필요
        //  -> 해결을 위해서는 UserDetailsImpl 에 User 객체를 저장하지 않도록 수정
        //  ex) UserDetailsImpl 에 userId, username, role 만 저장
        //    -> JWT 에 userId, username, role 정보를 암호화/복호화하여 사용
        Member member = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));;
        MemberDetailsImpl userDetails = new MemberDetailsImpl(member);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtPreProcessingToken.class.isAssignableFrom(authentication); // 이 토큰이어야지만 프로바이드가 처리함. 검증다하고 다시필터에넘겨.
    }
}
