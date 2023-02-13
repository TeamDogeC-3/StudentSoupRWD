package ProjectDoge.StudentSoup.repository.restaurantreview;

import ProjectDoge.StudentSoup.dto.member.MemberMyPageRestaurantReviewDto;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import com.querydsl.jpa.impl.JPAQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface RestaurantReviewRepositoryCustom {

    Optional<Double> avgByRestaurantId(Long restaurantId);

    Long countByRestaurantId(Long restaurantId);

    List<RestaurantReview> findByRestaurantIdSorted(Long restaurantId, String sorted, Pageable pageable);

    JPAQuery<Long> pagingCountByRestaurantId(Long restaurantId);

    Page<RestaurantReview> findByMemberIdForMyPage(Long memberId, String cond, Pageable pageable);
}
