package farmSystem.closeUp.repository.raffle;

import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface RaffleRepositoryCustom {

    Slice<GetRafflesResponse> findByRaffles(Long creatorId, Pageable pageable);

}
