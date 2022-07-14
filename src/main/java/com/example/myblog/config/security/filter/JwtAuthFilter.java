package com.example.myblog.config.security.filter;


import com.example.myblog.config.security.jwt.HeaderTokenExtractor;
import com.example.myblog.config.security.jwt.JwtPreProcessingToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Token 을 내려주는 Filter 가 아닌  client 에서 받아지는 Token 을 서버 사이드에서 검증하는 클레스 SecurityContextHolder 보관소에 해당
 * Token 값의 인증 상태를 보관 하고 필요할때 마다 인증 확인 후 권한 상태 확인 하는 기능
 */
public class JwtAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final HeaderTokenExtractor extractor;

    public JwtAuthFilter(
            RequestMatcher requiresAuthenticationRequestMatcher,
            HeaderTokenExtractor extractor
    ) {
        super(requiresAuthenticationRequestMatcher);

        this.extractor = extractor;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException, IOException {

        // JWT 값을 담아주는 변수 TokenPayload
        String tokenPayload = request.getHeader("Authorization");
        if (tokenPayload == null) {
            //토큰이 없는 경우 로그인 페이지로 리다이렉트
            response.sendRedirect("/user/loginView");
//            response.sendRedirect("/user/login");
            return null;
        }

        //HEADER에서 jwt TOKEN 값만 가져와서 Manager에 넘길 Token 생성
        JwtPreProcessingToken jwtToken = new JwtPreProcessingToken(
                extractor.extract(tokenPayload, request)); //익스트렉트 알죠 ? 거기서 토큰만 가져와서  생성자 안에 토큰 넣어줘.

        return super
                .getAuthenticationManager() //똑같이 매니저한테 넘겨서 처리해준다.
                .authenticate(jwtToken);
    }

    @Override // provider의 성공 검증을 해줌. 인증성공하면 request에 token을 저장. 인증보관함에 jwt 결과를 넣어줘. 어떤 권한이 있다.
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {
        /*
         *  SecurityContext 사용자 Token 저장소를 생성합니다.
         *  SecurityContext 에 사용자의 인증된 Token 값을 저장합니다.
         */
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context); // 인증결과를 context 에 담아서

        // FilterChain chain 해당 필터가 실행 후 다른 필터도 실행할 수 있도록 연결실켜주는 메서드
        chain.doFilter(  //다음단계로 넘겨줘.
                request,
                response
        );
    }

    @Override
    protected void unsuccessfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException failed
    ) throws IOException, ServletException {
        /*
         *	로그인을 한 상태에서 Token값을 주고받는 상황에서 잘못된 Token값이라면
         *	인증이 성공하지 못한 단계 이기 때문에 잘못된 Token값을 제거합니다.
         *	모든 인증받은 Context 값이 삭제 됩니다.
         */
        SecurityContextHolder.clearContext();  //토큰 지워져.

        super.unsuccessfulAuthentication(
                request,
                response,
                failed
        );
    }
}
