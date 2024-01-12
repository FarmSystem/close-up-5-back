package farmSystem.closeUp.repository.raffleProduct;

import farmSystem.closeUp.domain.RaffleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface RaffleProductRepository extends JpaRepository<RaffleProduct, Long>, RaffleProductRepositoryCustom {

    List<RaffleProduct> findByWinningDate(LocalDateTime winningDate);

//    List<RaffleProduct> findByWinningDate(LocalDate winningDate);

//    @Query("SELECT r FROM RaffleProduct r WHERE FUNCTION('to_char', r.winningDate, 'YYYY-MM-DD') = :winningDate")
//    List<RaffleProduct> findByWinningDate(LocalDate winningDate);
}
