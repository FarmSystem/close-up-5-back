package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy.MM.dd")
    private LocalDate endDate;

    private String content;
    private Long winnerCount;
    private Long rafflePrice;
    private String address;
    @DateTimeFormat(pattern = "yyyy.MM.dd HH:mm:ss")
    private LocalDateTime winningDate;

    @Column(length = 20000)
    private String thumbnailImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User creator; //크리에이터

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id")
    private Category category;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="winningProduct_id")
    private WinningProduct winningProduct;

    @Builder
    public RaffleProduct(Long raffleProductId, String title, LocalDate startDate, LocalDate endDate, String content, Long winnerCount, Long rafflePrice, String address, LocalDateTime winningDate, String thumbnailImageUrl, User creator, Category category) {
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
        this.creator = creator;
        this.category = category;
    }
  
    public RaffleProduct(Long raffleProductId, String title, LocalDate startDate, LocalDate endDate, String content, Long winnerCount, Long rafflePrice, String address, LocalDateTime winningDate, String thumbnailImageUrl) {
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
        this.creator = creator;
        this.category = category;
    }

    public void setWinningProduct(WinningProduct winningProduct) {
        this.winningProduct = winningProduct;
    }
}
