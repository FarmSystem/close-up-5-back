package farmSystem.closeUp.service;

import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.raffle.RaffleRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleService {
    private final RaffleRepository raffleRepository;
    private final RaffleRepositoryImpl raffleRepositoryImpl;

    // 크리에이터 래플 조회 (무한 스크롤)
    @Transactional
    public Slice<GetRafflesResponse> getRaffles(Long creatorId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetRafflesResponse> findRaffles = raffleRepositoryImpl.findByRaffles(creatorId, pageable);

        return findRaffles;
    }

}
