package farmSystem.closeUp.dto.user.response;

import farmSystem.closeUp.domain.UserRole;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostSignUpResponse {
    private Long userId;
    private UserRole userRole;

    public static PostSignUpResponse of(Long userId, UserRole userRole){
        return PostSignUpResponse.builder()
                .userId(userId)
                .userRole(userRole)
                .build();
    }
}
