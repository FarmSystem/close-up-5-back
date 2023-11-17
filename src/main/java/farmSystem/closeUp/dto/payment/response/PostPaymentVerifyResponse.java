package farmSystem.closeUp.dto.payment.response;

import farmSystem.closeUp.domain.Status;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PostPaymentVerifyResponse {

    private Long paymentId;
    private String merchantId;
    private Long chargePoint;
    private Status status;
    private LocalDateTime paidAt;

    public static PostPaymentVerifyResponse of(Long paymentId, String merchantId, Long chargePoint, Status status, LocalDateTime paidAt){
        return PostPaymentVerifyResponse.builder()
                .paymentId(paymentId)
                .merchantId(merchantId)
                .chargePoint(chargePoint)
                .status(status)
                .paidAt(paidAt)
                .build();
    }
}
