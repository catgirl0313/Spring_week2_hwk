package com.example.myblog.config;


import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

//Spring Security 파일 관리
@EnableWebSecurity //사용할 수 있게 하겠다는 어노테이션?
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable(); //
        http.csrf().disable();
        http.cors().disable();
        http.authorizeRequests()
                .anyRequest().permitAll(); //어떤 리퀘스트도 허가해준다. 막아둔 시큐리티도 열리게
    }
}
