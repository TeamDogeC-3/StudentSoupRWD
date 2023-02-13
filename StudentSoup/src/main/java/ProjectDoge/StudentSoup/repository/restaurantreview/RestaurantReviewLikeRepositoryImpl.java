package ProjectDoge.StudentSoup.repository.restaurantreview;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantMenuLike.restaurantMenuLike;
import static ProjectDoge.StudentSoup.entity.restaurant.QRestaurantReviewLike.restaurantReviewLike;

@RequiredArgsConstructor
public class RestaurantReviewLikeRepositoryImpl implements RestaurantReviewLikeRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<RestaurantReviewLike> findRestaurantReviewLikeByReviewIdAndMemberId(Long restaurantReviewId, Long memberId) {

        RestaurantReviewLike query = queryFactory.select(restaurantReviewLike)
                .from(restaurantReviewLike)
                .where(restaurantReviewLike.restaurantReview.id.eq(restaurantReviewId), restaurantReviewLike.member.memberId.eq(memberId))
                .fetchOne();

        return Optional.ofNullable(query);
    }
}
