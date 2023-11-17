package farmSystem.closeUp.service;

import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import farmSystem.closeUp.repository.guestbook.GuestBookRepository;
import farmSystem.closeUp.repository.guestbook.GuestBookRepositoryImpl;
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
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;
    private final GuestBookRepositoryImpl guestBookRepositoryImpl;

    @Transactional
    public Slice<GetGuestBooksResponse> getGuestBooks(Long creatorId, Pageable pageable) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);
        Slice<GetGuestBooksResponse> findGuestBooks = guestBookRepositoryImpl.findByGuestBooks(creatorId, pageable);

        return findGuestBooks;
    }
}
