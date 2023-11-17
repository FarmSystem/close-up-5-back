package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import farmSystem.closeUp.service.GuestBookService;
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
public class GuestBookController {

    private final GuestBookService guestBookService;

    // 크리에이터 방명록 조회(무한 스크롤)
    @GetMapping("/user/{creatorId}/guestbooks")
    public CommonResponse<Slice<GetGuestBooksResponse>> getGuestBooks(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(guestBookService.getGuestBooks(creatorId, pageable));
    }

}
