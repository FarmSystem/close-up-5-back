package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetRaffleProductResponse {

    private Long raffleProductId;
    private String raffleProductTitle;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String raffleProductContent;
    private Long winnerCount;
    private Long raffleProductPrice;
    private LocalDateTime winningDate;
    private String raffleProductThumbnail;
    private String creatorName;
    private Long creatorId;

    public static GetRaffleProductResponse of(Long raffleProductId, String raffleProductTitle, LocalDateTime startDate,
                                               LocalDateTime endDate, String raffleProductContent, Long winnerCount,
                                               Long raffleProductPrice, LocalDateTime winningDate, String raffleProductThumbnail,
                                               String creatorName, Long creatorId) {
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
                .build();
    }
}
