package ProjectDoge.StudentSoup.repository.restaurant;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantLike;

import java.util.Optional;

public interface RestaurantLikeRepositoryCustom {

    Optional<RestaurantLike> findRestaurantLikeByRestaurantIdAndMemberId(Long restaurantId, Long memberId);
}
