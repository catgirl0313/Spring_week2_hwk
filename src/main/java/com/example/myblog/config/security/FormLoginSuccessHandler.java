package com.example.myblog.config.security;


import com.example.myblog.config.security.jwt.JwtTokenUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//FormLoginFilter가 성공했을 때 실행될 클래스
public class FormLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    public static final String AUTH_HEADER = "Authorization"; // 토큰을 가지고 있으면 header에 Authorization 무조건 붙여야함. 규칙.
    public static final String TOKEN_TYPE = "BEARER";//토큰 종류 중 하나. Bearer가장 standard한 녀석

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                        final Authentication authentication) {
        final MemberDetailsImpl userDetails = ((MemberDetailsImpl) authentication.getPrincipal());
        // Token 생성
        final String token = JwtTokenUtils.generateJwtToken(userDetails); //토큰생성 .
        response.addHeader(AUTH_HEADER, TOKEN_TYPE + " " + token);//server ->client 로 반응해줘. 그러니까 response에 담아
    }

}
