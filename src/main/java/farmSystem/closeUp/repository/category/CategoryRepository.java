package farmSystem.closeUp.repository.category;

import farmSystem.closeUp.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    Optional<Category> findByCategoryId(Long id);
}
