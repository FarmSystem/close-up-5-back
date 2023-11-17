package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.*;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductPaymentResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductsResponse;
import farmSystem.closeUp.dto.raffleProduct.response.PostRaffleProductResponse;
import farmSystem.closeUp.repository.follow.FollowRepository;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleProductService {

    private final RaffleProductRepository raffleProductRepository;
    private final RaffleProductRepositoryImpl raffleProductRepositoryImpl;
    private final FollowRepository followRepository;
    private final UserService userService;
    private final RaffleRepository raffleRepository;

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
        }else {
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
        }
        catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_RAFFLE);
        }
    }

    // 전체 래플 목록 조회
    @Transactional(readOnly = true)
    public Slice<GetRaffleProductsResponse> getRaffleProducts(Pageable pageable){
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        try {
            Slice<GetRaffleProductsResponse> raffleProducts = raffleProductRepositoryImpl.findRaffleProducts(pageable);
            return raffleProducts;
        }catch (Exception e) {
            throw new CustomException(Result.NOTFOUND_RAFFLE);
        }
    }

    // 래플 상세 조회
    @Transactional(readOnly = true)
    public GetRaffleProductResponse getRaffleProduct(Long raffleProductId){
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));
        GetRaffleProductResponse getRaffleProductResponse = GetRaffleProductResponse.
                of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getContent(), raffleProduct.getWinnerCount(), raffleProduct.getRafflePrice(), raffleProduct.getWinningDate(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getCreator().getNickName(), raffleProduct.getCreator().getUserId());
        return getRaffleProductResponse;
    }

    // 래플 결제 페이지(주문 정보 조회)
    @Transactional(readOnly = true)
    public GetRaffleProductPaymentResponse getOrder(Long raffleProductId) {

        User user = userService.getCurrentUser();
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));

        GetRaffleProductPaymentResponse getRaffleProductPaymentResponse =
                GetRaffleProductPaymentResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getRafflePrice(), raffleProduct.getThumbnailImageUrl(), user.getNickName(), user.getPhoneNumber(), user.getAddress());

        return getRaffleProductPaymentResponse;
    }

    // 래플 신청 완료(결제 후 동작)
    @Transactional
    public PostRaffleProductResponse postRaffleProduct(Long raffleProductId) {

        // 현재 회원 조회
        User user = userService.getCurrentUser();
        RaffleProduct raffleProduct = raffleProductRepository.findById(raffleProductId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));

        // 래플 신청 처리
        Raffle raffle = Raffle.builder()
                .winningInfo(WinningInfo.NONE) //아직 아무 상태가 아님
                .build();

        raffleRepository.save(raffle);

        // 포인트 차감

        raffle.setRaffleProduct(raffleProduct);
        raffle.setUser(user);

        return PostRaffleProductResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getRafflePrice(), raffleProduct.getTitle(), user.getAddress());
    }
}
