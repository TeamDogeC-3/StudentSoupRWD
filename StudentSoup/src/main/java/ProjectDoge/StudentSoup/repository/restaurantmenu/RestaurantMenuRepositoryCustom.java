package ProjectDoge.StudentSoup.repository.restaurantmenu;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RestaurantMenuRepositoryCustom {

    Optional<RestaurantMenu> findByRestaurantMenuNameAndRestaurant_RestaurantId(String menuName, Long restaurantId);

    List<RestaurantMenu> findByRestaurantId(Long restaurantId, Pageable pageable);
    List<RestaurantMenu> findByRestaurantId(Long restaurantId);
    JPAQuery<Long> countByRestaurantId(Long restaurantId);
}
