package farmSystem.closeUp.controller;


import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.user.request.UserFollowRequest;
import farmSystem.closeUp.dto.user.request.UserInfoRequest;
import farmSystem.closeUp.dto.user.request.UserInterestRequest;
import farmSystem.closeUp.dto.user.response.GetSearchCreatorResponse;
import farmSystem.closeUp.dto.user.response.PostSignUpResponse;
import farmSystem.closeUp.dto.user.response.PostTokenReissueResponse;
import farmSystem.closeUp.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;


    // 따라서 redis의 refreshToken과 전달받은 refreshToken 비교 후 일치한다면 accessToken, refreshToken 재발급
    @PostMapping("/token/reissue")
    public CommonResponse<PostTokenReissueResponse> reIssueToken(@RequestHeader("Authorization-refresh") @Valid final String refreshToken) {
        PostTokenReissueResponse tokenResponse = userService.reIssueToken(refreshToken);
        return CommonResponse.success(tokenResponse);
    }

    // 크리에이터 이름 키워드 검색
    @GetMapping("/user/search")
    public CommonResponse<List<GetSearchCreatorResponse>> searchCreatorByKeyword(@RequestParam("keyword") String keyword) {
        return CommonResponse.success(userService.searchCreatorByKeyword(keyword));
    }

    // 특정 플랫폼에 포함된 크리에이터 조회
    @GetMapping("/user/platforms/{platformId}")
    public CommonResponse<Slice<GetSearchCreatorResponse>> searchCreatorByPlatform(@PathVariable("platformId") Long platformId, Pageable pageable) {
        return CommonResponse.success(userService.searchCreatorByPlatform(platformId, pageable));
    }

    @PostMapping(value = "/user/sign-up")
    public CommonResponse<PostSignUpResponse> signUp(@RequestBody @Valid final UserInfoRequest userInfoRequest){
        log.info("start");
        return CommonResponse.success(userService.signUp(userInfoRequest));
    }

    @PostMapping(value = "/user/sign-up/follow")
    public CommonResponse<PostSignUpResponse> signUpFollowBulk(@RequestBody @Valid final UserFollowRequest userFollowRequest){
        return CommonResponse.success(userService.followBulk(userFollowRequest));
    }

    @PostMapping(value = "/user/sign-up/interest")
    public CommonResponse<PostSignUpResponse> signUpInterestBulk(@RequestBody @Valid final UserInterestRequest userInterestRequest){
        return CommonResponse.success(userService.interestBulk(userInterestRequest));
    }
}
