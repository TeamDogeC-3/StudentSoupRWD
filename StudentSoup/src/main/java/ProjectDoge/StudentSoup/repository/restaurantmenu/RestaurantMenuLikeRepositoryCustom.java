package ProjectDoge.StudentSoup.repository.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenuLike;

import java.util.Optional;

public interface RestaurantMenuLikeRepositoryCustom {
    Optional<RestaurantMenuLike> findRestaurantMenuLikeByRestaurantMenuIdAndMemberId(Long restaurantMenuId, Long memberId);
}
