package farmSystem.closeUp.repository.raffle;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.RaffleProduct;
import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QRaffleProduct.raffleProduct;

@RequiredArgsConstructor
public class RaffleRepositoryImpl implements RaffleRepositoryCustom{

    private final JPAQueryFactory queryFactory;


    @Override
    public Slice<GetRafflesResponse> findByRaffles(Long creatorId, Pageable pageable) {
        List<RaffleProduct> findRaffles = queryFactory
                .selectFrom(raffleProduct)
                .where(raffleProduct.creator.userId.eq(creatorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(raffleProduct.createdAt.desc())
                .fetch();

        List<GetRafflesResponse> result = new ArrayList<>();

        for (RaffleProduct findRaffle : findRaffles) {
            result.add(GetRafflesResponse.of(findRaffle.getRaffleProductId(),findRaffle.getTitle(), findRaffle.getStartDate(), findRaffle.getEndDate(), findRaffle.getThumbnailImageUrl()));

        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));


    }

    private boolean hasNextPage(List<GetRafflesResponse> contents, int pageSize) {
        if(contents.size()>pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
