/*
 * Spring Security利用時のH2コンソール接続設定
 */
package com.project.chats.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll())
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(
                         frame -> frame.sameOrigin()))
                .securityMatcher("/**")
                .formLogin(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .headers(Customizer.withDefaults())
                .authorizeHttpRequests(authz -> authz.anyRequest().authenticated());
        return http.build();
    }
}