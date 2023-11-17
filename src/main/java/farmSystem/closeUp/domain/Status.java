package farmSystem.closeUp.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {
    PLUS("포인트 적립"), MINUS("포인트 차감"), REFUND("환불"), NONE("미처리"), FAIL("결제 실패");
    private final String key;
}