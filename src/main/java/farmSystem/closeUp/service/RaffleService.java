package farmSystem.closeUp.service;

import farmSystem.closeUp.common.CustomException;
import farmSystem.closeUp.common.Result;
import farmSystem.closeUp.domain.Raffle;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.domain.WinningInfo;
import farmSystem.closeUp.dto.raffle.response.GetRafflesUserResponse;
import farmSystem.closeUp.dto.raffle.response.GetUserRaffleDetailIntangibleResponse;
import farmSystem.closeUp.dto.raffle.response.GetUserRaffleDetailResponse;
import farmSystem.closeUp.dto.raffle.response.GetUserRaffleDetailTangible;
import farmSystem.closeUp.repository.raffle.RaffleRepository;
import farmSystem.closeUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RaffleService {
    private final RaffleRepository raffleRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    // 유저의 당첨된 래플 조회
    public List<GetRafflesUserResponse> getRafflesUserWinning() {
        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        List<Raffle> raffles = raffleRepository.findAllByUserAndWinningInfo(user, WinningInfo.WINNING);

        List<GetRafflesUserResponse> result = new ArrayList<>();
        for (Raffle raffle : raffles) {
            GetRafflesUserResponse getRafflesResponse = GetRafflesUserResponse.builder()
                    .raffleId(raffle.getRaffleId())
                    .raffleTitle(raffle.getRaffleProduct().getTitle())
                    .winningInfo(raffle.getWinningInfo())
                    .raffleCreatedAt(LocalDate.from(raffle.getRaffleProduct().getCreatedAt()))
                    .raffleEndAt(raffle.getRaffleProduct().getEndDate())
                    .raffleThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                    .creatorId(raffle.getRaffleProduct().getCreator().getUserId())
                    .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                    .build();
            result.add(getRafflesResponse);
        }

        return result;
    }

    public List<GetRafflesUserResponse> getRafflesUserAll() {
        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(Result.NOTFOUND_USER));

        List<Raffle> raffles = raffleRepository.findAllByUser(user);

        List<GetRafflesUserResponse> result = new ArrayList<>();
        for (Raffle raffle : raffles) {
            GetRafflesUserResponse getRafflesResponse = GetRafflesUserResponse.builder()
                    .raffleId(raffle.getRaffleId())
                    .raffleTitle(raffle.getRaffleProduct().getTitle())
                    .winningInfo(raffle.getWinningInfo())
                    .raffleCreatedAt(LocalDate.from(raffle.getRaffleProduct().getCreatedAt()))
                    .raffleEndAt(raffle.getRaffleProduct().getEndDate())
                    .raffleThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                    .creatorId(raffle.getRaffleProduct().getCreator().getUserId())
                    .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                    .build();
            result.add(getRafflesResponse);
        }

        return result;
    }

    public GetUserRaffleDetailResponse getRaffleDetail(Long raffleId) {
        // 본인 래플인지 확인
        Long userId = null;
        try {
            userId = userService.getCurrentUserId();
        } catch (AuthenticationException e) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        Raffle raffle = raffleRepository.findByRaffleId(raffleId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));

        if (raffle.getUser().getUserId() != userId) {
            throw new CustomException(Result.UNAUTHORIZED);
        }

        // 당첨의 경우
        if (raffle.getWinningInfo().equals(WinningInfo.WINNING)) {
            GetUserRaffleDetailTangible result = GetUserRaffleDetailTangible.builder()
                    .winningInfo(raffle.getWinningInfo())
                    .raffleCreateDate(raffle.getCreatedAt())
                    .raffleProductStartDate(raffle.getRaffleProduct().getStartDate().atStartOfDay())
                    .raffleProductEndDate(raffle.getRaffleProduct().getEndDate().atStartOfDay())
                    .raffleProductTitle(raffle.getRaffleProduct().getTitle())
                    .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                    .raffleProductThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                    .raffleProductContent(raffle.getRaffleProduct().getContent())
                    .raffleUserAddress(raffle.getUser().getAddress())
                    .winningProductUrl(raffle.getRaffleProduct().getWinningProduct().getFileUrl())
                    .build();
            return result;
            } else {
                GetUserRaffleDetailIntangibleResponse result = GetUserRaffleDetailIntangibleResponse.builder()
                        .winningInfo(raffle.getWinningInfo())
                        .raffleCreateDate(raffle.getCreatedAt())
                        .raffleProductStartDate(raffle.getRaffleProduct().getStartDate().atStartOfDay())
                        .raffleProductEndDate(raffle.getRaffleProduct().getEndDate().atStartOfDay())
                        .raffleProductTitle(raffle.getRaffleProduct().getTitle())
                        .creatorName(raffle.getRaffleProduct().getCreator().getNickName())
                        .raffleProductThumbnailUrl(raffle.getRaffleProduct().getThumbnailImageUrl())
                        .raffleProductContent(raffle.getRaffleProduct().getContent())
                        .raffleWinningDate(raffle.getRaffleProduct().getWinningDate())
                        .build();
                return result;
        }
    }

    public String downloadWinningProduct(Long raffleId) {
        Raffle raffle = raffleRepository.findById(raffleId).orElseThrow(() -> new CustomException(Result.NOTFOUND_RAFFLE));
        String fileUrl = raffle.getRaffleProduct().getWinningProduct().getFileUrl();
        return fileUrl;
    }
}
