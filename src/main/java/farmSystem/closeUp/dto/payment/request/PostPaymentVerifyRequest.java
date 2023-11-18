package farmSystem.closeUp.dto.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostPaymentVerifyRequest {

    private String imp_uid;
    private String merchantId;
    private String amount;
}
