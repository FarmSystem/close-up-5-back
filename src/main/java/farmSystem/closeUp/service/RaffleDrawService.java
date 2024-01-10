package farmSystem.closeUp.service;

import farmSystem.closeUp.domain.Raffle;
import farmSystem.closeUp.domain.RaffleProduct;
import farmSystem.closeUp.domain.WinningInfo;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.raffleProduct.RaffleProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RaffleDrawService {

    private final RaffleProductRepository raffleProductRepository;
    private final RaffleRepository raffleRepository;

    // 성능 최적화에 대해 고민.
    @Transactional
//    @Scheduled(cron = "0 0 12 * * *") // 매일 정오에 실행
    @Scheduled(cron = "0 * * * * *") // 매 분마다 실행
    public void raffleDraw() {
        log.info("추첨 시작");
        LocalDateTime now = LocalDateTime.now();
        LocalDate localDate = now.toLocalDate(); // 날짜 정보만 추출

        // winningdate 기준으로 수정
        List<RaffleProduct> matchingProducts = raffleProductRepository.findByWinningDate(localDate);

        for (RaffleProduct product : matchingProducts) {
            Long winnerCount = product.getWinnerCount();
            List<Raffle> raffles = raffleRepository.findByRaffleProduct(product);

            // 래플 리스트를 무작위로 섞음
            Collections.shuffle(raffles);

            // 하위 부분은 추후에 벌크 연산으로 리팩토링
            // 상위 winnerCount 개의 객체에 대해 WINNING 설정
            raffles.stream().limit(winnerCount).forEach(raffle -> raffle.setWinningInfo(WinningInfo.WINNING));

            // 나머지 객체들에 대해 LOSE 설정
            raffles.stream().skip(winnerCount).forEach(raffle -> raffle.setWinningInfo(WinningInfo.LOSE));

            // 변경된 Raffle 객체들을 저장
//            raffleRepository.saveAll(raffles);
            log.info("래플 상품 1개에 대해 추첨 종료");
        }
        log.info("추첨 종료");
    }
}
