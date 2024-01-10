package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.config.s3.S3Uploader;
import farmSystem.closeUp.domain.*;
import farmSystem.closeUp.dto.raffleProduct.request.PostCreateRaffleProductRequest;
import farmSystem.closeUp.dto.raffleProduct.response.*;
import farmSystem.closeUp.repository.category.CategoryRepository;
import farmSystem.closeUp.repository.follow.FollowRepository;
import farmSystem.closeUp.repository.pointHistory.PointHistoryRepository;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepositoryImpl;
import farmSystem.closeUp.repository.winningProduct.WinningProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.AuthenticationException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleProductService {
    @Autowired
    private S3Uploader s3Uploader;

    private final RaffleProductRepository raffleProductRepository;
    private final RaffleProductRepositoryImpl raffleProductRepositoryImpl;
    private final FollowRepository followRepository;
    private final UserService userService;
    private final RaffleRepository raffleRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final CategoryRepository categoryRepository;
    private final WinningProductRepository winningProductRepository;


    // 회원님이 팔로우하는 크리에이터 래플 목록 조회
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getFollowingRaffleProducts(Pageable pageable) {

        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        List<Follow> followList = followRepository.findByUserUserId(userId);
        List<Long> creatorIds = null;

        // 아무도 팔로우 안한 경우 빈 리스트 반환
        if (followList.isEmpty()) {
            creatorIds = Collections.emptyList();
        } else {
            creatorIds = followList
                    .stream()
                    .map(follow -> follow.getCreator().getUserId())
                    .collect(Collectors.toList());
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        try {
            Slice<GetRaffleProductsResponse> followingRaffleProducts = raffleProductRepositoryImpl.findFollowingRaffleProducts(creatorIds, pageable);
            return followingRaffleProducts;
        } catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_RAFFLE);
        }
    }

    // 전체 래플 목록 조회
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getRaffleProducts(Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        try {
            Slice<GetRaffleProductsResponse> raffleProducts = raffleProductRepositoryImpl.findRaffleProducts(pageable);
            return raffleProducts;
        } catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_RAFFLE);
        }
    }

    // 래플 상세 조회
    @Transactional(readOnly = true)
    public GetRaffleProductResponse getRaffleProduct(Long raffleProductId) {
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));
        GetRaffleProductResponse getRaffleProductResponse = GetRaffleProductResponse.
                of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getContent(), raffleProduct.getWinnerCount(), raffleProduct.getRafflePrice(), raffleProduct.getWinningDate(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getCreator().getNickName(), raffleProduct.getCreator().getUserId());
        return getRaffleProductResponse;
    }

    // 래플 응모 페이지(주문 정보 조회)
    @Transactional(readOnly = true)
    public GetRaffleProductApplyResponse getOrder(Long raffleProductId) {

        User user = userService.getCurrentUser();
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));

        // 만약 해당 래플 상품의 응모 마감기한이 지났다면 응모 페이지 리다이렉트 불가
        if (raffleProduct.getEndDate().isBefore(LocalDate.now())) {
            throw new CustomException(Result.RAFFLE_END);
        }

        GetRaffleProductApplyResponse getRaffleProductApplyResponse =
                GetRaffleProductApplyResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getRafflePrice(), raffleProduct.getThumbnailImageUrl(), user.getNickName(), user.getPhoneNumber(), user.getAddress(), user.getPoint());

        return getRaffleProductApplyResponse;
    }

    // 래플 신청 완료(포인트 차감)
    @Transactional
    public PostRaffleProductResponse postRaffleProduct(Long raffleProductId) {

        // 현재 회원 조회
        User user = userService.getCurrentUser();

        // 래플 상품 조회
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));

        // 만약 해당 래플 상품의 응모 마감기한이 지났다면 신청 불가능
        if (raffleProduct.getEndDate().isBefore(LocalDate.now())) {
            throw new CustomException(Result.RAFFLE_END);
        }

        if (user.getPoint() - raffleProduct.getRafflePrice() < 0) {
            throw new CustomException(Result.NOT_ENOUGH_POINT);
        }

        // 래플 신청 처리
        Raffle raffle = Raffle.builder()
                .winningInfo(WinningInfo.NONE) //아직 아무 상태가 아님
                .build();
        raffleRepository.save(raffle);
        raffle.setRaffleProduct(raffleProduct);
        raffle.setUser(user);

        // 포인트 차감
        user.minusPoint(raffleProduct.getRafflePrice());

        // 포인트 history 처리
        PointHistory pointHistory = PointHistory.builder()
                .minusPoint(raffleProduct.getRafflePrice())
                .pointHistoryName(raffleProduct.getTitle() + "응모")
                .pointEventAt(LocalDateTime.now())
                .build();
        pointHistoryRepository.save(pointHistory);
        pointHistory.setUser(user);

        return PostRaffleProductResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getRafflePrice(), raffleProduct.getTitle(), user.getAddress());
    }

    // 크리에이터 래플 조회 (무한 스크롤)
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getCreatorRaffleProducts(Long creatorId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetRaffleProductsResponse> findRaffles = raffleProductRepositoryImpl.findCreatorRaffleProducts(creatorId, pageable);

        return findRaffles;
    }

    // 크리에이터 - 래플 상품 생성
    @Transactional
    public PostCreateRaffleProductResponse postCreateRaffleProduct(MultipartFile thumbnailImage, MultipartFile attachedFile, PostCreateRaffleProductRequest postCreateRaffleProductRequest) throws IOException {
        // 현재 크리에이터 조회
        User user = userService.getCurrentUser();

        log.info("category");
        log.info(postCreateRaffleProductRequest.getCategoryId().toString());
        // 카테고리 조회
        Category category = categoryRepository.findById(postCreateRaffleProductRequest.getCategoryId()).orElseThrow(() -> new CustomException(Result.NOTFOUND_CATEGORY));

        // 당첨자 발표 시간 구하기 - 래플 종료 일 다음날 낮 12시
        LocalDate endDate = postCreateRaffleProductRequest.getEndDate();
        LocalDateTime winningDate = endDate.plusDays(1).atTime(12, 0, 0);

        // 섬네일 이미지 s3에 저장
        String thumbnailImageUrl = null;
        String attachedFileUrl = null;
        String attachedFileName = null;
        if(!thumbnailImage.isEmpty()) {
            thumbnailImageUrl = s3Uploader.upload(thumbnailImage, thumbnailImage.getOriginalFilename()+ UUID.randomUUID());
            attachedFileName = attachedFile.getOriginalFilename() + UUID.randomUUID();
            attachedFileUrl = s3Uploader.upload(attachedFile, attachedFileName);

        }

        WinningProduct winningProduct = WinningProduct.builder()
                .uploadFileName(attachedFileName)
                .originalFileName(attachedFile.getOriginalFilename())
                .fileUrl(attachedFileUrl)
                .build();

        winningProductRepository.save(winningProduct);

        // 래플 상품 생성
        RaffleProduct raffleProduct = RaffleProduct.builder()
                .title(postCreateRaffleProductRequest.getTitle())
                .startDate(postCreateRaffleProductRequest.getStartDate())
                .endDate(postCreateRaffleProductRequest.getEndDate())
                .winningDate(LocalDateTime.from(winningDate))
                .content(postCreateRaffleProductRequest.getContent())
                .winnerCount(postCreateRaffleProductRequest.getWinnerCount())
                .rafflePrice(postCreateRaffleProductRequest.getRafflePrice())
                .thumbnailImageUrl(thumbnailImageUrl)
                .creator(user)
                .category(category)
                .build();

        raffleProductRepository.save(raffleProduct);

        raffleProduct.setWinningProduct(winningProduct);
        return PostCreateRaffleProductResponse.of(winningDate, postCreateRaffleProductRequest.getStartDate(), postCreateRaffleProductRequest.getEndDate());
    }
}
