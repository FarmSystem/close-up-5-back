package farmSystem.closeUp.repository.raffleProduct;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.RaffleProduct;
import farmSystem.closeUp.dto.raffleProduct.response.GetRaffleProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QRaffleProduct.raffleProduct;

@RequiredArgsConstructor
public class RaffleProductRepositoryImpl implements RaffleProductRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetRaffleProductsResponse> findRaffleProducts(Pageable pageable) {
        List<RaffleProduct> findRaffleProducts = queryFactory
                .selectFrom(raffleProduct)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(raffleProduct.createdAt.desc())
                .fetch();

        List<GetRaffleProductsResponse> result = new ArrayList<>();

        for (RaffleProduct raffleProduct : findRaffleProducts) {
            result.add(GetRaffleProductsResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getCreator().getNickName(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getRafflePrice()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    @Override
    public Slice<GetRaffleProductsResponse> findFollowingRaffleProducts(List<Long> creatorIds, Pageable pageable) {
        List<RaffleProduct> findRaffleProducts = queryFactory
                .selectFrom(raffleProduct)
                .where(raffleProduct.creator.userId.in(creatorIds))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(raffleProduct.createdAt.desc())
                .fetch();

        List<GetRaffleProductsResponse> result = new ArrayList<>();

        for (RaffleProduct raffleProduct : findRaffleProducts) {
            result.add(GetRaffleProductsResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getCreator().getNickName(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getRafflePrice()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    @Override
    public Slice<GetRaffleProductsResponse> findCreatorRaffleProducts(Long creatorId, Pageable pageable) {

        List<RaffleProduct> findRaffleProducts = queryFactory
                .selectFrom(raffleProduct)
                .where(raffleProduct.creator.userId.eq(creatorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(raffleProduct.createdAt.desc())
                .fetch();

        List<GetRaffleProductsResponse> result = new ArrayList<>();

        for (RaffleProduct raffleProduct : findRaffleProducts) {
            result.add(GetRaffleProductsResponse.of(raffleProduct.getRaffleProductId(), raffleProduct.getTitle(), raffleProduct.getCreator().getNickName(), raffleProduct.getThumbnailImageUrl(), raffleProduct.getStartDate(), raffleProduct.getEndDate(), raffleProduct.getRafflePrice()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetRaffleProductsResponse> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
