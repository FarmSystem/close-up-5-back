package farmSystem.closeUp.dto.raffleProduct.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class PostCreateRaffleProductResponse {
    private LocalDateTime winnerDateTime;
    private LocalDate startDate;
    private LocalDate endDate;

    public static PostCreateRaffleProductResponse of(LocalDateTime winnerDateTime, LocalDate startDate, LocalDate endDate) {
        return PostCreateRaffleProductResponse.builder()
                .winnerDateTime(winnerDateTime)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
