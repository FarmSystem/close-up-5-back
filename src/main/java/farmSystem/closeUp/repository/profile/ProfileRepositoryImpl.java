package farmSystem.closeUp.repository.profile;

import com.querydsl.jpa.impl.JPAQueryFactory;
import farmSystem.closeUp.domain.User;
import farmSystem.closeUp.dto.profile.response.GetProfileResponse;
import lombok.RequiredArgsConstructor;

import static farmSystem.closeUp.domain.QUser.user;

@RequiredArgsConstructor
public class ProfileRepositoryImpl implements ProfileRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public GetProfileResponse findByProfile(Long creatorId) {
        User findProfile = queryFactory
                .selectFrom(user)
                .where(user.userId.eq(creatorId))
                .fetchOne();

        if(findProfile != null) {
            return GetProfileResponse.of(
                    findProfile.getUserId(),
                    findProfile.getNickName(),
                    findProfile.getProfileImageUrl(),
                    findProfile.getProfileComment(),
                    findProfile.getPoint()

            );
        } else {
            return null;
        }





    }
}


