package farmSystem.closeUp.dto.profile.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;


@Getter
@Builder
public class GetProfileResponse {
    private Long creatorId;
    private String creatorName;
    private String creatorProfileImageUrl;
    private String creatorIntroduction;
    private Long totalPoint;

    public static GetProfileResponse of(Long creatorId, String creatorName, String creatorProfileImageUrl, String creatorIntroduction, Long totalPoint){
        return GetProfileResponse.builder()
                .creatorId(creatorId)
                .creatorName(creatorName)
                .creatorProfileImageUrl(creatorProfileImageUrl)
                .creatorIntroduction(creatorIntroduction)
                .totalPoint(totalPoint)
                .build();
    }


}
