package farmSystem.closeUp.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class WinningProduct extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "winningProduct_id")
    private Long winningProductId;

    // 무형의 경우(사진, 영상, 음성)
    private String uploadFileName;
    private String originalFileName;
    @Column(length = 20000)
    private String fileUrl;


}
