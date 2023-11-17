package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostRaffleProductResponse {

    private Long raffleProductId;
    private Long raffleProductPrice;
    private String raffleProductTitle;
    private String orderAddress;

    public static PostRaffleProductResponse of(Long raffleProductId, Long raffleProductPrice, String raffleProductTitle, String orderAddress) {
        return PostRaffleProductResponse.builder()
                .raffleProductId(raffleProductId)
                .raffleProductPrice(raffleProductPrice)
                .raffleProductTitle(raffleProductTitle)
                .orderAddress(orderAddress)
                .build();
    }
}
