package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductPaymentResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductResponse;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductsResponse;
import farmSystem.closeUp.dto.raffleProduct.response.PostRaffleProductResponse;
import farmSystem.closeUp.service.RaffleProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RaffleProductController {

    private final RaffleProductService raffleProductService;

    // 회원님이 팔로우하는 크리에이터 래플 목록 조회
    @GetMapping("/user/following-raffle-products")
    public CommonResponse<Slice<GetRaffleProductsResponse>> getFollowingRaffleProducts(Pageable pageable) throws AuthenticationException {
        return CommonResponse.success(raffleProductService.getFollowingRaffleProducts(pageable));
    }

    // 전체 래플 목록 조회
    @GetMapping("/user/raffle-products")
    public CommonResponse<Slice<GetRaffleProductsResponse>> getRaffleProducts(Pageable pageable) {
        return CommonResponse.success(raffleProductService.getRaffleProducts(pageable));
    }

    // 추천 래플 목록 ??
//    @GetMapping("/user/recommend-raffle-products/")
//    public CommonResponse<GetRaffleProductsResponse> getRecommendRaffleProducts(Pageable pageable) {
//        return CommonResponse.success(raffleProductService.getRecommendRaffleProducts);
//    }

    // 래플 상세 조회
    @GetMapping("/user/raffle-products/{raffleProductId}")
    public CommonResponse<GetRaffleProductResponse> getRaffleProduct(@PathVariable("raffleProductId") Long raffleProductId){
        return CommonResponse.success(raffleProductService.getRaffleProduct(raffleProductId));
    }

    // 크리에이터 탭안에서 크리에이터 래플 조회 (무한 스크롤)
    @GetMapping("/user/{creatorId}/raffle-products")
    public CommonResponse<Slice<GetRaffleProductsResponse>> getCreatorRaffleProducts(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(raffleProductService.getCreatorRaffleProducts(creatorId, pageable));
    }

    // 래플 상세 페이지에서 신청하기 버튼 클릭 - 응모 페이지 조회
    @GetMapping("/user/raffle-products/{raffleProductId}/order")
    public CommonResponse<GetRaffleProductPaymentResponse> getOrder(@PathVariable("raffleProductId") Long raffleProductId){
        return CommonResponse.success(raffleProductService.getOrder(raffleProductId));
    }

    // 래플 응모 유저 정보 수정하기



    // 래플 응모하기 - 포인트 차감
    @PostMapping("/user/raffle-products/{raffleProductId}/order")
    public CommonResponse<PostRaffleProductResponse> postRaffleProduct(@PathVariable("raffleProductId") Long raffleProductId){
        return CommonResponse.success(raffleProductService.postRaffleProduct(raffleProductId));
    }
}
