package ProjectDoge.StudentSoup.repository.restaurantreview;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantReviewLikeRepository extends JpaRepository<RestaurantReviewLike, Long>, RestaurantReviewLikeRepositoryCustom {
}
