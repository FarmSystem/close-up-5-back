package farmSystem.closeUp.dto.category;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetCategoriesResponse {

    private Long categoryId;
    private String categoryName;

    public static GetCategoriesResponse of(Long categoryId, String categoryName) {
        return GetCategoriesResponse.builder()
                .categoryId(categoryId)
                .categoryName(categoryName)
                .build();
    }

}
