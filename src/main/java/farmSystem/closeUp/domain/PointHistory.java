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
public class PointHistory extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pointhistory_id")
    private Long pointHistoryId;

    private Long plusPoint;
    private Long minusPoint;
    private String pointHistoryName;
    private LocalDateTime pointEventAt;
//    @Enumerated(EnumType.STRING)
//    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //일반 유저, 크리에이터

    @Builder
    public PointHistory(Long plusPoint, Long minusPoint, String pointHistoryName, LocalDateTime pointEventAt) {
        this.plusPoint = plusPoint;
        this.minusPoint = minusPoint;
        this.pointHistoryName = pointHistoryName;
        this.pointEventAt = pointEventAt;
//        this.status = status;
    }

    public void setUser(User user){
        this.user = user;
    }
}
