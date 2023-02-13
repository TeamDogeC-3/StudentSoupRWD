package ProjectDoge.StudentSoup.service.restaurantreview;

import ProjectDoge.StudentSoup.entity.restaurant.RestaurantMenu;
import ProjectDoge.StudentSoup.entity.restaurant.RestaurantReview;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuIdNotSentException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantMenuNotFoundException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewIdNotSentException;
import ProjectDoge.StudentSoup.exception.restaurant.RestaurantReviewNotFoundException;
import ProjectDoge.StudentSoup.repository.restaurantreview.RestaurantReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantReviewFindService {

    private final RestaurantReviewRepository restaurantReviewRepository;

    public RestaurantReview findOne(Long restaurantReviewId){
        checkRestaurantMenuIdSent(restaurantReviewId);
        return restaurantReviewRepository.findById(restaurantReviewId).orElseThrow(() -> {
            throw new RestaurantReviewNotFoundException("등록되지 않은 리뷰 입니다.");
        });
    }

    private void checkRestaurantMenuIdSent(Long restaurantReviewId) {
        if (restaurantReviewId == null){
            log.info("RestaurantReviewId가 전송되지 않았습니다.");
            throw new RestaurantReviewIdNotSentException("restaurantReviewId가 전송되지 않았습니다.");
        }
    }
}
