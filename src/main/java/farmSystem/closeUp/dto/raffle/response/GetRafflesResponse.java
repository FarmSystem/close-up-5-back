package farmSystem.closeUp.dto.raffle.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetRafflesResponse {
    private Long raffleId;
    private String raffleTitle;
    private LocalDateTime raffleCreatedAt;
    private LocalDateTime raffleEndAt;
    private String raffleThumbnailUrl;

    public static GetRafflesResponse of(Long raffleId, String raffleTitle, LocalDateTime raffleCreatedAt, LocalDateTime raffleEndAt, String raffleThumbnailUrl) {
        return GetRafflesResponse.builder()
                .raffleId(raffleId)
                .raffleTitle(raffleTitle)
                .raffleCreatedAt(raffleCreatedAt)
                .raffleEndAt(raffleEndAt)
                .raffleThumbnailUrl(raffleThumbnailUrl)
                .build();
    }
}
