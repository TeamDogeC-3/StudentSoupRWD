package ProjectDoge.StudentSoup.repository.restaurant;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantLike.restaurantLike;

@RequiredArgsConstructor
public class RestaurantLikeRepositoryImpl implements RestaurantLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RestaurantLike> findRestaurantLikeByRestaurantIdAndMemberId(Long restaurantId, Long memberId) {

        RestaurantLike query = queryFactory.select(restaurantLike)
                .from(restaurantLike)
                .where(restaurantLike.restaurant.id.eq(restaurantId), restaurantLike.member.memberId.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(query);
    }
}
