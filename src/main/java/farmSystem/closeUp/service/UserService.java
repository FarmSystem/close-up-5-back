package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.config.security.SecurityUtils;
import farmSystem.closeUp.domain.*;
import farmSystem.closeUp.dto.request.UserFollowRequest;
import farmSystem.closeUp.dto.request.UserInfoRequest;
import farmSystem.closeUp.dto.request.UserInterestRequest;
import farmSystem.closeUp.dto.request.UserRequestTest;
import farmSystem.closeUp.dto.response.UserResponseTest;
import farmSystem.closeUp.dto.user.response.GetSearchCreatorResponse;
import farmSystem.closeUp.dto.user.response.PostTokenReissueResponse;
import farmSystem.closeUp.repository.UserInterestRepository;
import farmSystem.closeUp.repository.follow.FollowRepository;
import farmSystem.closeUp.repository.user.UserRepository;
import farmSystem.closeUp.repository.user.UserRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    private final FollowRepository followRepository;
    private final UserInterestRepository userInterestRepository;
    private final RedisUtils redisUtils;
    private final JwtService jwtService;

    @Transactional
    public List<GetSearchCreatorResponse> searchCreatorByKeyword(String keyword){
        List<GetSearchCreatorResponse> searchCreatorResponses = new ArrayList<>();

        List<User> findCreators = userRepository.findByNickNameContainingAndUserRole(keyword, UserRole.CREATOR);

        for (User findCreator : findCreators) {
            searchCreatorResponses.add(GetSearchCreatorResponse.of(findCreator.getUserId(), findCreator.getNickName(), findCreator.getProfileImageUrl(), findCreator.getProfileComment()));
        }
        return searchCreatorResponses;
    }

    @Transactional
    public Slice<GetSearchCreatorResponse> searchCreatorByPlatform(Long platformId, Pageable pageable){
        List<GetSearchCreatorResponse> searchCreatorResponses = new ArrayList<>();

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Slice<GetSearchCreatorResponse> findCreators = userRepositoryImpl.findByPlatform(platformId, pageable);

        return findCreators;
    }


    @Transactional
    public PostTokenReissueResponse reIssueToken(String refreshToken) {

        log.info("reissue start");

        // Bearer 뺀 토큰 자체
        refreshToken = jwtService.extractToken(refreshToken);
        // 클라이언트가 보낸 토큰의 클레임으로 있는 userId를 가져옴.
        Long userId = jwtService.getUserId(refreshToken);

        // userId로 조회한 redis 상의 토큰 value
        String findRefreshToken = redisUtils.getData(String.valueOf(userId));
        log.info(findRefreshToken);

        // 현재 사용자와 토큰에 적힌 사용자가 다르면 올바르지 않은 접근
        if (!findRefreshToken.equals(refreshToken)){
            redisUtils.deleteData(String.valueOf(userId));
            //로그아웃
            throw new CustomException(Result.INVALID_TOKEN);
        }

        String newRefreshToken = jwtService.createRefreshToken(userId);
        String newAccessToken = jwtService.createAccessToken(userId);
        return PostTokenReissueResponse.of(newAccessToken, newRefreshToken);
    }


    // 아래 두 메서드는 토큰이 살아있는 동안 사용
    public Long getCurrentUserId() throws AuthenticationException {
        return SecurityUtils.getCurrentUserId();
    }


    // 현재 사용자 조회
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        try {
            return userRepository
                    .findById(SecurityUtils.getCurrentUserId())
                    .orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        } catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_USER);
        }
    }

    // 추가 회원가입
    @Transactional
    public UserResponseTest signUp(UserRequestTest userRequestTest){
        User currentUser = getCurrentUser();
        currentUser.signUp(userRequestTest.getNickname(), userRequestTest.getAddress(), userRequestTest.getPhoneNumber(), userRequestTest.getProfileImageUrl(), userRequestTest.getGender(), userRequestTest.getBirthday());
        currentUser.authorizeUser(UserRole.USER);
        return UserResponseTest.builder().userId(currentUser.getUserId()).build();
    }

    //권한 확인용
    @Transactional(readOnly = true)
    public User getUser(Long userId){
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        return user;
    }

    @Transactional
    public boolean signUp(UserInfoRequest userInfoRequest) throws Exception {
        Long userId = null;
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }

        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        // 닉네임 중복 체크
        boolean isUserNickName = userRepository.findByNickName(userInfoRequest.getNickname()).isEmpty();
        if (!isUserNickName) throw new CustomException(Result.USERNAME_DUPLICATION);

        // 회원가입 레벨 통과
        user.update(
            userId,
            userInfoRequest.getNickname(),
            userInfoRequest.getAddress(),
            userInfoRequest.getPhoneNumber(),
            userInfoRequest.getProfileImageUrl(),
            userInfoRequest.getGender(),
            userInfoRequest.getBirthday(),
            UserRole.SIGNUP_USER
        );

        return true;
    }

    @Transactional
    public Boolean followBulk(UserFollowRequest userFollowRequest) throws Exception {
        Long userId = null;
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }
        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        for (Long creatorId : userFollowRequest.getCreatorId()) {
            User creator = User.builder().userId(creatorId).build();
            Follow followCreator = Follow.builder()
                    .user(user)
                    .creator(creator)
                    .build();
            followRepository.save(followCreator);
        }

        user.update(userId, UserRole.FOLLOWED_USER);
        return true;
    }

    @Transactional
    public Boolean interestBulk(UserInterestRequest userInterestRequest) throws Exception {
        Long userId = null;
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }

        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        for (Long interestId : userInterestRequest.getGenreId()) {
            Interest interest = Interest.builder().interestId(interestId).build();
            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();
            userInterestRepository.save(userInterest);
        }

        user.update(userId, UserRole.INTERESTED_USER);
        return true;
    }

}
