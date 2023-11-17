package farmSystem.closeUp.repository.guestbook;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.GuestBook;
import farmSystem.closeUp.dto.guestbook.response.GetGuestBooksResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.ArrayList;
import java.util.List;

import static farmSystem.closeUp.domain.QGuestBook.guestBook;

@RequiredArgsConstructor
public class GuestBookRepositoryImpl implements GuestBookRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<GetGuestBooksResponse> findByGuestBooks(Long creatorId, Pageable pageable) {
        List<GuestBook> findGuestBooks = queryFactory
                .selectFrom(guestBook)
                .where(guestBook.creator.userId.eq(creatorId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .orderBy(guestBook.createdAt.desc())
                .fetch();

        List<GetGuestBooksResponse> result = new ArrayList<>();

        for(GuestBook findGuestBook : findGuestBooks) {
            result.add(GetGuestBooksResponse.of(findGuestBook.getGuestBookId(), findGuestBook.getUser().getUserId(), findGuestBook.getUser().getNickName(), findGuestBook.getUser().getProfileImageUrl(), findGuestBook.getCreatedAt(), findGuestBook.getContent()));
        }

        return new SliceImpl<>(result, pageable, hasNextPage(result, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<GetGuestBooksResponse> contents, int pageSize) {
        if(contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
