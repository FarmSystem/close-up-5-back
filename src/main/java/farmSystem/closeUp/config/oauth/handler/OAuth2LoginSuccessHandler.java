package farmSystem.closeUp.config.oauth.handler;

import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.config.oauth.CustomOAuth2User;
import farmSystem.closeUp.domain.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("OAuth2 Login 성공!");
        try {
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
            log.info("Principal에서 꺼낸 OAuth2User = {}", oAuth2User);

            /*
            각 역할별로(추가 회원가입(GUEST), 관심사 설정(INTEREST), 크리에이터 팔로우(FOLLOW), 로그인 성공(USER, CREATOR) ) 다른 페이지로 리다이렉트 해줘야한다.
             */
            String targetUrl;

            // User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getUserRole() == UserRole.GUEST) {
                targetUrl = "http://localhost:8080/login-success-test"; //추가 회원가입 url
                String redirectUrl = createToken(response, oAuth2User, targetUrl);

                // 로그인 확인 페이지로 리다이렉트 시킨다.
                log.info("추가 회원가입 진행");
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);

//            } else if(oAuth2User.getUserRole() == UserRole.INTERESTED_USER) { // 가입은 됐지만, 관심사 설정 안한경우
//                targetUrl = "http://localhost:8080/user/interest";
//                String redirectUrl = createToken(response, oAuth2User, targetUrl);
//                log.info("관심사 설정 페이지로 이동");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            } else if(oAuth2User.getUserRole() == UserRole.FOLLOWED_USER) { // 관심사 설정까지 했지만, 크리에이터 팔로우 설정을 안한 경우
//                targetUrl = "http://localhost:8080/user/creator-follow";
//                String redirectUrl = createToken(response, oAuth2User, targetUrl);
//                log.info("크리에이터 팔로우 페이지로 이동");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
//            } else if (oAuth2User.getUserRole() == UserRole.SIGNUP_CREATOR) { // 크리에이터가 추가정보는 입력했는데, 플랫폼 정보 및 관심사 설정안한경우
//                targetUrl = "http://localhost:8080/creator/interest";
//                String redirectUrl = createToken(response, oAuth2User, targetUrl);
//                log.info("크리에이터 플랫폼 및 이미지 검증 및 관심사 설정 페이지로 이동");
//                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            }
        } catch (Exception e) {
            throw e;
        }
    }

    private String createToken(HttpServletResponse response, CustomOAuth2User oAuth2User, String targetUrl) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getUserId());
        String refreshToken = jwtService.createRefreshToken(oAuth2User.getUserId()); //여기서 refreshToken 업데이트 됨
        log.info(accessToken);
        log.info(refreshToken);

        String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("accessToken", accessToken)
                .queryParam("refreshToken", refreshToken)
                .build().encode(StandardCharsets.UTF_8).toUriString();
        return redirectUrl;
    }
}
