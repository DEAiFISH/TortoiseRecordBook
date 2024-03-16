package com.deaifish.tortoiserecordbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * @description Security配置类
 *
 * @author DEAiFISH
 * @date 2024/3/16 18:17
 */
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers("/").permitAll()
                .anyRequest().authenticated());

        http.cors(withDefaults());

        http.formLogin(withDefaults());

        // 测试api时必须关闭，否则403
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
