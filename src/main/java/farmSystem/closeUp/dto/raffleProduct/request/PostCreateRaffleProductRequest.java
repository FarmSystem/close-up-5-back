package farmSystem.closeUp.dto.raffleProduct.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRaffleProductRequest {
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private String content;
    private Long winnerCount;
    private Long rafflePrice;
    private Long categoryId;
}
