package com.example.myblog.config.security.filter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//너 username이랑 password로 로그인 하는거지?
public class FormLoginFilter extends UsernamePasswordAuthenticationFilter {

    //JSON <->objedct 변환 양방향으로 가능
    private final ObjectMapper objectMapper;

    public FormLoginFilter(final AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        objectMapper = new ObjectMapper()//object멤버 설정.
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //모르는애가 들어오면 프로퍼티처리해라
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //Provider들을 관리하는 manager에게 전달할 토큰 생성
        UsernamePasswordAuthenticationToken authRequest; //괄호안에 넣으면 못쓰니까 밖으로 빼서 두번 적어줘
        try {
            //RequestBody에서 username,password 추출
            JsonNode requestBody = objectMapper.readTree(request.getInputStream());
            String username = requestBody.get("username").asText();
            String password = requestBody.get("password").asText();
            authRequest = new UsernamePasswordAuthenticationToken(username, password); //토큰 생성하는거임.
        } catch (Exception e) {
            throw new RuntimeException("username, password 입력이 필요합니다. (JSON)");
        }

        setDetails(request, authRequest); //Request에 Token담기. Request에서details를 담는거 토큰을 담아줘. request가 provider와
        return this.getAuthenticationManager().authenticate(authRequest); // 필터에 대응되는 메니저 불러와서 처리하라고 명령.
        //필터 전부 검사해서 다음 토큰으로 넘겨줘 여기서는 jwtauthfilter 로
    }
}
