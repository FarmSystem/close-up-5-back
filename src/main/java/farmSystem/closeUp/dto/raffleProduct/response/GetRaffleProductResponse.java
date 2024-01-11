package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class GetRaffleProductResponse {

    private Long raffleProductId;
    private String raffleProductTitle;
    private LocalDate startDate;
    private LocalDate endDate;
    private String raffleProductContent;
    private Long winnerCount;
    private Long raffleProductPrice;
    private LocalDateTime winningDate;
    private String raffleProductThumbnail;
    private String creatorName;
    private Long creatorId;
    private String creatorProfileImage;

    public static GetRaffleProductResponse of(Long raffleProductId, String raffleProductTitle, LocalDate startDate,
                                               LocalDate endDate, String raffleProductContent, Long winnerCount,
                                               Long raffleProductPrice, LocalDateTime winningDate, String raffleProductThumbnail,
                                               String creatorName, Long creatorId, String creatorProfileImage) {
        return GetRaffleProductResponse.builder()
                .raffleProductId(raffleProductId)
                .raffleProductTitle(raffleProductTitle)
                .startDate(startDate)
                .endDate(endDate)
                .raffleProductContent(raffleProductContent)
                .winnerCount(winnerCount)
                .raffleProductPrice(raffleProductPrice)
                .winningDate(winningDate)
                .raffleProductThumbnail(raffleProductThumbnail)
                .creatorName(creatorName)
                .creatorId(creatorId)
                .creatorProfileImage(creatorProfileImage)
                .build();
    }
}
