package farmSystem.closeUp.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostCreatorInfoRequest {
    @NotBlank(message = "닉네임 입력은 필수입니다.")
    private String nickname;
    @NotBlank(message = "주소 입력은 필수입니다.")
    private String address;
    @NotBlank(message = "휴대전화번호 입력은 필수입니다.")
    private String phoneNumber;
    @NotBlank(message = "한줄소개는 필수입니다.")
    private String profileComment;
}