package farmSystem.closeUp.repository.guestbook;

import farmSystem.closeUp.domain.GuestBook;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestBookRepository extends JpaRepository<GuestBook, Long>, GuestBookRepositoryCustom {

}
