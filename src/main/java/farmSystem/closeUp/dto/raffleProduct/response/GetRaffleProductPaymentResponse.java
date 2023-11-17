package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetRaffleProductPaymentResponse {

    private Long raffleProductId;
    private String raffleProductTitle;
    private Long raffleProductPrice;
    private String raffleProductThumbnailImage;
    private String userNickname;
    private String userPhoneNumber;
    private String userAddress;

    public static GetRaffleProductPaymentResponse of(Long raffleProductId, String raffleProductTitle, Long raffleProductPrice, String raffleProductThumbnailImage, String userNickname, String userPhoneNumber, String userAddress) {
        return GetRaffleProductPaymentResponse.builder()
                .raffleProductId(raffleProductId)
                .raffleProductTitle(raffleProductTitle)
                .raffleProductPrice(raffleProductPrice)
                .raffleProductThumbnailImage(raffleProductThumbnailImage)
                .userNickname(userNickname)
                .userPhoneNumber(userPhoneNumber)
                .userAddress(userAddress)
                .build();
    }
}
