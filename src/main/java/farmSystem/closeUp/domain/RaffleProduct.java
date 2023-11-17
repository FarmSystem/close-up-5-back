package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RaffleProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raffleProduct_id")
    private Long raffleProductId;

    private String title;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime endDate;

    private String content;
    private Long winnerCount;
    private Long rafflePrice;
    private String address;
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime winningDate;

    private String thumbnailImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User creator; //크리에이터

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @Builder
    public RaffleProduct(Long raffleProductId, String title, LocalDateTime startDate, LocalDateTime endDate, String content, Long winnerCount, Long rafflePrice, String address, LocalDateTime winningDate, String thumbnailImageUrl) {
        this.raffleProductId = raffleProductId;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
        this.winnerCount = winnerCount;
        this.rafflePrice = rafflePrice;
        this.address = address;
        this.winningDate = winningDate;
        this.thumbnailImageUrl = thumbnailImageUrl;
    }
}
