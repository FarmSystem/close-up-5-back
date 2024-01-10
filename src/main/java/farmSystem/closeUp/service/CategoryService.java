package farmSystem.closeUp.service;

import farmSystem.closeUp.domain.Category;
import farmSystem.closeUp.dto.category.GetCategoriesResponse;
import farmSystem.closeUp.repository.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;


    public List<GetCategoriesResponse> getCreatorCategories() {
        List<Category> all = categoryRepository.findAll();

        ArrayList<GetCategoriesResponse> getCategoriesResponses = new ArrayList<>();

        for (Category category : all) {
            getCategoriesResponses.add(GetCategoriesResponse.of(category.getCategoryId(), category.getCategoryName()));
        }

        return getCategoriesResponses;
    }
}
