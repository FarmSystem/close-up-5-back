package farmSystem.closeUp.repository.raffleProduct;

import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductsResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface RaffleProductRepositoryCustom {

    Slice<GetRaffleProductsResponse> findRaffleProducts(Pageable pageable);
    Slice<GetRaffleProductsResponse> findFollowingRaffleProducts(List<Long> creatorIds, Pageable pageable);

    Slice<GetRaffleProductsResponse> findCreatorRaffleProducts(Long creatorId, Pageable pageable);
}
