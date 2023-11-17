package farmSystem.closeUp.controller;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.payment.request.PostPaymentVerifyRequest;
import farmSystem.closeUp.dto.payment.response.PostMerchantIdReponse;
import farmSystem.closeUp.dto.payment.response.PostPaymentVerifyResponse;
import farmSystem.closeUp.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Slf4j
public class PaymentController {

    //토큰 발급을 위해 아임포트에서 제공해주는 rest api 사용.(gradle로 의존성 추가)
    private final IamportClient iamportClient;
    private final PaymentService paymentService;

    //생성자로 rest api key와 secret을 입력해서 토큰 바로생성.
    public PaymentController(@Value("${pgmodule.app-id}") String apiKey,
                             @Value("${pgmodule.secret-key}") String apiSecret, PaymentService paymentService) {
        this.paymentService = paymentService;
        this.iamportClient = new IamportClient(apiKey, apiSecret);
    }

    // 포인트 충전 금액 설정 및 주문 번호 생성
    @PostMapping("/user/point-charge")
    public CommonResponse<PostMerchantIdReponse> chargePoint(@RequestParam("amount") Long amount) {
        return CommonResponse.success(paymentService.chargePoint(amount));
    }

    // 포인트 충전 금액 검증
    @PostMapping("/user/verifyIamport")
    public CommonResponse<PostPaymentVerifyResponse> verifyIamport(@RequestBody PostPaymentVerifyRequest postPaymentVerifyRequest) throws IamportResponseException, IOException {
        return CommonResponse.success(paymentService.verifyIamportService(postPaymentVerifyRequest,iamportClient));
    }
}
