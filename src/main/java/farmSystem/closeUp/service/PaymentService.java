package farmSystem.closeUp.service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.PointHistory;
import farmSystem.closeUp.domain.Status;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.payment.request.PostPaymentVerifyRequest;
import farmSystem.closeUp.dto.payment.response.PostMerchantIdReponse;
import farmSystem.closeUp.dto.payment.response.PostPaymentVerifyResponse;
import farmSystem.closeUp.repository.PointHistoryRepository;
import farmSystem.closeUp.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final UserService userService;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional
    public PostPaymentVerifyResponse verifyIamportService(PostPaymentVerifyRequest postPaymentVerifyRequest, IamportClient iamportClient) throws IamportResponseException, IOException {
        // imp_uid로 아임포트 서버쪽에 결제된 정보 조회.
        IamportResponse<Payment> paymentIamportResponse = iamportClient.paymentByImpUid(postPaymentVerifyRequest.getImp_uid());
        Long payAmount = paymentIamportResponse.getResponse().getAmount().longValue();

        // db에서 상품 가격 조회
        String merchantId = postPaymentVerifyRequest.getMerchantId();
        farmSystem.closeUp.domain.Payment payment = paymentRepository.findByMerchantId(merchantId);
        Long chargePoint = payment.getChargePoint();

        // 두개 가격 동일한지 비교
        if (payAmount==chargePoint){
            // 동일하면 포인트 적립 처리 및 결제 정보 저장
            payment.successPaymentCharge(postPaymentVerifyRequest.getImp_uid(), Status.PLUS, LocalDateTime.now());
            PointHistory pointHistory = PointHistory.builder().plusPoint(chargePoint).pointHistoryName("포인트 충전").pointEventAt(LocalDateTime.now()).build();
            pointHistoryRepository.save(pointHistory);
            pointHistory.setUser(userService.getCurrentUser());
        }else {
            payment.failPaymentCharge(postPaymentVerifyRequest.getImp_uid(), Status.FAIL, LocalDateTime.now(), "결제 금액과 충전 금액이 일치하지 않습니다.");
            throw new CustomException(Result.NOT_EQUAL_AMOUNT);
        }
        return PostPaymentVerifyResponse.of(payment.getPaymentId(), payment.getMerchantId(), payment.getChargePoint(), payment.getStatus(), payment.getPaidAt());
    }

    public PostMerchantIdReponse chargePoint(Long amount){
        User user = userService.getCurrentUser();
        farmSystem.closeUp.domain.Payment payment;
        if (amount<5000){
            throw new CustomException(Result.LESS_THAN_MINIMUM_POINT);
        }else {
//            payment = farmSystem.closeUp.domain.Payment.builder().chargePoint(amount).status(Status.NONE).merchantId("merchant_" + new Date().getTime()).createAt(LocalDateTime.now()).build();
            //위의 코드가 실제 코드 아래는 테스팅 코드
            payment = farmSystem.closeUp.domain.Payment.builder().chargePoint(amount).status(Status.NONE).merchantId("1").createAt(LocalDateTime.now()).build();

            payment.setUser(user);
            paymentRepository.save(payment);
        }
        return PostMerchantIdReponse.of(payment.getMerchantId(), payment.getChargePoint(), payment.getCreateAt());
    }
}
