package farmSystem.closeUp.config.security;


import farmSystem.closeUp.config.CorsConfig;
import farmSystem.closeUp.config.jwt.*;
import farmSystem.closeUp.config.oauth.CustomOAuth2UserService;
import farmSystem.closeUp.config.oauth.handler.OAuth2LoginFailureHandler;
import farmSystem.closeUp.config.oauth.handler.OAuth2LoginSuccessHandler;
import farmSystem.closeUp.domain.UserRole;
import farmSystem.closeUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CorsConfig corsConfig;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginFailureHandler oAuth2LoginFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtExceptionFilter jwtExceptionFilter;

    // ⭐️ CORS 설정
    CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedHeaders(Collections.singletonList("*"));
            config.setAllowedMethods(Collections.singletonList("*"));
            config.setAllowedOriginPatterns(Collections.singletonList("http://localhost:5173")); // ⭐️ 허용할 origin
            config.setAllowCredentials(true);
            return config;
        };
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) //csrf 비활성
                .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable()) //폼 로그인 비활성
                .httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable()) //HTTP 기본인증 비활성
                .cors(corsConfigurer -> corsConfigurer.configurationSource(corsConfigurationSource()))
                .exceptionHandling(exceptionHandlingConfigurer -> exceptionHandlingConfigurer
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler))

                // 시큐리티가 세션을 만들지도 사용하지도 않음.
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 특정 URL에 대한 권한 설정
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/login-success-test/**").permitAll()
                        .requestMatchers("/health","/token/reissue", "/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                        .requestMatchers("/user/sign-up/**").hasAnyRole(String.valueOf(UserRole.GUEST), String.valueOf(UserRole.SIGNUP_USER), String.valueOf(UserRole.FOLLOWED_USER), String.valueOf(UserRole.INTERESTED_USER))
                        .requestMatchers("/user/**").hasRole(String.valueOf(UserRole.USER))
                        .requestMatchers("/user/raffle-products", "/user/raffle-products/{raffleProductId}").hasAnyRole(String.valueOf(UserRole.USER), String.valueOf(UserRole.CREATOR))
                        .requestMatchers("/creator/sign-up/**").hasAnyRole(String.valueOf(UserRole.GUEST), String.valueOf(UserRole.SIGNUP_CREATOR))
                        .requestMatchers("/creator/**").hasRole(String.valueOf(UserRole.CREATOR))
                    .anyRequest().authenticated()
                )

                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint.userService(customOAuth2UserService))
                                .successHandler(oAuth2LoginSuccessHandler) // 동의하고 계속하기를 눌렀을 때 Handler 설정
                                .failureHandler(oAuth2LoginFailureHandler) // 소셜 로그인 실패 시 핸들러 설정

                                );
        // JwtExceptionFilter -> JwtAuthenticationFilter -> UsernamePasswordAuthenticationFilter 순으로 필터가 실행된다.
        return  http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                    .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtService, userRepository);
        return jwtAuthenticationFilter;
    }
}
