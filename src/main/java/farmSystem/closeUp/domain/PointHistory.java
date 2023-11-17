package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointhistory_id")
    private Long pointHistoryId;

    private Long balancePoint;
    private Long plusPoint;
    private Long minusPoint;
    private String raffleTitle;
    private String pointEventAt;
//    @Enumerated(EnumType.STRING)
//    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //일반 유저, 크리에이터

    @Builder
    public PointHistory(Long pointHistoryId, Long balancePoint, Long plusPoint, Long minusPoint, String raffleTitle, String pointEventAt) {
        this.pointHistoryId = pointHistoryId;
        this.balancePoint = balancePoint;
        this.plusPoint = plusPoint;
        this.minusPoint = minusPoint;
        this.raffleTitle = raffleTitle;
        this.pointEventAt = pointEventAt;
//        this.status = status;
    }

}
