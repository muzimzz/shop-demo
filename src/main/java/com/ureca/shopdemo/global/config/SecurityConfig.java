package com.ureca.shopdemo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/join", "/member/login").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")                  // 기본 로그인 HTML 페이지 생성 방지
                        .loginProcessingUrl("/member/login")  // POST 요청 받을 URL
                        .usernameParameter("email")           // 기본값이 username이라서 email로 바꿔줌
                        .passwordParameter("password")
                        .successHandler((req, res, auth) -> res.setStatus(200))  // 성공시 200 반환
                        .failureHandler((req, res, ex) -> res.setStatus(401))    // 실패시 401 반환
                        .permitAll()                          // /login 접근 허용
                )

                .httpBasic(basic -> basic.disable())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((req, res, authException) -> res.setStatus(401))
                )

                ;

        return http.build();
    }


}
