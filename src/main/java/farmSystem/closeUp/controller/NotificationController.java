package farmSystem.closeUp.controller;

import farmSystem.closeUp.common.CommonResponse;
import farmSystem.closeUp.dto.notification.response.GetNotificationsResponse;
import farmSystem.closeUp.service.NotificationService;
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
public class NotificationController {

    private final NotificationService notificationService;

    // 크리에이터 공지사항 조회(무한 스크롤) - 유저
    @GetMapping("/user/{creatorId}/notifications")
    public CommonResponse<Slice<GetNotificationsResponse>> getNotifications(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(notificationService.getNotifications(creatorId, pageable));
    }

    // 크리에이터 공지사항 조회(무한 스크롤) - 크리에이터
    @GetMapping("/creator/{creatorId}/notifications")
    public CommonResponse<Slice<GetNotificationsResponse>> getNotificationsCreator(@PathVariable("creatorId") Long creatorId, Pageable pageable) {
        return CommonResponse.success(notificationService.getNotificationsCreator(creatorId, pageable));
    }
}