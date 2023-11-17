package farmSystem.closeUp.repository.raffleProduct;

import farmSystem.closeUp.domain.RaffleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleProductRepository extends JpaRepository<RaffleProduct, Long>, RaffleProductRepositoryCustom {
}
