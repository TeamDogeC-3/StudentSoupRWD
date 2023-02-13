package ProjectDoge.StudentSoup.repository.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantMenuLike.restaurantMenuLike;

@RequiredArgsConstructor
public class RestaurantMenuLikeRepositoryImpl implements RestaurantMenuLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RestaurantMenuLike> findRestaurantMenuLikeByRestaurantMenuIdAndMemberId(Long restaurantMenuId, Long memberId) {

        RestaurantMenuLike query = queryFactory.select(restaurantMenuLike)
                .from(restaurantMenuLike)
                .where(restaurantMenuLike.restaurantMenu.id.eq(restaurantMenuId), restaurantMenuLike.member.memberId.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(query);
    }
}
