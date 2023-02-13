package ProjectDoge.StudentSoup.repository.restaurantreview;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;

import java.util.Optional;

public interface RestaurantReviewLikeRepositoryCustom {

    Optional<RestaurantReviewLike> findRestaurantReviewLikeByReviewIdAndMemberId(Long restaurantReviewId, Long memberId);
}
