package farmSystem.closeUp.repository.guestbook;

import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface GuestBookRepositoryCustom {

    Slice<GetGuestBooksResponse> findByGuestBooks(Long creatorId, Pageable pageable);
}
