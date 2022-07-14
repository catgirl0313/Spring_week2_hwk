package com.example.myblog.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.myblog.config.security.MemberDetailsImpl;


import java.util.Date;

public final class JwtTokenUtils {

    private static final int SEC = 1;
    private static final int MINUTE = 60 * SEC;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;

    // JWT 토큰의 유효기간: 3일 (단위: seconds)
    private static final int JWT_TOKEN_VALID_SEC = 3 * DAY; //서비스 정책에 따라 달라질 수 있어.
    // JWT 토큰의 유효기간: 3일 (단위: milliseconds)
    private static final int JWT_TOKEN_VALID_MILLI_SEC = JWT_TOKEN_VALID_SEC * 1000;

    public static final String CLAIM_EXPIRED_DATE = "EXPIRED_DATE";
    public static final String CLAIM_USER_NAME = "USER_NAME";
    public static final String JWT_SECRET = "jwt_secret_!@#$%"; //SECRET KEY

    public static String generateJwtToken(MemberDetailsImpl userDetails) {
        String token = null;
        try {
            token = JWT.create()
                    .withIssuer("sparta") //이 토큰을 누가 만든건가? 정확히 인증해야 믿고쓸수있지.
                    .withClaim(CLAIM_USER_NAME, userDetails.getUsername()) //토큰 설정정보 만들어줌(토큰의 유저는 유저디테일에서 빼와서씀?설정정보 입력하는거임.
                     // 토큰 만료 일시 = 현재 시간 + 토큰 유효기간)
                    .withClaim(CLAIM_EXPIRED_DATE, new Date(System.currentTimeMillis() + JWT_TOKEN_VALID_MILLI_SEC))
                    .sign(generateAlgorithm()); //토큰 완성( 어떤 알고리즘으로 암호화할거냐?를 알려줘야해.
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return token; //발행.
    }

    private static Algorithm generateAlgorithm() {
        return Algorithm.HMAC256(JWT_SECRET);
    } //얻떤알고리즘
}
