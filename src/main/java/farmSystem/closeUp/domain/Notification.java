package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    private String notificationTitle; // 제목이 필요한가?
    private String notificationContent;
    private String notificationThumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User creator; // 크리에이터

    @Builder
    public Notification(Long notificationId, String notificationTitle, String notificationContent, String notificationThumbnailUrl){
        this.notificationId = notificationId;
        this.notificationTitle = notificationTitle;
        this.notificationContent = notificationContent;
        this.notificationThumbnailUrl = notificationThumbnailUrl;
    }

    public void setCreator(User creator) { this.creator = creator; }

}