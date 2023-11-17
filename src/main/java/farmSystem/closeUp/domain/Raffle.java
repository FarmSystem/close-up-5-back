package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Raffle extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raffle_id")
    private Long raffleId;

    @Enumerated(value = EnumType.STRING)
    private WinningInfo winningInfo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "raffleProduct_id")
    private RaffleProduct raffleProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; //일반 유저

    @Builder
    public Raffle(WinningInfo winningInfo) {
        this.winningInfo = winningInfo;
    }

    public void setRaffleProduct(RaffleProduct raffleProduct) {
        this.raffleProduct = raffleProduct;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
