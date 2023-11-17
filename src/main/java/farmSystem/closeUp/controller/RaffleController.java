package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.raffle.response.GetRafflesResponse;
import farmSystem.closeUp.service.RaffleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RaffleController {

    private final RaffleService raffleService;

    // 크리에이터 래플 조회 (무한 스크롤)
    @GetMapping("/user/{creatorId}/raffles")
    public CommonResponse<Slice<GetRafflesResponse>> getRaffles(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(raffleService.getRaffles(creatorId, pageable));
    }
}
