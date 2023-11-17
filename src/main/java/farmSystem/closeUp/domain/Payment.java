package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long paymentId;

    private Long chargePoint;
    private String merchantId;
    private String impUid;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime createAt;
    private LocalDateTime failedAt;
    private LocalDateTime paidAt;
    private String failedReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public Payment(Long paymentId, Long chargePoint, String merchantId, String impUid, Status status, LocalDateTime createAt, LocalDateTime failedAt, LocalDateTime paidAt, String failedReason) {
        this.paymentId = paymentId;
        this.chargePoint = chargePoint;
        this.merchantId = merchantId;
        this.impUid = impUid;
        this.status = status;
        this.createAt = createAt;
        this.failedAt = failedAt;
        this.paidAt = paidAt;
        this.failedReason = failedReason;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    // 포인트 충전 금액과 실제 결제 금액이 같은 경우 호출
    public void successPaymentCharge(String impUid, Status status, LocalDateTime paidAt){
        this.impUid = impUid;
        this.status = status;
        this.paidAt = paidAt;
    }

    public void failPaymentCharge(String impUid, Status status, LocalDateTime failedAt, String failedReason){
        this.impUid = impUid;
        this.status = status;
        this.failedAt = failedAt;
        this.failedReason = failedReason;
    }

    public void setUser(User user){
        this.user = user;
    }


}
