package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.config.jwt.JwtService;
import farmSystem.closeUp.config.redis.RedisUtils;
import farmSystem.closeUp.config.security.SecurityUtils;
import farmSystem.closeUp.domain.*;
import farmSystem.closeUp.dto.creator.request.PostCreatorSettingRequest;
import farmSystem.closeUp.dto.profile.response.GetProfileResponse;
import farmSystem.closeUp.dto.user.request.PostCreatorInfoRequest;
import farmSystem.closeUp.dto.user.request.UserFollowRequest;
import farmSystem.closeUp.dto.user.request.UserInfoRequest;
import farmSystem.closeUp.dto.user.request.UserInterestRequest;
import farmSystem.closeUp.dto.user.response.GetSearchCreatorResponse;
import farmSystem.closeUp.dto.user.response.PostSignUpResponse;
import farmSystem.closeUp.dto.user.response.PostTokenReissueResponse;
import farmSystem.closeUp.repository.InterestRepository;
import farmSystem.closeUp.repository.UserInterestRepository;
import farmSystem.closeUp.repository.follow.FollowRepository;
import farmSystem.closeUp.repository.platform.PlatformRepository;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    private final InterestRepository interestRepository;
    private final PlatformRepository platformRepository;
    private final S3UploadService s3UploadService;

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
                    .findById(SecurityUtils.getCurrentUserId()).get();
        } catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_USER);
        }
    }

    //권한 확인용
    @Transactional(readOnly = true)
    public User getUser(Long userId){
        Optional<User> byId = userRepository.findById(userId);
        User user = byId.get();
        return user;
    }

    // 회원 추가 가입(개인 정보 입력)
    @Transactional
    public PostSignUpResponse signUp(UserInfoRequest userInfoRequest, MultipartFile profileImage){
        Long userId = null;

        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }

        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        // 닉네임 중복 체크
        if(userRepository.existsByNickName(userInfoRequest.getNickname())){
            new CustomException(Result.USERNAME_DUPLICATION);
        }

        String profileImageUrl = null;

        try {
            profileImageUrl = s3UploadService.saveFile(profileImage, profileImage.getName() + UUID.randomUUID());
        } catch (IOException e) {
            new CustomException(Result.FILE_UPLOAD_FAIL);
        }

        // 추가 정보 업데이트
        user.update(
            userId,
            userInfoRequest.getNickname(),
            userInfoRequest.getAddress(),
            userInfoRequest.getPhoneNumber(),
            profileImageUrl,
            UserRole.USER
        );

        log.info("유저 - 추가 가입 성공");

        return PostSignUpResponse.of(user.getUserId(), user.getUserRole());
    }

    @Transactional
    // 크리에이터 추가 가입
    public PostSignUpResponse signUpCreator(PostCreatorInfoRequest postCreatorInfoRequest, MultipartFile profileImage, MultipartFile verificationImage) {
        Long userId = null;
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }

        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        // 닉네임 중복 체크
        if(userRepository.existsByNickName(postCreatorInfoRequest.getNickname())){
            new CustomException(Result.USERNAME_DUPLICATION);
        }

        String profileImageUrl = null;
        String verificationImageUrl = null;

        try {
            profileImageUrl = s3UploadService.saveFile(profileImage, profileImage.getName() + UUID.randomUUID());
            verificationImageUrl = s3UploadService.saveFile(verificationImage, verificationImage.getName()+UUID.randomUUID());
        } catch (IOException e) {
            new CustomException(Result.FILE_UPLOAD_FAIL);
        }

        // 회원가입 레벨 통과
        user.update(
                userId,
                postCreatorInfoRequest.getNickname(),
                postCreatorInfoRequest.getAddress(),
                postCreatorInfoRequest.getPhoneNumber(),
                profileImageUrl,
                postCreatorInfoRequest.getProfileComment(),
                verificationImageUrl,
                UserRole.CREATOR
        );
        log.info("크리에이터 추가 가입 완료");

        return PostSignUpResponse.of(user.getUserId(), user.getUserRole());
    }

    @Transactional
    public PostSignUpResponse followBulk(UserFollowRequest userFollowRequest){
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

        return PostSignUpResponse.of(user.getUserId(), user.getUserRole());
    }

    @Transactional
    public PostSignUpResponse interestBulk(UserInterestRequest userInterestRequest){
        Long userId = null;
        try {
            userId = getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.INVALID_ACCESS);
        }

        // 만약 유저 존재 안할 경우 에러
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));
        for (Long interestId : userInterestRequest.getGenreId()) {
            Interest interest = interestRepository.findById(interestId).orElseThrow(() -> new CustomException(Result.NOTFOUND_INTEREST));
            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();
            userInterestRepository.save(userInterest);
        }

        user.update(userId, UserRole.USER);
//        userRepository.save(user);

        return PostSignUpResponse.of(user.getUserId(), user.getUserRole());
    }

    @Transactional
    // 플랫폼 설정 및 관심사 설정
    public PostSignUpResponse creatorSetting(PostCreatorSettingRequest postCreatorSettingRequest, MultipartFile multipartFile) {
        User user;
        user = getCurrentUser();
        Platform platform = platformRepository.findById(postCreatorSettingRequest.getPlatformId()).orElseThrow(() -> new CustomException(Result.NOTFOUND_PLATFORM));
        user.setPlatform(platform); // 크리에이터 플랫폼 설정

        // 크리에이터 장르(관심사) 설정
        for (Long interestId :postCreatorSettingRequest.getInterestIds()) {
            Interest interest = interestRepository.findById(interestId).orElseThrow(() -> new CustomException(Result.NOTFOUND_INTEREST));
            UserInterest userInterest = UserInterest.builder()
                    .user(user)
                    .interest(interest)
                    .build();
            userInterestRepository.save(userInterest);
        }

        // 크리에이터 본인인증
        String fileName = "creator_verification/"+ UUID.randomUUID()+ multipartFile.getOriginalFilename();


        try {
            s3UploadService.saveFile(multipartFile, fileName);
        } catch (IOException e) {
            new CustomException(Result.FILE_UPLOAD_FAIL);
        }

        user.setVerificationImageUrl(fileName);
        user.authorizeUser(UserRole.CREATOR);

        return PostSignUpResponse.of(user.getUserId(), user.getUserRole());
    }

    public GetProfileResponse getUserProfile() {
        User user = getCurrentUser();
        return GetProfileResponse.of(user.getUserId(), user.getNickName(), user.getProfileImageUrl(), user.getProfileComment(), user.getPoint());
    }
}
