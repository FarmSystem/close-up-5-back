package farmSystem.closeUp.dto.payment.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostMerchantIdReponse {

    private String merchantId;
    private Long chargePoint;
    private LocalDateTime createAt;

    public static PostMerchantIdReponse of(String merchantId, Long chargePoint, LocalDateTime createAt) {
        return PostMerchantIdReponse.builder()
                .merchantId(merchantId)
                .chargePoint(chargePoint)
                .createAt(createAt)
                .build();
    }
}
