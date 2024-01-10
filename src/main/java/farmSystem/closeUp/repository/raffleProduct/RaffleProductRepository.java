package farmSystem.closeUp.repository.raffleProduct;

import farmSystem.closeUp.domain.RaffleProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RaffleProductRepository extends JpaRepository<RaffleProduct, Long>, RaffleProductRepositoryCustom {

    List<RaffleProduct> findByEndDate(LocalDate endDate);

    List<RaffleProduct> findByWinningDate(LocalDate winningDate);
}
