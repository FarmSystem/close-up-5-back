package farmSystem.closeUp.repository.raffle;

import farmSystem.closeUp.domain.Raffle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RaffleRepository extends JpaRepository<Raffle, Long> {
}
