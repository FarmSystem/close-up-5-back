package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.category.GetCategoriesResponse;
import farmSystem.closeUp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/creator/categories")
    public CommonResponse<List<GetCategoriesResponse>> getCreatorCategories() {
        return CommonResponse.success(categoryService.getCreatorCategories());
    }
}
