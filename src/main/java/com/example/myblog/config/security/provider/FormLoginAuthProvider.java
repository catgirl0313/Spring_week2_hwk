package com.example.myblog.config.security.provider;

import com.example.myblog.config.security.MemberDetailsImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.Resource;

//Repository 에서 가지고 온 Member의 Password 랑 입력한 Password가 같은지 비교. -엄밀히는. provider가 역할을 함
//로그인페이지에서 아이디 + 비밀번호 로그인 인증 구간
public class FormLoginAuthProvider implements AuthenticationProvider {

    @Resource(name="memberDetailsServiceImpl")
    private UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    public FormLoginAuthProvider(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        ///가지고온 토큰을 받아와서 타입을 변경.
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // FormLoginFilter 에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        //토큰안에서 username, password 가져와.
        String username = token.getName();
        String password = (String) token.getCredentials(); //credential 보통 비밀번호.

        // UserDetailsService 를 통해 DB에서 username 으로 사용자 조회
        MemberDetailsImpl memberDetails = (MemberDetailsImpl) userDetailsService.loadUserByUsername(username);
        //인코딩되는 시간정보 + 인코딩 패턴 +비밀번호-> 인코딩 해줘. 같은 값으로 인코딩해도 시간은 달라져서 같을 수 없음.
        if (!passwordEncoder.matches(password, memberDetails.getPassword())) {//암호화해서 저장. 되돌리기는불가능
            //토큰 패스워드 리포지토리 멤버의 페스워드를 비교 - : matches .
            throw new BadCredentialsException(memberDetails.getUsername() + "Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
