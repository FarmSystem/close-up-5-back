package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WinningProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winningProduct_id")
    private Long winningProductId;

    // 무형의 경우(사진, 영상, 음성)
    private String uploadFileName;
    private String originalFileName;
    private String FileUrl;


}
